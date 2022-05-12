package com.gofit.gui.back.utilisateur;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Utilisateur;
import com.gofit.services.UtilisateurService;

public class Manage extends Form {


    Utilisateur currentUtilisateur;

    TextField emailTF;
    TextField roleTF;
    TextField passwordTF;
    TextField numTF;
    Label emailLabel;
    Label roleLabel;
    Label passwordLabel;
    Label numLabel;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentUtilisateur == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentUtilisateur = ShowAll.currentUtilisateur;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");


        roleLabel = new Label("Role : ");
        roleLabel.setUIID("labelDefault");
        roleTF = new TextField();
        roleTF.setHint("Tapez le role");


        passwordLabel = new Label("Password : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField();
        passwordTF.setHint("Tapez le password");


        numLabel = new Label("Num : ");
        numLabel.setUIID("labelDefault");
        numTF = new TextField();
        numTF.setHint("Tapez le num");


        if (currentUtilisateur == null) {


            manageButton = new Button("Ajouter");
        } else {
            emailTF.setText(currentUtilisateur.getEmail());
            roleTF.setText(currentUtilisateur.getRole());
            passwordTF.setText(currentUtilisateur.getPassword());
            numTF.setText(currentUtilisateur.getNum());


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                emailLabel, emailTF,
                roleLabel, roleTF,
                passwordLabel, passwordTF,
                numLabel, numTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentUtilisateur == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = UtilisateurService.getInstance().add(
                            new Utilisateur(


                                    emailTF.getText(),
                                    roleTF.getText(),
                                    passwordTF.getText(),
                                    numTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Utilisateur ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de utilisateur. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = UtilisateurService.getInstance().edit(
                            new Utilisateur(
                                    currentUtilisateur.getId(),


                                    emailTF.getText(),
                                    roleTF.getText(),
                                    passwordTF.getText(),
                                    numTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Utilisateur modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de utilisateur. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }


        if (roleTF.getText().equals("")) {
            Dialog.show("Avertissement", "Role vide", new Command("Ok"));
            return false;
        }


        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Password vide", new Command("Ok"));
            return false;
        }


        if (numTF.getText().equals("")) {
            Dialog.show("Avertissement", "Num vide", new Command("Ok"));
            return false;
        }


        return true;
    }
}