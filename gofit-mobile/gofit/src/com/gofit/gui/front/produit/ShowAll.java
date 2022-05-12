package com.gofit.gui.front.produit;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Produit;
import com.gofit.services.ProduitService;
import com.gofit.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class ShowAll extends Form {

    public static Produit currentProduit = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    PickerComponent sortPicker;
    ArrayList<Component> componentModels;
    Label refPLabel, prixUniLabel, quantiteLabel, descriptionLabel, nomProduitLabel, categorieLabel, imageLabel, noteLabel, totalnoteLabel, occurenceLabel, businessLabel;
    ImageViewer imageIV;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Produits", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Produit> listProduits = ProduitService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("RefP", "PrixUni", "Quantite", "Description", "NomProduit", "Categorie", "Image", "Note", "Totalnote", "Occurence", "Business").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listProduits);
            for (Produit produit : listProduits) {
                Component model = makeProduitModel(produit);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listProduits.size() > 0) {
            for (Produit produit : listProduits) {
                Component model = makeProduitModel(produit);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    private Container makeModelWithoutButtons(Produit produit) {
        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");


        refPLabel = new Label("RefP : " + produit.getRefP());
        refPLabel.setUIID("labelDefault");

        prixUniLabel = new Label("PrixUni : " + produit.getPrixUni());
        prixUniLabel.setUIID("labelDefault");

        quantiteLabel = new Label("Quantite : " + produit.getQuantite());
        quantiteLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + produit.getDescription());
        descriptionLabel.setUIID("labelDefault");

        nomProduitLabel = new Label("NomProduit : " + produit.getNomProduit());
        nomProduitLabel.setUIID("labelDefault");

        categorieLabel = new Label("Categorie : " + produit.getCategorie());
        categorieLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + produit.getImage());
        imageLabel.setUIID("labelDefault");

        noteLabel = new Label("Note : " + produit.getNote());
        noteLabel.setUIID("labelDefault");

        totalnoteLabel = new Label("Totalnote : " + produit.getTotalnote());
        totalnoteLabel.setUIID("labelDefault");

        occurenceLabel = new Label("Occurence : " + produit.getOccurence());
        occurenceLabel.setUIID("labelDefault");

        businessLabel = new Label("Business : " + produit.getBusiness());
        businessLabel.setUIID("labelDefault");

        businessLabel = new Label("Business : " + produit.getBusiness().getNom());
        businessLabel.setUIID("labelDefault");

        if (produit.getImage() != null) {
            String url = Statics.PRODUIT_IMAGE_URL + produit.getImage();
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

        produitModel.addAll(
                imageIV,
                refPLabel, prixUniLabel, quantiteLabel, descriptionLabel, nomProduitLabel, categorieLabel, noteLabel, totalnoteLabel, occurenceLabel, businessLabel
        );

        return produitModel;
    }

    private Component makeProduitModel(Produit produit) {

        Container produitModel = makeModelWithoutButtons(produit);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        Button btnAfficherScreenshot = new Button("Partager");

        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);

        produitModel.add(btnsContainer);

        return produitModel;
    }

}