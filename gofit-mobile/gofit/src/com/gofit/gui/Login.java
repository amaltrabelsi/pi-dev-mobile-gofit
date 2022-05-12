package com.gofit.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.MainApp;
import com.gofit.entities.Utilisateur;
import com.gofit.services.UtilisateurService;

public class Login extends Form {

    public static Form loginForm;

    public Login() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {


        TextField emailTF = new TextField("");
        emailTF.setHint("Tapez votre email");

        TextField passwordTF = new TextField("", "Tapez votre mot de passe", 20, TextField.PASSWORD);

        Button connectBtn = new Button("Connexion");
        connectBtn.setUIID("buttonWhiteCenter");
        connectBtn.addActionListener(l -> connexion(emailTF.getText(), passwordTF.getText()));

        Label registerLabel = new Label("Besoin d'un compte ?");

        Button registerBtn = new Button("Register");
        registerBtn.setUIID("buttonWhiteCenter");
        registerBtn.addActionListener(l -> new com.gofit.gui.Register(this).show());

        this.addAll(emailTF, passwordTF, connectBtn, registerLabel, registerBtn);


        Button backendBtn = new Button("Back");
        backendBtn.addActionListener(l -> new com.gofit.gui.back.AccueilBack().show());

        this.add(backendBtn);
    }

    private void connexion(String email, String password) {
        Utilisateur utilisateur = UtilisateurService.getInstance().checkCredentials(email, password);

        if (utilisateur != null) {
            MainApp.setSession(utilisateur);
            new com.gofit.gui.front.AccueilFront().show();
        } else {
            Dialog.show("Erreur", "Identifiants invalides", new Command("Ok"));
        }
    }
}
