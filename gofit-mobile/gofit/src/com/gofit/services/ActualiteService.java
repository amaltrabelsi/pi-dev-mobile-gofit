package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Actualite;
import com.gofit.entities.Utilisateur;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActualiteService {

    public static ActualiteService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Actualite> listActualites;


    private ActualiteService() {
        cr = new ConnectionRequest();
    }

    public static ActualiteService getInstance() {
        if (instance == null) {
            instance = new ActualiteService();
        }
        return instance;
    }

    public ArrayList<Actualite> getAll() {
        listActualites = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/actualite");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listActualites = getList();
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

        return listActualites;
    }

    private ArrayList<Actualite> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Actualite actualite = new Actualite(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("titre"),
                        (String) obj.get("description"),
                        (String) obj.get("contenu"),
                        (String) obj.get("categorie"),
                        (String) obj.get("image"),
                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur"))

                );

                listActualites.add(actualite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listActualites;
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

    public int add(Actualite actualite) {
        return manage(actualite, false, true);
    }

    public int edit(Actualite actualite, boolean imageEdited) {
        return manage(actualite, true, imageEdited);
    }

    public int manage(Actualite actualite, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Actualite.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/actualite/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(actualite.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/actualite/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", actualite.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", actualite.getImage());
        }

        cr.addArgumentNoEncoding("titre", actualite.getTitre());
        cr.addArgumentNoEncoding("description", actualite.getDescription());
        cr.addArgumentNoEncoding("contenu", actualite.getContenu());
        cr.addArgumentNoEncoding("categorie", actualite.getCategorie());
        cr.addArgumentNoEncoding("image", actualite.getImage());
        cr.addArgumentNoEncoding("utilisateur", String.valueOf(actualite.getUtilisateur().getId()));


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

    public int delete(int actualiteId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/actualite/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(actualiteId));

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
