package com.gofit.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.gofit.entities.Commande;
import com.gofit.entities.Utilisateur;
import com.gofit.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandeService {

    public static CommandeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commande> listCommandes;


    private CommandeService() {
        cr = new ConnectionRequest();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }

    public ArrayList<Commande> getAll() {
        listCommandes = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommandes = getList();
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

        return listCommandes;
    }

    private ArrayList<Commande> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commande commande = new Commande(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateC")),
                        Float.parseFloat(obj.get("total").toString()),
                        (int) Float.parseFloat(obj.get("nbProduit").toString()),
                        (String) obj.get("modePaiement"),
                        (int) Float.parseFloat(obj.get("statut").toString()),
                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur"))

                );

                listCommandes.add(commande);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listCommandes;
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

    public int add(Commande commande) {
        return manage(commande, false);
    }

    public int edit(Commande commande) {
        return manage(commande, true);
    }

    public int manage(Commande commande, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/commande/edit");
            cr.addArgument("id", String.valueOf(commande.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/commande/add");
        }

        cr.addArgument("dateC", new SimpleDateFormat("dd-MM-yyyy").format(commande.getDateC()));
        cr.addArgument("total", String.valueOf(commande.getTotal()));
        cr.addArgument("nbProduit", String.valueOf(commande.getNbProduit()));
        cr.addArgument("modePaiement", commande.getModePaiement());
        cr.addArgument("statut", String.valueOf(commande.getStatut()));
        cr.addArgument("utilisateur", String.valueOf(commande.getUtilisateur().getId()));


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

    public int delete(int commandeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commandeId));

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
