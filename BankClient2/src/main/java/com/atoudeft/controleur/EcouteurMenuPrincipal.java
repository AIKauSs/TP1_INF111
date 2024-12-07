package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauConfigServeur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2024-11-01
 */
public class EcouteurMenuPrincipal implements ActionListener {
    private Client client;
    private JFrame fenetre;

    public EcouteurMenuPrincipal(Client client, JFrame fenetre) {
        this.client = client;
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String action;
        String alias;
        int res;

        if (source instanceof JMenuItem) {
            action = ((JMenuItem)source).getActionCommand();
            switch (action) {
                case "CONNECTER":
                    if (!client.isConnecte()) {
                        if (!client.connecter()) {
                            JOptionPane.showMessageDialog(fenetre, "Le serveur ne répond pas");
                            break;
                        }
                    }
                    break;
                case "DECONNECTER":
                    if (!client.isConnecte())
                        break;
                    res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                            "Confirmation Déconnecter",
                            JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.OK_OPTION){
                        client.deconnecter();
                    }
                    break;
                case "CONFIGURER":
                    // Afficher le panneau de configuration du serveur avec gestion du port
                    boolean validPort = false;
                    while (!validPort) {
                        // On utilise les données actuelles du client remplir les champs
                        String adr = client.getAdrServeur();
                        int port = client.getPortServeur();

                        PanneauConfigServeur panneau = new PanneauConfigServeur(adr, port);

                        // Afficher la boîte de confirmation avec le panneau de configuration
                        int option = JOptionPane.showConfirmDialog(fenetre, panneau,
                                "Configurer serveur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        // Si l'utilisateur appuie sur "OK"
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                // Récupérer les valeurs saisies
                                adr = panneau.getAdresseServeur();
                                String portStr = panneau.getPortServeur();

                                int newPort = Integer.parseInt(portStr); // Essayer de convertir en entier

                                // Transmettre les données au client
                                client.setAdrServeur(adr);
                                client.setPortServeur(newPort);
                                validPort = true; // Le port est valide, on sort de la boucle
                            } catch (NumberFormatException e) {
                                // Si le port est invalide, on affiche un message d'erreur
                                JOptionPane.showMessageDialog(fenetre, "Le port doit être un nombre entier valide.",
                                        "Erreur de port", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            break; // Si l'utilisateur annule, on quitte sans enregistrer
                        }
                    }
                    break;
                case "QUITTER":
                    if (client.isConnecte()) {
                        res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                                "Confirmation Quitter",
                                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.OK_OPTION){
                            client.deconnecter();
                            System.exit(0);
                        }
                    }
                    else
                        System.exit(0);
                    break;
            }
        }
    }
}