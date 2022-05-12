package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Utilisateur;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilisateurService {

    public static UtilisateurService instance = null;
    public int resultCode;
    Utilisateur utilisateur;
    private ConnectionRequest cr;
    private ArrayList<Utilisateur> listUtilisateurs;

    private UtilisateurService() {
        cr = new ConnectionRequest();
    }

    public static UtilisateurService getInstance() {
        if (instance == null) {
            instance = new UtilisateurService();
        }
        return instance;
    }

    public Utilisateur getUtilisateurById(int idUtilisateur) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateur/show");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(idUtilisateur));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    utilisateur = getOne();
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

        return utilisateur;
    }

    public Utilisateur checkCredentials(String email, String password) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateur/verif");
        cr.setHttpMethod("POST");
        cr.addArgument("email", email);
        cr.addArgument("password", password);

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    utilisateur = getOne();
                } else {
                    utilisateur = null;
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

        return utilisateur;
    }


    private Utilisateur getOne() {
        try {
            Map<String, Object> obj = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));

            return new Utilisateur(
                    (int) Float.parseFloat(obj.get("id").toString()),

                    (String) obj.get("email"),
                    (String) obj.get("role"),
                    (String) obj.get("password"),
                    (String) obj.get("num")

            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Utilisateur> getAll() {
        listUtilisateurs = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateur");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listUtilisateurs = getList();
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

        return listUtilisateurs;
    }

    private ArrayList<Utilisateur> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Utilisateur utilisateur = new Utilisateur(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("email"),
                        (String) obj.get("role"),
                        (String) obj.get("password"),
                        (String) obj.get("num")

                );

                listUtilisateurs.add(utilisateur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listUtilisateurs;
    }

    public int add(Utilisateur utilisateur) {
        return manage(utilisateur, false);
    }

    public int edit(Utilisateur utilisateur) {
        return manage(utilisateur, true);
    }

    public int manage(Utilisateur utilisateur, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/utilisateur/edit");
            cr.addArgument("id", String.valueOf(utilisateur.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/utilisateur/add");
        }

        cr.addArgument("email", utilisateur.getEmail());
        cr.addArgument("role", utilisateur.getRole());
        cr.addArgument("password", utilisateur.getPassword());
        cr.addArgument("num", utilisateur.getNum());


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

    public int delete(int utilisateurId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateur/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(utilisateurId));

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
