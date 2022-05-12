package com.gofit.gui.back.utilisateur;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Utilisateur;
import com.gofit.services.UtilisateurService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Utilisateur currentUtilisateur = null;
    Form previous;
    Button addBtn;
    Label emailLabel, roleLabel, passwordLabel, numLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Utilisateurs", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Utilisateur> listUtilisateurs = UtilisateurService.getInstance().getAll();


        if (listUtilisateurs.size() > 0) {
            for (Utilisateur utilisateur : listUtilisateurs) {
                Component model = makeUtilisateurModel(utilisateur);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentUtilisateur = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Utilisateur utilisateur) {
        Container utilisateurModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        utilisateurModel.setUIID("containerRounded");


        emailLabel = new Label("Email : " + utilisateur.getEmail());
        emailLabel.setUIID("labelDefault");

        roleLabel = new Label("Role : " + utilisateur.getRole());
        roleLabel.setUIID("labelDefault");

        passwordLabel = new Label("Password : " + utilisateur.getPassword());
        passwordLabel.setUIID("labelDefault");

        numLabel = new Label("Num : " + utilisateur.getNum());
        numLabel.setUIID("labelDefault");


        utilisateurModel.addAll(

                emailLabel, roleLabel, passwordLabel, numLabel
        );

        return utilisateurModel;
    }

    private Component makeUtilisateurModel(Utilisateur utilisateur) {

        Container utilisateurModel = makeModelWithoutButtons(utilisateur);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentUtilisateur = utilisateur;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce utilisateur ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = UtilisateurService.getInstance().delete(utilisateur.getId());

                if (responseCode == 200) {
                    currentUtilisateur = null;
                    dlg.dispose();
                    utilisateurModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du utilisateur. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        utilisateurModel.add(btnsContainer);

        return utilisateurModel;
    }

}