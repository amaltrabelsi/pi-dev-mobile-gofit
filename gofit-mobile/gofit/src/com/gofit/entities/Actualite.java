package com.gofit.entities;

public class Actualite {

    private int id;
    private String titre;
    private String description;
    private String contenu;
    private String categorie;
    private String image;
    private Utilisateur utilisateur;

    public Actualite() {
    }

    public Actualite(int id, String titre, String description, String contenu, String categorie, String image, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.categorie = categorie;
        this.image = image;
        this.utilisateur = utilisateur;
    }

    public Actualite(String titre, String description, String contenu, String categorie, String image, Utilisateur utilisateur) {
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.categorie = categorie;
        this.image = image;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    @Override
    public String toString() {
        return "Actualite : " +
                "id=" + id
                + ", Titre=" + titre
                + ", Description=" + description
                + ", Contenu=" + contenu
                + ", Categorie=" + categorie
                + ", Image=" + image
                + ", Utilisateur=" + utilisateur
                ;
    }


}