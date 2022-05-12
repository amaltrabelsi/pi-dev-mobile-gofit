package com.gofit.gui.back.business;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Business;
import com.gofit.entities.Utilisateur;
import com.gofit.services.BusinessService;
import com.gofit.services.UtilisateurService;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Business currentBusiness;

    TextField nomTF;
    TextField nomGerantTF;
    TextField prenomGerantTF;
    TextField regionTF;
    TextField adresseTF;
    TextField descriptionTF;
    TextField telPortableTF;
    TextField telFixTF;
    TextField imageTF;
    TextField occurenceTF;
    Label nomLabel;
    Label nomGerantLabel;
    Label prenomGerantLabel;
    Label regionLabel;
    Label adresseLabel;
    Label descriptionLabel;
    Label telPortableLabel;
    Label telFixLabel;
    Label imageLabel;
    Label occurenceLabel;
    PickerComponent dateFondationTF;

    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentBusiness == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentBusiness = ShowAll.currentBusiness;

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


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");


        nomGerantLabel = new Label("NomGerant : ");
        nomGerantLabel.setUIID("labelDefault");
        nomGerantTF = new TextField();
        nomGerantTF.setHint("Tapez le nomGerant");


        prenomGerantLabel = new Label("PrenomGerant : ");
        prenomGerantLabel.setUIID("labelDefault");
        prenomGerantTF = new TextField();
        prenomGerantTF.setHint("Tapez le prenomGerant");


        regionLabel = new Label("Region : ");
        regionLabel.setUIID("labelDefault");
        regionTF = new TextField();
        regionTF.setHint("Tapez le region");


        adresseLabel = new Label("Adresse : ");
        adresseLabel.setUIID("labelDefault");
        adresseTF = new TextField();
        adresseTF.setHint("Tapez le adresse");


        dateFondationTF = PickerComponent.createDate(null).label("DateFondation");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        telPortableLabel = new Label("TelPortable : ");
        telPortableLabel.setUIID("labelDefault");
        telPortableTF = new TextField();
        telPortableTF.setHint("Tapez le telPortable");


        telFixLabel = new Label("TelFix : ");
        telFixLabel.setUIID("labelDefault");
        telFixTF = new TextField();
        telFixTF.setHint("Tapez le telFix");


        occurenceLabel = new Label("Occurence : ");
        occurenceLabel.setUIID("labelDefault");
        occurenceTF = new TextField();
        occurenceTF.setHint("Tapez le occurence");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentBusiness == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentBusiness.getNom());
            nomGerantTF.setText(currentBusiness.getNomGerant());
            prenomGerantTF.setText(currentBusiness.getPrenomGerant());
            regionTF.setText(currentBusiness.getRegion());
            adresseTF.setText(currentBusiness.getAdresse());
            dateFondationTF.getPicker().setDate(currentBusiness.getDateFondation());
            descriptionTF.setText(currentBusiness.getDescription());
            telPortableTF.setText(currentBusiness.getTelPortable());
            telFixTF.setText(currentBusiness.getTelFix());

            occurenceTF.setText(String.valueOf(currentBusiness.getOccurence()));


            utilisateurPC.getPicker().setSelectedString(currentBusiness.getUtilisateur().getEmail());
            selectedUtilisateur = currentBusiness.getUtilisateur();


            if (currentBusiness.getImage() != null) {
                selectedImage = currentBusiness.getImage();
                String url = Statics.BUSINESS_IMAGE_URL + currentBusiness.getImage();
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
                nomGerantLabel, nomGerantTF,
                prenomGerantLabel, prenomGerantTF,
                regionLabel, regionTF,
                adresseLabel, adresseTF,
                dateFondationTF,
                descriptionLabel, descriptionTF,
                telPortableLabel, telPortableTF,
                telFixLabel, telFixTF,

                occurenceLabel, occurenceTF,

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

        if (currentBusiness == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = BusinessService.getInstance().add(
                            new Business(


                                    nomTF.getText(),
                                    nomGerantTF.getText(),
                                    prenomGerantTF.getText(),
                                    regionTF.getText(),
                                    adresseTF.getText(),
                                    dateFondationTF.getPicker().getDate(),
                                    descriptionTF.getText(),
                                    telPortableTF.getText(),
                                    telFixTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(occurenceTF.getText()),
                                    selectedUtilisateur
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Business ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de business. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = BusinessService.getInstance().edit(
                            new Business(
                                    currentBusiness.getId(),


                                    nomTF.getText(),
                                    nomGerantTF.getText(),
                                    prenomGerantTF.getText(),
                                    regionTF.getText(),
                                    adresseTF.getText(),
                                    dateFondationTF.getPicker().getDate(),
                                    descriptionTF.getText(),
                                    telPortableTF.getText(),
                                    telFixTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(occurenceTF.getText()),
                                    selectedUtilisateur

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Business modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de business. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (nomGerantTF.getText().equals("")) {
            Dialog.show("Avertissement", "NomGerant vide", new Command("Ok"));
            return false;
        }


        if (prenomGerantTF.getText().equals("")) {
            Dialog.show("Avertissement", "PrenomGerant vide", new Command("Ok"));
            return false;
        }


        if (regionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Region vide", new Command("Ok"));
            return false;
        }


        if (adresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Adresse vide", new Command("Ok"));
            return false;
        }


        if (dateFondationTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateFondation", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (telPortableTF.getText().equals("")) {
            Dialog.show("Avertissement", "TelPortable vide", new Command("Ok"));
            return false;
        }


        if (telFixTF.getText().equals("")) {
            Dialog.show("Avertissement", "TelFix vide", new Command("Ok"));
            return false;
        }


        if (occurenceTF.getText().equals("")) {
            Dialog.show("Avertissement", "Occurence vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(occurenceTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", occurenceTF.getText() + " n'est pas un nombre valide (occurence)", new Command("Ok"));
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