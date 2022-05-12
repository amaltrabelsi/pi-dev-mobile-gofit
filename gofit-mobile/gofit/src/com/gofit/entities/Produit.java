package com.gofit.entities;

import com.gofit.utils.Statics;

public class Produit implements Comparable<Produit> {

    private int id;
    private String refP;
    private float prixUni;
    private int quantite;
    private String description;
    private String nomProduit;
    private String categorie;
    private String image;
    private int note;
    private int totalnote;
    private int occurence;
    private Business business;

    public Produit() {
    }

    public Produit(int id, String refP, float prixUni, int quantite, String description, String nomProduit, String categorie, String image, int note, int totalnote, int occurence, Business business) {
        this.id = id;
        this.refP = refP;
        this.prixUni = prixUni;
        this.quantite = quantite;
        this.description = description;
        this.nomProduit = nomProduit;
        this.categorie = categorie;
        this.image = image;
        this.note = note;
        this.totalnote = totalnote;
        this.occurence = occurence;
        this.business = business;
    }

    public Produit(String refP, float prixUni, int quantite, String description, String nomProduit, String categorie, String image, int note, int totalnote, int occurence, Business business) {
        this.refP = refP;
        this.prixUni = prixUni;
        this.quantite = quantite;
        this.description = description;
        this.nomProduit = nomProduit;
        this.categorie = categorie;
        this.image = image;
        this.note = note;
        this.totalnote = totalnote;
        this.occurence = occurence;
        this.business = business;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefP() {
        return refP;
    }

    public void setRefP(String refP) {
        this.refP = refP;
    }

    public float getPrixUni() {
        return prixUni;
    }

    public void setPrixUni(float prixUni) {
        this.prixUni = prixUni;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getTotalnote() {
        return totalnote;
    }

    public void setTotalnote(int totalnote) {
        this.totalnote = totalnote;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }


    @Override
    public String toString() {
        return "Produit : " +
                "id=" + id
                + ", RefP=" + refP
                + ", PrixUni=" + prixUni
                + ", Quantite=" + quantite
                + ", Description=" + description
                + ", NomProduit=" + nomProduit
                + ", Categorie=" + categorie
                + ", Image=" + image
                + ", Note=" + note
                + ", Totalnote=" + totalnote
                + ", Occurence=" + occurence
                + ", Business=" + business
                ;
    }

    @Override
    public int compareTo(Produit produit) {
        switch (Statics.compareVar) {
            case "RefP":
                return this.getRefP().compareTo(produit.getRefP());
            case "PrixUni":
                return Float.compare(this.getPrixUni(), produit.getPrixUni());
            case "Quantite":
                return Integer.compare(this.getQuantite(), produit.getQuantite());
            case "Description":
                return this.getDescription().compareTo(produit.getDescription());
            case "NomProduit":
                return this.getNomProduit().compareTo(produit.getNomProduit());
            case "Categorie":
                return this.getCategorie().compareTo(produit.getCategorie());
            case "Image":
                return this.getImage().compareTo(produit.getImage());
            case "Note":
                return Integer.compare(this.getNote(), produit.getNote());
            case "Totalnote":
                return Integer.compare(this.getTotalnote(), produit.getTotalnote());
            case "Occurence":
                return Integer.compare(this.getOccurence(), produit.getOccurence());
            case "Business":
                return this.getBusiness().getNom().compareTo(produit.getBusiness().getNom());

            default:
                return 0;
        }
    }

}