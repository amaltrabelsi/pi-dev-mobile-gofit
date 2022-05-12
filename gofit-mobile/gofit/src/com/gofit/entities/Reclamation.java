package com.gofit.entities;

public class Reclamation {

    private int id;
    private String contenu;
    private Terrain terrain;
    private Utilisateur utilisateur;

    public Reclamation() {
    }

    public Reclamation(int id, String contenu, Terrain terrain, Utilisateur utilisateur) {
        this.id = id;
        this.contenu = contenu;
        this.terrain = terrain;
        this.utilisateur = utilisateur;
    }

    public Reclamation(String contenu, Terrain terrain, Utilisateur utilisateur) {
        this.contenu = contenu;
        this.terrain = terrain;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


}