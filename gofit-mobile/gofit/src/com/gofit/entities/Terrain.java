package com.gofit.entities;

public class Terrain {

    private int id;
    private String nom;
    private String description;
    private String activitie;
    private String contact;
    private String prix;
    private String image;
    private String region;

    public Terrain() {
    }

    public Terrain(int id, String nom, String description, String activitie, String contact, String prix, String image, String region) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.activitie = activitie;
        this.contact = contact;
        this.prix = prix;
        this.image = image;
        this.region = region;
    }

    public Terrain(String nom, String description, String activitie, String contact, String prix, String image, String region) {
        this.nom = nom;
        this.description = description;
        this.activitie = activitie;
        this.contact = contact;
        this.prix = prix;
        this.image = image;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivitie() {
        return activitie;
    }

    public void setActivitie(String activitie) {
        this.activitie = activitie;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


}