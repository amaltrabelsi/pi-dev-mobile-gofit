package com.gofit.entities;

import java.util.Date;

public class Commande {

    private int id;
    private Date dateC;
    private float total;
    private int nbProduit;
    private String modePaiement;
    private int statut;
    private Utilisateur utilisateur;

    public Commande() {
    }

    public Commande(int id, Date dateC, float total, int nbProduit, String modePaiement, int statut, Utilisateur utilisateur) {
        this.id = id;
        this.dateC = dateC;
        this.total = total;
        this.nbProduit = nbProduit;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.utilisateur = utilisateur;
    }

    public Commande(Date dateC, float total, int nbProduit, String modePaiement, int statut, Utilisateur utilisateur) {
        this.dateC = dateC;
        this.total = total;
        this.nbProduit = nbProduit;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getNbProduit() {
        return nbProduit;
    }

    public void setNbProduit(int nbProduit) {
        this.nbProduit = nbProduit;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    @Override
    public String toString() {
        return "Commande : " +
                "id=" + id
                + ", DateC=" + dateC
                + ", Total=" + total
                + ", NbProduit=" + nbProduit
                + ", ModePaiement=" + modePaiement
                + ", Statut=" + statut
                + ", Utilisateur=" + utilisateur
                ;
    }


}