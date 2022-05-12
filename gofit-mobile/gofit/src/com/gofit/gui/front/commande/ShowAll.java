package com.gofit.gui.front.commande;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Commande;
import com.gofit.services.CommandeService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Commande currentCommande = null;
    Form previous;
    Button addBtn;
    Label dateCLabel, totalLabel, nbProduitLabel, modePaiementLabel, statutLabel, utilisateurLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Commandes", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Commande> listCommandes = CommandeService.getInstance().getAll();


        if (listCommandes.size() > 0) {
            for (Commande commande : listCommandes) {
                Component model = makeCommandeModel(commande);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCommande = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Commande commande) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");


        dateCLabel = new Label("DateC : " + new SimpleDateFormat("dd-MM-yyyy").format(commande.getDateC()));
        dateCLabel.setUIID("labelDefault");

        totalLabel = new Label("Total : " + commande.getTotal());
        totalLabel.setUIID("labelDefault");

        nbProduitLabel = new Label("NbProduit : " + commande.getNbProduit());
        nbProduitLabel.setUIID("labelDefault");

        modePaiementLabel = new Label("ModePaiement : " + commande.getModePaiement());
        modePaiementLabel.setUIID("labelDefault");

        statutLabel = new Label("Statut : " + (commande.getStatut() == 1 ? "True" : "False"));
        statutLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commande.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + commande.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");


        commandeModel.addAll(

                dateCLabel, totalLabel, nbProduitLabel, modePaiementLabel, statutLabel, utilisateurLabel
        );

        return commandeModel;
    }

    private Component makeCommandeModel(Commande commande) {

        Container commandeModel = makeModelWithoutButtons(commande);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCommande = commande;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commande ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommandeService.getInstance().delete(commande.getId());

                if (responseCode == 200) {
                    currentCommande = null;
                    dlg.dispose();
                    commandeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        commandeModel.add(btnsContainer);

        return commandeModel;
    }

}