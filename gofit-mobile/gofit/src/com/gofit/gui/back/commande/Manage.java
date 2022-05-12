package com.gofit.gui.back.commande;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Commande;
import com.gofit.entities.Utilisateur;
import com.gofit.services.CommandeService;
import com.gofit.services.UtilisateurService;

import java.util.ArrayList;

public class Manage extends Form {


    Commande currentCommande;

    TextField totalTF;
    TextField nbProduitTF;
    TextField modePaiementTF;
    Label totalLabel;
    Label nbProduitLabel;
    Label modePaiementLabel;
    PickerComponent dateCTF;
    CheckBox statutCB;
    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentCommande == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCommande = ShowAll.currentCommande;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateurStrings;
        int utilisateurIndex;
        utilisateurPC = PickerComponent.createStrings("").label("Utilisateur");
        listUtilisateurs = UtilisateurService.getInstance().getAll();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getEmail();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
            utilisateurPC.getPicker().setStrings(utilisateurStrings);
            utilisateurPC.getPicker().addActionListener(l -> selectedUtilisateur = listUtilisateurs.get(utilisateurPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateurPC.getPicker().setStrings("");
        }


        dateCTF = PickerComponent.createDate(null).label("DateC");


        totalLabel = new Label("Total : ");
        totalLabel.setUIID("labelDefault");
        totalTF = new TextField();
        totalTF.setHint("Tapez le total");


        nbProduitLabel = new Label("NbProduit : ");
        nbProduitLabel.setUIID("labelDefault");
        nbProduitTF = new TextField();
        nbProduitTF.setHint("Tapez le nbProduit");


        modePaiementLabel = new Label("ModePaiement : ");
        modePaiementLabel.setUIID("labelDefault");
        modePaiementTF = new TextField();
        modePaiementTF.setHint("Tapez le modePaiement");


        statutCB = new CheckBox("Statut : ");


        if (currentCommande == null) {


            manageButton = new Button("Ajouter");
        } else {
            dateCTF.getPicker().setDate(currentCommande.getDateC());
            totalTF.setText(String.valueOf(currentCommande.getTotal()));
            nbProduitTF.setText(String.valueOf(currentCommande.getNbProduit()));
            modePaiementTF.setText(currentCommande.getModePaiement());
            if (currentCommande.getStatut() == 1) {
                statutCB.setSelected(true);
            }


            utilisateurPC.getPicker().setSelectedString(currentCommande.getUtilisateur().getEmail());
            selectedUtilisateur = currentCommande.getUtilisateur();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                dateCTF,
                totalLabel, totalTF,
                nbProduitLabel, nbProduitTF,
                modePaiementLabel, modePaiementTF,
                statutCB,

                utilisateurPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentCommande == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommandeService.getInstance().add(
                            new Commande(


                                    dateCTF.getPicker().getDate(),
                                    Float.parseFloat(totalTF.getText()),
                                    (int) Float.parseFloat(nbProduitTF.getText()),
                                    modePaiementTF.getText(),
                                    statutCB.isSelected() ? 1 : 0,
                                    selectedUtilisateur
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Commande ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de commande. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommandeService.getInstance().edit(
                            new Commande(
                                    currentCommande.getId(),


                                    dateCTF.getPicker().getDate(),
                                    Float.parseFloat(totalTF.getText()),
                                    (int) Float.parseFloat(nbProduitTF.getText()),
                                    modePaiementTF.getText(),
                                    statutCB.isSelected() ? 1 : 0,
                                    selectedUtilisateur

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Commande modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de commande. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (dateCTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateC", new Command("Ok"));
            return false;
        }


        if (totalTF.getText().equals("")) {
            Dialog.show("Avertissement", "Total vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(totalTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", totalTF.getText() + " n'est pas un nombre valide (total)", new Command("Ok"));
            return false;
        }


        if (nbProduitTF.getText().equals("")) {
            Dialog.show("Avertissement", "NbProduit vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nbProduitTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nbProduitTF.getText() + " n'est pas un nombre valide (nbProduit)", new Command("Ok"));
            return false;
        }


        if (modePaiementTF.getText().equals("")) {
            Dialog.show("Avertissement", "ModePaiement vide", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }


        return true;
    }
}