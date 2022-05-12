package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Business;
import com.gofit.entities.Utilisateur;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusinessService {

    public static BusinessService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Business> listBusinesss;


    private BusinessService() {
        cr = new ConnectionRequest();
    }

    public static BusinessService getInstance() {
        if (instance == null) {
            instance = new BusinessService();
        }
        return instance;
    }

    public ArrayList<Business> getAll() {
        listBusinesss = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/business");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listBusinesss = getList();
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

        return listBusinesss;
    }

    private ArrayList<Business> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Business business = new Business(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("nom"),
                        (String) obj.get("nomGerant"),
                        (String) obj.get("prenomGerant"),
                        (String) obj.get("region"),
                        (String) obj.get("adresse"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateFondation")),
                        (String) obj.get("description"),
                        (String) obj.get("telPortable"),
                        (String) obj.get("telFix"),
                        (String) obj.get("image"),
                        (int) Float.parseFloat(obj.get("occurence").toString()),
                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur"))

                );

                listBusinesss.add(business);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listBusinesss;
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

    public int add(Business business) {
        return manage(business, false, true);
    }

    public int edit(Business business, boolean imageEdited) {
        return manage(business, true, imageEdited);
    }

    public int manage(Business business, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Business.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/business/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(business.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/business/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", business.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", business.getImage());
        }

        cr.addArgumentNoEncoding("nom", business.getNom());
        cr.addArgumentNoEncoding("nomGerant", business.getNomGerant());
        cr.addArgumentNoEncoding("prenomGerant", business.getPrenomGerant());
        cr.addArgumentNoEncoding("region", business.getRegion());
        cr.addArgumentNoEncoding("adresse", business.getAdresse());
        cr.addArgumentNoEncoding("dateFondation", new SimpleDateFormat("dd-MM-yyyy").format(business.getDateFondation()));
        cr.addArgumentNoEncoding("description", business.getDescription());
        cr.addArgumentNoEncoding("telPortable", business.getTelPortable());
        cr.addArgumentNoEncoding("telFix", business.getTelFix());
        cr.addArgumentNoEncoding("image", business.getImage());
        cr.addArgumentNoEncoding("occurence", String.valueOf(business.getOccurence()));
        cr.addArgumentNoEncoding("utilisateur", String.valueOf(business.getUtilisateur().getId()));


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

    public int delete(int businessId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/business/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(businessId));

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
