package com.gofit.gui.back.actualite;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Actualite;
import com.gofit.entities.Utilisateur;
import com.gofit.services.ActualiteService;
import com.gofit.services.UtilisateurService;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Actualite currentActualite;

    TextField titreTF;
    TextField descriptionTF;
    TextField contenuTF;
    TextField categorieTF;
    TextField imageTF;
    Label titreLabel;
    Label descriptionLabel;
    Label contenuLabel;
    Label categorieLabel;
    Label imageLabel;


    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentActualite == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentActualite = ShowAll.currentActualite;

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


        titreLabel = new Label("Titre : ");
        titreLabel.setUIID("labelDefault");
        titreTF = new TextField();
        titreTF.setHint("Tapez le titre");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        contenuLabel = new Label("Contenu : ");
        contenuLabel.setUIID("labelDefault");
        contenuTF = new TextField();
        contenuTF.setHint("Tapez le contenu");


        categorieLabel = new Label("Categorie : ");
        categorieLabel.setUIID("labelDefault");
        categorieTF = new TextField();
        categorieTF.setHint("Tapez le categorie");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentActualite == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            titreTF.setText(currentActualite.getTitre());
            descriptionTF.setText(currentActualite.getDescription());
            contenuTF.setText(currentActualite.getContenu());
            categorieTF.setText(currentActualite.getCategorie());


            utilisateurPC.getPicker().setSelectedString(currentActualite.getUtilisateur().getEmail());
            selectedUtilisateur = currentActualite.getUtilisateur();


            if (currentActualite.getImage() != null) {
                selectedImage = currentActualite.getImage();
                String url = Statics.ACTUALITE_IMAGE_URL + currentActualite.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                titreLabel, titreTF,
                descriptionLabel, descriptionTF,
                contenuLabel, contenuTF,
                categorieLabel, categorieTF,


                utilisateurPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        if (currentActualite == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ActualiteService.getInstance().add(
                            new Actualite(


                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    contenuTF.getText(),
                                    categorieTF.getText(),
                                    selectedImage,
                                    selectedUtilisateur
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Actualite ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de actualite. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ActualiteService.getInstance().edit(
                            new Actualite(
                                    currentActualite.getId(),


                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    contenuTF.getText(),
                                    categorieTF.getText(),
                                    selectedImage,
                                    selectedUtilisateur

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Actualite modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de actualite. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (titreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Titre vide", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (contenuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contenu vide", new Command("Ok"));
            return false;
        }


        if (categorieTF.getText().equals("")) {
            Dialog.show("Avertissement", "Categorie vide", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}