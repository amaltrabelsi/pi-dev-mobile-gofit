package com.gofit.gui.front.panier;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Panier;
import com.gofit.entities.Produit;
import com.gofit.entities.Utilisateur;
import com.gofit.services.PanierService;
import com.gofit.services.ProduitService;
import com.gofit.services.UtilisateurService;

import java.util.ArrayList;

public class Manage extends Form {


    Panier currentPanier;


    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;
    ArrayList<Produit> listProduits;
    PickerComponent produitPC;
    Produit selectedProduit = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentPanier == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentPanier = ShowAll.currentPanier;

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

        String[] produitStrings;
        int produitIndex;
        produitPC = PickerComponent.createStrings("").label("Produit");
        listProduits = ProduitService.getInstance().getAll();
        produitStrings = new String[listProduits.size()];
        produitIndex = 0;
        for (Produit produit : listProduits) {
            produitStrings[produitIndex] = produit.getNomProduit();
            produitIndex++;
        }
        if (listProduits.size() > 0) {
            produitPC.getPicker().setStrings(produitStrings);
            produitPC.getPicker().addActionListener(l -> selectedProduit = listProduits.get(produitPC.getPicker().getSelectedStringIndex()));
        } else {
            produitPC.getPicker().setStrings("");
        }


        if (currentPanier == null) {


            manageButton = new Button("Ajouter");
        } else {


            utilisateurPC.getPicker().setSelectedString(currentPanier.getUtilisateur().getEmail());
            selectedUtilisateur = currentPanier.getUtilisateur();
            produitPC.getPicker().setSelectedString(currentPanier.getProduit().getNomProduit());
            selectedProduit = currentPanier.getProduit();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                utilisateurPC, produitPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentPanier == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PanierService.getInstance().add(
                            new Panier(


                                    selectedUtilisateur,
                                    selectedProduit
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Panier ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de panier. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PanierService.getInstance().edit(
                            new Panier(
                                    currentPanier.getId(),


                                    selectedUtilisateur,
                                    selectedProduit

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Panier modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de panier. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }

        if (selectedProduit == null) {
            Dialog.show("Avertissement", "Veuillez choisir un produit", new Command("Ok"));
            return false;
        }


        return true;
    }
}