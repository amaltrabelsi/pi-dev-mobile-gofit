package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Panier;
import com.gofit.entities.Produit;
import com.gofit.entities.Utilisateur;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanierService {

    public static PanierService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Panier> listPaniers;


    private PanierService() {
        cr = new ConnectionRequest();
    }

    public static PanierService getInstance() {
        if (instance == null) {
            instance = new PanierService();
        }
        return instance;
    }

    public ArrayList<Panier> getAll() {
        listPaniers = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/panier");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listPaniers = getList();
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

        return listPaniers;
    }

    private ArrayList<Panier> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Panier panier = new Panier(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur")),
                        makeProduit((Map<String, Object>) obj.get("produit"))

                );

                listPaniers.add(panier);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listPaniers;
    }

    public Utilisateur makeUtilisateur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId((int) Float.parseFloat(obj.get("id").toString()));
        utilisateur.setEmail((String) obj.get("email"));
        return utilisateur;
    }

    public Produit makeProduit(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId((int) Float.parseFloat(obj.get("id").toString()));
        produit.setNomProduit((String) obj.get("nomProduit"));
        return produit;
    }

    public int add(Panier panier) {
        return manage(panier, false);
    }

    public int edit(Panier panier) {
        return manage(panier, true);
    }

    public int manage(Panier panier, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/panier/edit");
            cr.addArgument("id", String.valueOf(panier.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/panier/add");
        }

        cr.addArgument("utilisateur", String.valueOf(panier.getUtilisateur().getId()));
        cr.addArgument("produit", String.valueOf(panier.getProduit().getId()));


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

    public int delete(int panierId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/panier/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(panierId));

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
