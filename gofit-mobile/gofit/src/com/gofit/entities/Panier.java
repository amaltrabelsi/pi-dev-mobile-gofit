package com.gofit.entities;

public class Panier {

    private int id;
    private Utilisateur utilisateur;
    private Produit produit;

    public Panier() {
    }

    public Panier(int id, Utilisateur utilisateur, Produit produit) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.produit = produit;
    }

    public Panier(Utilisateur utilisateur, Produit produit) {
        this.utilisateur = utilisateur;
        this.produit = produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }


    @Override
    public String toString() {
        return "Panier : " +
                "id=" + id
                + ", Utilisateur=" + utilisateur
                + ", Produit=" + produit
                ;
    }


}