package com.atoudeft.banque;

import java.io.Serializable;

public class PileChainee implements Serializable {
    /**
     * Cette classe englobe un élément de la pile (un objet quelconque)
     * et garde en paramètre l'élément suivant.
     */
    private static class Noeud {
        private Object element;
        private Noeud suivant;

        public Noeud(Object element) {
            this.element = element;
            this.suivant = null;
        }
    }

    //initialisation des paramètres
    private Noeud sommet = null;
    private int taille = 0;

    // Ajouter un élément à la pile
    public void empiler(Object element) {
        Noeud nouveau = new Noeud(element);
        nouveau.suivant = sommet;
        sommet = nouveau;
        taille++;
    }

    // Retirer l'élément au sommet de la pile
    public Object dépiler() {
        if (sommet == null) {
            return null; // Si la pile est vide, rien à dépiler
        }
        Object element = sommet.element;
        sommet = sommet.suivant; // Mise à jour du sommet
        taille--;
        return element;
    }

    // Vérifier si la pile est vide
    public boolean estVide() {
        return taille == 0;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Noeud courant = sommet;
        while (courant != null) {
            sb.append(courant.element.toString()).append("\n");
            courant = courant.suivant;
        }
        return sb.toString();
    }
}

