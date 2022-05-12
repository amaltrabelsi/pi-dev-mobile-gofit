package com.gofit.gui.front.utilisateur;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.MainApp;
import com.gofit.entities.Utilisateur;
import com.gofit.services.UtilisateurService;

public class EditProfile extends Form {


    Utilisateur currentUtilisateur;

    Label emailLabel, numLabel;
    TextField
            emailTF,
            numTF;


    Button manageButton;

    Form previous;

    public EditProfile(Form previous) {
        super("Modifier mon profil", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {
        currentUtilisateur = MainApp.getSession();


        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");

        numLabel = new Label("Num : ");
        numLabel.setUIID("labelDefault");
        numTF = new TextField();
        numTF.setHint("Tapez le num");


        emailTF.setText(currentUtilisateur.getEmail());
        numTF.setText(currentUtilisateur.getNum());


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                emailLabel, emailTF,
                numLabel, numTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = UtilisateurService.getInstance().edit(
                        new Utilisateur(
                                currentUtilisateur.getId(),


                                emailTF.getText(),
                                "ROLE_USER",
                                "",
                                numTF.getText()

                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Profil modifié avec succes", new Command("Ok"));
                    MainApp.setSession(new Utilisateur(
                            currentUtilisateur.getId(),


                            emailTF.getText(),
                            "ROLE_USER",
                            "",
                            numTF.getText()
                    ));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de utilisateur. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((MyProfile) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }

        if (numTF.getText().equals("")) {
            Dialog.show("Avertissement", "Num vide", new Command("Ok"));
            return false;
        }


        return true;
    }
}