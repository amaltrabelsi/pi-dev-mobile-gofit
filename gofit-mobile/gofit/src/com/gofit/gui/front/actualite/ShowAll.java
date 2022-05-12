package com.gofit.gui.front.actualite;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Actualite;
import com.gofit.services.ActualiteService;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Actualite currentActualite = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label titreLabel, descriptionLabel, contenuLabel, categorieLabel, imageLabel, utilisateurLabel;
    ImageViewer imageIV;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Actualites", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Actualite> listActualites = ActualiteService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher actualite par Titre");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Actualite actualite : listActualites) {
                if (actualite.getTitre().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeActualiteModel(actualite);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listActualites.size() > 0) {
            for (Actualite actualite : listActualites) {
                Component model = makeActualiteModel(actualite);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    private Container makeModelWithoutButtons(Actualite actualite) {
        Container actualiteModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        actualiteModel.setUIID("containerRounded");


        titreLabel = new Label("Titre : " + actualite.getTitre());
        titreLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + actualite.getDescription());
        descriptionLabel.setUIID("labelDefault");

        contenuLabel = new Label("Contenu : " + actualite.getContenu());
        contenuLabel.setUIID("labelDefault");

        categorieLabel = new Label("Categorie : " + actualite.getCategorie());
        categorieLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + actualite.getImage());
        imageLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + actualite.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + actualite.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        if (actualite.getImage() != null) {
            String url = Statics.ACTUALITE_IMAGE_URL + actualite.getImage();
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

        actualiteModel.addAll(
                imageIV,
                titreLabel, descriptionLabel, contenuLabel, categorieLabel, utilisateurLabel
        );

        return actualiteModel;
    }

    private Component makeActualiteModel(Actualite actualite) {

        Container actualiteModel = makeModelWithoutButtons(actualite);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(actualite));

        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);

        actualiteModel.add(btnsContainer);

        return actualiteModel;
    }

    private void share(Actualite actualite) {
        Form form = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        form.add(makeModelWithoutButtons(actualite));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager actualite", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(actualite.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        screenShotForm.show();
    }

}