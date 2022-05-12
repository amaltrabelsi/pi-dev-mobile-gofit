package com.gofit.gui.back.terrain;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Terrain;
import com.gofit.services.TerrainService;
import com.gofit.utils.Statics;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Terrain currentTerrain = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, descriptionLabel, activitieLabel, contactLabel, prixLabel, imageLabel, regionLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Terrains", new BoxLayout(BoxLayout.Y_AXIS));
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
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Terrain> listTerrains = TerrainService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher terrain par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Terrain terrain : listTerrains) {
                if (terrain.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeTerrainModel(terrain);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listTerrains.size() > 0) {
            for (Terrain terrain : listTerrains) {
                Component model = makeTerrainModel(terrain);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentTerrain = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Terrain terrain) {
        Container terrainModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        terrainModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + terrain.getNom());
        nomLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + terrain.getDescription());
        descriptionLabel.setUIID("labelDefault");

        activitieLabel = new Label("Activitie : " + terrain.getActivitie());
        activitieLabel.setUIID("labelDefault");

        contactLabel = new Label("Contact : " + terrain.getContact());
        contactLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + terrain.getPrix());
        prixLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + terrain.getImage());
        imageLabel.setUIID("labelDefault");

        regionLabel = new Label("Region : " + terrain.getRegion());
        regionLabel.setUIID("labelDefault");

        if (terrain.getImage() != null) {
            String url = Statics.TERRAIN_IMAGE_URL + terrain.getImage();
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

        terrainModel.addAll(
                imageIV,
                nomLabel, descriptionLabel, activitieLabel, contactLabel, prixLabel, regionLabel
        );

        return terrainModel;
    }

    private Component makeTerrainModel(Terrain terrain) {

        Container terrainModel = makeModelWithoutButtons(terrain);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentTerrain = terrain;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce terrain ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = TerrainService.getInstance().delete(terrain.getId());

                if (responseCode == 200) {
                    currentTerrain = null;
                    dlg.dispose();
                    terrainModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du terrain. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        terrainModel.add(btnsContainer);

        return terrainModel;
    }

}