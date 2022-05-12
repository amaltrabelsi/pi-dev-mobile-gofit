package com.gofit.gui;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Utilisateur;
import com.gofit.services.UtilisateurService;

public class Register extends Form {


    Label emailLabel, passwordLabel, numLabel;
    TextField
            emailTF,
            passwordTF,
            numTF, elemTF;


    Button manageButton;

    Form previous;

    public Register(Form previous) {
        super("Inscription", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {


        Label loginLabel = new Label("Vous avez deja un compte ?");

        Button loginBtn = new Button("Login");
        loginBtn.setUIID("buttonWhiteCenter");
        loginBtn.addActionListener(l -> new com.gofit.gui.Login().show());


        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");

        passwordLabel = new Label("Password : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField();
        passwordTF.setHint("Tapez le password");

        numLabel = new Label("Num : ");
        numLabel.setUIID("labelDefault");
        numTF = new TextField();
        numTF.setHint("Tapez le num");


        manageButton = new Button("S'inscrire");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                emailLabel, emailTF,
                passwordLabel, passwordTF,
                numLabel, numTF,

                manageButton,
                loginLabel, loginBtn
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = UtilisateurService.getInstance().add(
                        new Utilisateur(


                                emailTF.getText(),
                                "ROLE_USER",
                                passwordTF.getText(),
                                numTF.getText()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Inscription effectué avec succes", new Command("Ok"));
                    previous.showBack();
                } else if (responseCode == 203) {
                    Dialog.show("Erreur", "Email deja utilisé", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de utilisateur. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir email", new Command("Ok"));
            return false;
        }
if (!emailTF.getText().contains("@")) {
            Dialog.show("Avertissement", "Email invalide", new Command("Ok"));
            return false;
        }


        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir password", new Command("Ok"));
            return false;
        }


        if (numTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir num", new Command("Ok"));
            return false;
        }


        return true;
    }
}