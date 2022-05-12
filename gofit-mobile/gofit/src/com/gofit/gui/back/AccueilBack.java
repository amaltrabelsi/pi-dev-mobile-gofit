package com.gofit.gui.back;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.gui.Login;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilBack() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Admin"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeUtilisateursButton(),
                makeTerrainsButton(),
                makeReclamationsButton(),
                makeCommandesButton(),
                makePaniersButton(),
                makeProduitsButton(),
                makeActualitesButton(),
                makeBusinesssButton()
        );

        this.add(menuContainer);
    }

    private Button makeUtilisateursButton() {
        Button button = new Button("Utilisateurs");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.utilisateur.ShowAll(this).show());
        return button;
    }

    private Button makeTerrainsButton() {
        Button button = new Button("Terrains");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.terrain.ShowAll(this).show());
        return button;
    }

    private Button makeReclamationsButton() {
        Button button = new Button("Reclamations");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.reclamation.ShowAll(this).show());
        return button;
    }

    private Button makeCommandesButton() {
        Button button = new Button("Commandes");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.commande.ShowAll(this).show());
        return button;
    }

    private Button makePaniersButton() {
        Button button = new Button("Paniers");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.panier.ShowAll(this).show());
        return button;
    }

    private Button makeProduitsButton() {
        Button button = new Button("Produits");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.produit.ShowAll(this).show());
        return button;
    }

    private Button makeActualitesButton() {
        Button button = new Button("Actualites");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.actualite.ShowAll(this).show());
        return button;
    }

    private Button makeBusinesssButton() {
        Button button = new Button("Businesss");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.gofit.gui.back.business.ShowAll(this).show());
        return button;
    }
}
