package com.gofit.gui.front.utilisateur;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.MainApp;
import com.gofit.entities.Utilisateur;

public class MyProfile extends Form {


    public static Utilisateur currentUtilisateur = null;
    Button editProfileBtn;
    Label emailLabel, roleLabel, numLabel;

    public MyProfile(Form previous) {
        super("Mon profil", new BoxLayout(BoxLayout.Y_AXIS));

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

        currentUtilisateur = MainApp.getSession();

        editProfileBtn = new Button("Modifier mon profil");
        editProfileBtn.setUIID("buttonWhiteCenter");

        this.add(editProfileBtn);

        this.add(makeUtilisateurModel(currentUtilisateur));
    }

    private void addActions() {
        editProfileBtn.addActionListener(action -> {
            currentUtilisateur = null;
            new EditProfile(this).show();
        });
    }

    private Component makeUtilisateurModel(Utilisateur utilisateur) {
        Container utilisateurModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        utilisateurModel.setUIID("containerRounded");


        emailLabel = new Label("Email : " + utilisateur.getEmail());
        emailLabel.setUIID("labelDefault");

        roleLabel = new Label("Role : " + utilisateur.getRole());
        roleLabel.setUIID("labelDefault");

        numLabel = new Label("Num : " + utilisateur.getNum());
        numLabel.setUIID("labelDefault");


        utilisateurModel.addAll(
                emailLabel, roleLabel, numLabel

        );

        return utilisateurModel;
    }
}