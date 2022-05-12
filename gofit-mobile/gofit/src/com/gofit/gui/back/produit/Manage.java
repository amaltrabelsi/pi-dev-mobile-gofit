package com.gofit.gui.back.produit;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Business;
import com.gofit.entities.Produit;
import com.gofit.services.BusinessService;
import com.gofit.services.ProduitService;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Produit currentProduit;

    TextField refPTF;
    TextField prixUniTF;
    TextField quantiteTF;
    TextField descriptionTF;
    TextField nomProduitTF;
    TextField categorieTF;
    TextField imageTF;
    TextField noteTF;
    TextField totalnoteTF;
    TextField occurenceTF;
    Label refPLabel;
    Label prixUniLabel;
    Label quantiteLabel;
    Label descriptionLabel;
    Label nomProduitLabel;
    Label categorieLabel;
    Label imageLabel;
    Label noteLabel;
    Label totalnoteLabel;
    Label occurenceLabel;


    ArrayList<Business> listBusinesss;
    PickerComponent businessPC;
    Business selectedBusiness = null;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentProduit == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentProduit = ShowAll.currentProduit;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] businessStrings;
        int businessIndex;
        businessPC = PickerComponent.createStrings("").label("Business");
        listBusinesss = BusinessService.getInstance().getAll();
        businessStrings = new String[listBusinesss.size()];
        businessIndex = 0;
        for (Business business : listBusinesss) {
            businessStrings[businessIndex] = business.getNom();
            businessIndex++;
        }
        if (listBusinesss.size() > 0) {
            businessPC.getPicker().setStrings(businessStrings);
            businessPC.getPicker().addActionListener(l -> selectedBusiness = listBusinesss.get(businessPC.getPicker().getSelectedStringIndex()));
        } else {
            businessPC.getPicker().setStrings("");
        }


        refPLabel = new Label("RefP : ");
        refPLabel.setUIID("labelDefault");
        refPTF = new TextField();
        refPTF.setHint("Tapez le refP");


        prixUniLabel = new Label("PrixUni : ");
        prixUniLabel.setUIID("labelDefault");
        prixUniTF = new TextField();
        prixUniTF.setHint("Tapez le prixUni");


        quantiteLabel = new Label("Quantite : ");
        quantiteLabel.setUIID("labelDefault");
        quantiteTF = new TextField();
        quantiteTF.setHint("Tapez le quantite");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        nomProduitLabel = new Label("NomProduit : ");
        nomProduitLabel.setUIID("labelDefault");
        nomProduitTF = new TextField();
        nomProduitTF.setHint("Tapez le nomProduit");


        categorieLabel = new Label("Categorie : ");
        categorieLabel.setUIID("labelDefault");
        categorieTF = new TextField();
        categorieTF.setHint("Tapez le categorie");


        noteLabel = new Label("Note : ");
        noteLabel.setUIID("labelDefault");
        noteTF = new TextField();
        noteTF.setHint("Tapez le note");


        totalnoteLabel = new Label("Totalnote : ");
        totalnoteLabel.setUIID("labelDefault");
        totalnoteTF = new TextField();
        totalnoteTF.setHint("Tapez le totalnote");


        occurenceLabel = new Label("Occurence : ");
        occurenceLabel.setUIID("labelDefault");
        occurenceTF = new TextField();
        occurenceTF.setHint("Tapez le occurence");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentProduit == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {
            refPTF.setText(currentProduit.getRefP());
            prixUniTF.setText(String.valueOf(currentProduit.getPrixUni()));
            quantiteTF.setText(String.valueOf(currentProduit.getQuantite()));
            descriptionTF.setText(currentProduit.getDescription());
            nomProduitTF.setText(currentProduit.getNomProduit());
            categorieTF.setText(currentProduit.getCategorie());

            noteTF.setText(String.valueOf(currentProduit.getNote()));
            totalnoteTF.setText(String.valueOf(currentProduit.getTotalnote()));
            occurenceTF.setText(String.valueOf(currentProduit.getOccurence()));


            businessPC.getPicker().setSelectedString(currentProduit.getBusiness().getNom());
            selectedBusiness = currentProduit.getBusiness();


            if (currentProduit.getImage() != null) {
                selectedImage = currentProduit.getImage();
                String url = Statics.PRODUIT_IMAGE_URL + currentProduit.getImage();
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
                refPLabel, refPTF,
                prixUniLabel, prixUniTF,
                quantiteLabel, quantiteTF,
                descriptionLabel, descriptionTF,
                nomProduitLabel, nomProduitTF,
                categorieLabel, categorieTF,

                noteLabel, noteTF,
                totalnoteLabel, totalnoteTF,
                occurenceLabel, occurenceTF,

                businessPC,
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

        if (currentProduit == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().add(
                            new Produit(


                                    refPTF.getText(),
                                    Float.parseFloat(prixUniTF.getText()),
                                    (int) Float.parseFloat(quantiteTF.getText()),
                                    descriptionTF.getText(),
                                    nomProduitTF.getText(),
                                    categorieTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(noteTF.getText()),
                                    (int) Float.parseFloat(totalnoteTF.getText()),
                                    (int) Float.parseFloat(occurenceTF.getText()),
                                    selectedBusiness
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Produit ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de produit. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().edit(
                            new Produit(
                                    currentProduit.getId(),


                                    refPTF.getText(),
                                    Float.parseFloat(prixUniTF.getText()),
                                    (int) Float.parseFloat(quantiteTF.getText()),
                                    descriptionTF.getText(),
                                    nomProduitTF.getText(),
                                    categorieTF.getText(),
                                    selectedImage,
                                    (int) Float.parseFloat(noteTF.getText()),
                                    (int) Float.parseFloat(totalnoteTF.getText()),
                                    (int) Float.parseFloat(occurenceTF.getText()),
                                    selectedBusiness

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Produit modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de produit. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (refPTF.getText().equals("")) {
            Dialog.show("Avertissement", "RefP vide", new Command("Ok"));
            return false;
        }


        if (prixUniTF.getText().equals("")) {
            Dialog.show("Avertissement", "PrixUni vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixUniTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixUniTF.getText() + " n'est pas un nombre valide (prixUni)", new Command("Ok"));
            return false;
        }


        if (quantiteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Quantite vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(quantiteTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", quantiteTF.getText() + " n'est pas un nombre valide (quantite)", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (nomProduitTF.getText().equals("")) {
            Dialog.show("Avertissement", "NomProduit vide", new Command("Ok"));
            return false;
        }


        if (categorieTF.getText().equals("")) {
            Dialog.show("Avertissement", "Categorie vide", new Command("Ok"));
            return false;
        }


        if (noteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Note vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(noteTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", noteTF.getText() + " n'est pas un nombre valide (note)", new Command("Ok"));
            return false;
        }


        if (totalnoteTF.getText().equals("")) {
            Dialog.show("Avertissement", "Totalnote vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(totalnoteTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", totalnoteTF.getText() + " n'est pas un nombre valide (totalnote)", new Command("Ok"));
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


        if (selectedBusiness == null) {
            Dialog.show("Avertissement", "Veuillez choisir un business", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}