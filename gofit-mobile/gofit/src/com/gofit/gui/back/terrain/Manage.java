package com.gofit.gui.back.terrain;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Terrain;
import com.gofit.services.TerrainService;
import com.gofit.utils.AlertUtils;
import com.gofit.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Terrain currentTerrain;

    TextField nomTF;
    TextField descriptionTF;
    TextField activitieTF;
    TextField contactTF;
    TextField prixTF;
    TextField imageTF;
    TextField regionTF;
    Label nomLabel;
    Label descriptionLabel;
    Label activitieLabel;
    Label contactLabel;
    Label prixLabel;
    Label imageLabel;
    Label regionLabel;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentTerrain == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentTerrain = ShowAll.currentTerrain;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        activitieLabel = new Label("Activitie : ");
        activitieLabel.setUIID("labelDefault");
        activitieTF = new TextField();
        activitieTF.setHint("Tapez le activitie");


        contactLabel = new Label("Contact : ");
        contactLabel.setUIID("labelDefault");
        contactTF = new TextField();
        contactTF.setHint("Tapez le contact");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        regionLabel = new Label("Region : ");
        regionLabel.setUIID("labelDefault");
        regionTF = new TextField();
        regionTF.setHint("Tapez le region");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentTerrain == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentTerrain.getNom());
            descriptionTF.setText(currentTerrain.getDescription());
            activitieTF.setText(currentTerrain.getActivitie());
            contactTF.setText(currentTerrain.getContact());
            prixTF.setText(currentTerrain.getPrix());

            regionTF.setText(currentTerrain.getRegion());


            if (currentTerrain.getImage() != null) {
                selectedImage = currentTerrain.getImage();
                String url = Statics.TERRAIN_IMAGE_URL + currentTerrain.getImage();
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
                nomLabel, nomTF,
                descriptionLabel, descriptionTF,
                activitieLabel, activitieTF,
                contactLabel, contactTF,
                prixLabel, prixTF,

                regionLabel, regionTF,

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

        if (currentTerrain == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = TerrainService.getInstance().add(
                            new Terrain(


                                    nomTF.getText(),
                                    descriptionTF.getText(),
                                    activitieTF.getText(),
                                    contactTF.getText(),
                                    prixTF.getText(),
                                    selectedImage,
                                    regionTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Terrain ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de terrain. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = TerrainService.getInstance().edit(
                            new Terrain(
                                    currentTerrain.getId(),


                                    nomTF.getText(),
                                    descriptionTF.getText(),
                                    activitieTF.getText(),
                                    contactTF.getText(),
                                    prixTF.getText(),
                                    selectedImage,
                                    regionTF.getText()

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Terrain modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de terrain. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (activitieTF.getText().equals("")) {
            Dialog.show("Avertissement", "Activitie vide", new Command("Ok"));
            return false;
        }


        if (contactTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contact vide", new Command("Ok"));
            return false;
        }


        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prix vide", new Command("Ok"));
            return false;
        }


        if (regionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Region vide", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}