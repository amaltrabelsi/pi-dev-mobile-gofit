package com.gofit.gui.front.reclamation;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.gofit.MainApp;
import com.gofit.entities.Reclamation;
import com.gofit.entities.Terrain;
import com.gofit.entities.Utilisateur;
import com.gofit.services.ReclamationService;
import com.gofit.services.TerrainService;
import com.gofit.services.UtilisateurService;
import com.gofit.utils.AlertUtils;

import java.util.ArrayList;

public class Manage extends Form {


    Reclamation currentReclamation;

    TextField contenuTF;
    Label contenuLabel;


    ArrayList<Terrain> listTerrains;
    PickerComponent terrainPC;
    Terrain selectedTerrain = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentReclamation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReclamation = ShowAll.currentReclamation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] terrainStrings;
        int terrainIndex;
        terrainPC = PickerComponent.createStrings("").label("Terrain");
        listTerrains = TerrainService.getInstance().getAll();
        terrainStrings = new String[listTerrains.size()];
        terrainIndex = 0;
        for (Terrain terrain : listTerrains) {
            terrainStrings[terrainIndex] = terrain.getNom();
            terrainIndex++;
        }
        if (listTerrains.size() > 0) {
            terrainPC.getPicker().setStrings(terrainStrings);
            terrainPC.getPicker().addActionListener(l -> selectedTerrain = listTerrains.get(terrainPC.getPicker().getSelectedStringIndex()));
        } else {
            terrainPC.getPicker().setStrings("");
        }


        contenuLabel = new Label("Contenu : ");
        contenuLabel.setUIID("labelDefault");
        contenuTF = new TextField();
        contenuTF.setHint("Tapez le contenu");


        if (currentReclamation == null) {


            manageButton = new Button("Ajouter");
        } else {
            contenuTF.setText(currentReclamation.getContenu());


            terrainPC.getPicker().setSelectedString(currentReclamation.getTerrain().getNom());
            selectedTerrain = currentReclamation.getTerrain();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                contenuLabel, contenuTF,


                terrainPC, 
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentReclamation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReclamationService.getInstance().add(
                            new Reclamation(


                                    contenuTF.getText(),
                                    selectedTerrain,
                                    MainApp.getSession()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reclamation ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReclamationService.getInstance().edit(
                            new Reclamation(
                                    currentReclamation.getId(),


                                    contenuTF.getText(),
                                    selectedTerrain,
                                    MainApp.getSession()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reclamation modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (contenuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contenu vide", new Command("Ok"));
            return false;
        }


        if (selectedTerrain == null) {
            Dialog.show("Avertissement", "Veuillez choisir un terrain", new Command("Ok"));
            return false;
        }


        return true;
    }
}