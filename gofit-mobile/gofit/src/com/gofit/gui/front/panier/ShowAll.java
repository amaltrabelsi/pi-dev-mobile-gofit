package com.gofit.gui.front.panier;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Panier;
import com.gofit.services.PanierService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Panier currentPanier = null;
    Form previous;
    Button addBtn;
    Label utilisateurLabel, produitLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Paniers", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Panier> listPaniers = PanierService.getInstance().getAll();


        if (listPaniers.size() > 0) {
            for (Panier panier : listPaniers) {
                Component model = makePanierModel(panier);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentPanier = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Panier panier) {
        Container panierModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        panierModel.setUIID("containerRounded");


        utilisateurLabel = new Label("Utilisateur : " + panier.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + panier.getProduit());
        produitLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + panier.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + panier.getProduit().getNomProduit());
        produitLabel.setUIID("labelDefault");


        panierModel.addAll(

                utilisateurLabel, produitLabel
        );

        return panierModel;
    }

    private Component makePanierModel(Panier panier) {

        Container panierModel = makeModelWithoutButtons(panier);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentPanier = panier;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce panier ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = PanierService.getInstance().delete(panier.getId());

                if (responseCode == 200) {
                    currentPanier = null;
                    dlg.dispose();
                    panierModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du panier. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        panierModel.add(btnsContainer);

        return panierModel;
    }

}