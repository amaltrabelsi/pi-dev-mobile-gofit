package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Terrain;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TerrainService {

    public static TerrainService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Terrain> listTerrains;


    private TerrainService() {
        cr = new ConnectionRequest();
    }

    public static TerrainService getInstance() {
        if (instance == null) {
            instance = new TerrainService();
        }
        return instance;
    }

    public ArrayList<Terrain> getAll() {
        listTerrains = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/terrain");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listTerrains = getList();
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

        return listTerrains;
    }

    private ArrayList<Terrain> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Terrain terrain = new Terrain(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("nom"),
                        (String) obj.get("description"),
                        (String) obj.get("activitie"),
                        (String) obj.get("contact"),
                        (String) obj.get("prix"),
                        (String) obj.get("image"),
                        (String) obj.get("region")

                );

                listTerrains.add(terrain);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listTerrains;
    }

    public int add(Terrain terrain) {
        return manage(terrain, false, true);
    }

    public int edit(Terrain terrain, boolean imageEdited) {
        return manage(terrain, true, imageEdited);
    }

    public int manage(Terrain terrain, boolean isEdit, boolean imageEdited) {

        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Terrain.jpg");


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/terrain/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(terrain.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/terrain/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", terrain.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", terrain.getImage());
        }

        cr.addArgumentNoEncoding("nom", terrain.getNom());
        cr.addArgumentNoEncoding("description", terrain.getDescription());
        cr.addArgumentNoEncoding("activitie", terrain.getActivitie());
        cr.addArgumentNoEncoding("contact", terrain.getContact());
        cr.addArgumentNoEncoding("prix", terrain.getPrix());
        cr.addArgumentNoEncoding("image", terrain.getImage());
        cr.addArgumentNoEncoding("region", terrain.getRegion());


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

    public int delete(int terrainId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/terrain/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(terrainId));

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
