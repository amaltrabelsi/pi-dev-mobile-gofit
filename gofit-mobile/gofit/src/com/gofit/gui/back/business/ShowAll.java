package com.gofit.gui.back.business;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.gofit.entities.Business;
import com.gofit.services.BusinessService;
import com.gofit.utils.Statics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Business currentBusiness = null;
    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, nomGerantLabel, prenomGerantLabel, regionLabel, adresseLabel, dateFondationLabel, descriptionLabel, telPortableLabel, telFixLabel, imageLabel, occurenceLabel, utilisateurLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Businesss", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Business> listBusinesss = BusinessService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher business par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Business business : listBusinesss) {
                if (business.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeBusinessModel(business);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listBusinesss.size() > 0) {
            for (Business business : listBusinesss) {
                Component model = makeBusinessModel(business);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentBusiness = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Business business) {
        Container businessModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        businessModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + business.getNom());
        nomLabel.setUIID("labelDefault");

        nomGerantLabel = new Label("NomGerant : " + business.getNomGerant());
        nomGerantLabel.setUIID("labelDefault");

        prenomGerantLabel = new Label("PrenomGerant : " + business.getPrenomGerant());
        prenomGerantLabel.setUIID("labelDefault");

        regionLabel = new Label("Region : " + business.getRegion());
        regionLabel.setUIID("labelDefault");

        adresseLabel = new Label("Adresse : " + business.getAdresse());
        adresseLabel.setUIID("labelDefault");

        dateFondationLabel = new Label("DateFondation : " + new SimpleDateFormat("dd-MM-yyyy").format(business.getDateFondation()));
        dateFondationLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + business.getDescription());
        descriptionLabel.setUIID("labelDefault");

        telPortableLabel = new Label("TelPortable : " + business.getTelPortable());
        telPortableLabel.setUIID("labelDefault");

        telFixLabel = new Label("TelFix : " + business.getTelFix());
        telFixLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + business.getImage());
        imageLabel.setUIID("labelDefault");

        occurenceLabel = new Label("Occurence : " + business.getOccurence());
        occurenceLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + business.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + business.getUtilisateur().getEmail());
        utilisateurLabel.setUIID("labelDefault");

        if (business.getImage() != null) {
            String url = Statics.BUSINESS_IMAGE_URL + business.getImage();
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

        businessModel.addAll(
                imageIV,
                nomLabel, nomGerantLabel, prenomGerantLabel, regionLabel, adresseLabel, dateFondationLabel, descriptionLabel, telPortableLabel, telFixLabel, occurenceLabel, utilisateurLabel
        );

        return businessModel;
    }

    private Component makeBusinessModel(Business business) {

        Container businessModel = makeModelWithoutButtons(business);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentBusiness = business;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce business ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = BusinessService.getInstance().delete(business.getId());

                if (responseCode == 200) {
                    currentBusiness = null;
                    dlg.dispose();
                    businessModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du business. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        businessModel.add(btnsContainer);

        return businessModel;
    }

}