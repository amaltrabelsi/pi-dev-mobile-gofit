package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Business;
import com.gofit.entities.Produit;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProduitService {

    public static ProduitService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Produit> listProduits;


    private ProduitService() {
        cr = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public ArrayList<Produit> getAll() {
        listProduits = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/produit");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listProduits = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listProduits;
    }

    private ArrayList<Produit> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Produit produit = new Produit(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("refP"),
                        Float.parseFloat(obj.get("prixUni").toString()),
                        (int) Float.parseFloat(obj.get("quantite").toString()),
                        (String) obj.get("description"),
                        (String) obj.get("nomProduit"),
                        (String) obj.get("categorie"),
                        (String) obj.get("image"),
                        (int) Float.parseFloat(obj.get("note").toString()),
                        (int) Float.parseFloat(obj.get("totalnote").toString()),
                        (int) Float.parseFloat(obj.get("occurence").toString()),
                        makeBusiness((Map<String, Object>) obj.get("business"))

                );

                listProduits.add(produit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listProduits;
    }

    public Business makeBusiness(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Business business = new Business();
        business.setId((int) Float.parseFloat(obj.get("id").toString()));
        business.setNom((String) obj.get("nom"));
        return business;
    }

    public int add(Produit produit) {
        return manage(produit, false, true);
    }

    public int edit(Produit produit, boolean imageEdited) {
        return manage(produit, true, imageEdited);
    }

    public int manage(Produit produit, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Produit.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/produit/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(produit.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/produit/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", produit.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", produit.getImage());
        }

        cr.addArgumentNoEncoding("refP", produit.getRefP());
        cr.addArgumentNoEncoding("prixUni", String.valueOf(produit.getPrixUni()));
        cr.addArgumentNoEncoding("quantite", String.valueOf(produit.getQuantite()));
        cr.addArgumentNoEncoding("description", produit.getDescription());
        cr.addArgumentNoEncoding("nomProduit", produit.getNomProduit());
        cr.addArgumentNoEncoding("categorie", produit.getCategorie());
        cr.addArgumentNoEncoding("image", produit.getImage());
        cr.addArgumentNoEncoding("note", String.valueOf(produit.getNote()));
        cr.addArgumentNoEncoding("totalnote", String.valueOf(produit.getTotalnote()));
        cr.addArgumentNoEncoding("occurence", String.valueOf(produit.getOccurence()));
        cr.addArgumentNoEncoding("business", String.valueOf(produit.getBusiness().getId()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int produitId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/produit/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(produitId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
