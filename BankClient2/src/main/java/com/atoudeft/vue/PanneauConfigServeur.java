package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

/**
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        // Initialisation du panneau avec une disposition en GridLayout
        setLayout(new GridLayout(3, 2));

        // Label et champ pour l'adresse du serveur
        add(new JLabel("Adresse:"));
        txtAdrServeur = new JTextField(20);
        txtAdrServeur.setText(adr);  // On initialise avec l'adresse passée
        add(txtAdrServeur);

        // Label et champ pour le numéro de port
        add(new JLabel("Port:"));
        txtNumPort = new JTextField(20);
        txtNumPort.setText(String.valueOf(port));  // On initialise avec le port passé
        add(txtNumPort);

        // Ajouter les boutons OK et Annuler
        add(new JPanel());  // Pour l'alignement
        add(new JPanel());  // Pour l'alignement
    }

    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }

    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
