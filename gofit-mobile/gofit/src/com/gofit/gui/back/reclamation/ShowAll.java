package com.gofit.gui.back.reclamation;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.entities.Reclamation;
import com.gofit.services.ReclamationService;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Reclamation currentReclamation = null;
    Form previous;
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label contenuLabel, terrainLabel, utilisateurLabel;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Reclamations", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Reclamation> listReclamations = ReclamationService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher reclamation par Contenu");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Reclamation reclamation : listReclamations) {
                if (reclamation.getContenu().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeReclamationModel(reclamation);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listReclamations.size() > 0) {
            for (Reclamation reclamation : listReclamations) {
                Component model = makeReclamationModel(reclamation);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    private Container makeModelWithoutButtons(Reclamation reclamation) {
        Container reclamationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reclamationModel.setUIID("containerRounded");


        contenuLabel = new Label("Contenu : " + reclamation.getContenu());
        contenuLabel.setUIID("labelDefault");

        terrainLabel = new Label("Terrain : " + reclamation.getTerrain());
        terrainLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + reclamation.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        terrainLabel = new Label("Terrain : " + reclamation.getTerrain().getNom());
        terrainLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + reclamation.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");


        reclamationModel.addAll(

                contenuLabel, terrainLabel, utilisateurLabel
        );

        return reclamationModel;
    }

    private Component makeReclamationModel(Reclamation reclamation) {

        Container reclamationModel = makeModelWithoutButtons(reclamation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        reclamationModel.add(btnsContainer);

        return reclamationModel;
    }

}