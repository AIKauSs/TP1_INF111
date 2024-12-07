package com.atoudeft.controleur;

import com.atoudeft.client.Client;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeComptes extends MouseAdapter {

    private Client client;

    public EcouteurListeComptes(Client client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
           // Récupère la liste source
            JList<String> listeCompte = (JList<String>) evt.getSource();
            int compteSelect = listeCompte.locationToIndex(evt.getPoint());

            if (compteSelect != -1) {
                // Récupère l'élément sélectionné (supposons que c'est une String)
                Object compte = listeCompte.getModel().getElementAt(compteSelect);
                String numeroCompte = compte.toString();

                // Recherche des positions des crochets
                int debut = numeroCompte.indexOf('[');
                int fin = numeroCompte.indexOf(']');

                // Debut crochets a fin crochets
                if (debut < fin) {
                    // Extraction du contenu entre les crochets
                    String typeCompte = numeroCompte.substring(debut + 1, fin);
                    client.envoyer("SELECT " + typeCompte.toLowerCase());
                }
            }
        }
    }
}
