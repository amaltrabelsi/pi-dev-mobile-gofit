package com.gofit.entities;

import java.util.Date;

public class Business {

    private int id;
    private String nom;
    private String nomGerant;
    private String prenomGerant;
    private String region;
    private String adresse;
    private Date dateFondation;
    private String description;
    private String telPortable;
    private String telFix;
    private String image;
    private int occurence;
    private Utilisateur utilisateur;

    public Business() {
    }

    public Business(int id, String nom, String nomGerant, String prenomGerant, String region, String adresse, Date dateFondation, String description, String telPortable, String telFix, String image, int occurence, Utilisateur utilisateur) {
        this.id = id;
        this.nom = nom;
        this.nomGerant = nomGerant;
        this.prenomGerant = prenomGerant;
        this.region = region;
        this.adresse = adresse;
        this.dateFondation = dateFondation;
        this.description = description;
        this.telPortable = telPortable;
        this.telFix = telFix;
        this.image = image;
        this.occurence = occurence;
        this.utilisateur = utilisateur;
    }

    public Business(String nom, String nomGerant, String prenomGerant, String region, String adresse, Date dateFondation, String description, String telPortable, String telFix, String image, int occurence, Utilisateur utilisateur) {
        this.nom = nom;
        this.nomGerant = nomGerant;
        this.prenomGerant = prenomGerant;
        this.region = region;
        this.adresse = adresse;
        this.dateFondation = dateFondation;
        this.description = description;
        this.telPortable = telPortable;
        this.telFix = telFix;
        this.image = image;
        this.occurence = occurence;
        this.utilisateur = utilisateur;
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

    public String getNomGerant() {
        return nomGerant;
    }

    public void setNomGerant(String nomGerant) {
        this.nomGerant = nomGerant;
    }

    public String getPrenomGerant() {
        return prenomGerant;
    }

    public void setPrenomGerant(String prenomGerant) {
        this.prenomGerant = prenomGerant;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateFondation() {
        return dateFondation;
    }

    public void setDateFondation(Date dateFondation) {
        this.dateFondation = dateFondation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelPortable() {
        return telPortable;
    }

    public void setTelPortable(String telPortable) {
        this.telPortable = telPortable;
    }

    public String getTelFix() {
        return telFix;
    }

    public void setTelFix(String telFix) {
        this.telFix = telFix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


}