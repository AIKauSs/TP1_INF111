package com.atoudeft.client;

import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;
import com.atoudeft.vue.PanneauPrincipal;
import com.programmes.MainFrame;

import javax.swing.*;

public class GestionnaireEvenementClient2 implements GestionnaireEvenement {
    private Client client;
    private PanneauPrincipal panneauPrincipal;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient2(Client client, PanneauPrincipal panneauPrincipal) {

        this.client = client;
        this.panneauPrincipal = panneauPrincipal;
        this.client.setGestionnaireEvenement(this);
    }

    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        //Connexion cnx;
        String typeEvenement, arg, str;
        int i;
        String[] t;
        MainFrame fenetre;

        if (source instanceof Connexion) {
            //cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "END": //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                /******************* CREATION et CONNEXION *******************/
//                case "HIST": //Le serveur a renvoyé
//                    panneauPrincipal.setVisible(true);
//                    JOptionPane.showMessageDialog(null,"Panneau visible");
//                    cnx.envoyer("LIST");
//                    arg = evenement.getArgument();
//                    break;
                case "OK":
                    panneauPrincipal.setVisible(true);
                    fenetre = (MainFrame) panneauPrincipal.getTopLevelAncestor();
                    fenetre.setTitle(MainFrame.TITRE);//+" - Connecté"
                    break;
                case "NOUVEAU":
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Nouveau refusé");
                    } else {
                        panneauPrincipal.cacherPanneauConnexion();
                        panneauPrincipal.montrerPanneauCompteClient();
                        str = arg.substring(arg.indexOf("OK") + 2).trim();
                        panneauPrincipal.ajouterCompte(str);
                    }
                    break;
                case "CONNECT":
                    arg = evenement.getArgument();
                    if (arg.trim().startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Connexion refusée");
                    } else {
                        panneauPrincipal.cacherPanneauConnexion();
                        panneauPrincipal.montrerPanneauCompteClient();
                        str = arg.substring(arg.indexOf("OK") + 2).trim();
                        t = str.split(":");
                        for (String s : t) {
                            panneauPrincipal.ajouterCompte(s.substring(0, s.indexOf("]") + 1));
                        }
                        // Envoyer une commande SELECT au serveur pour obtenir le solde
                        client.envoyer("SELECT cheque");
                    }
                    break;
                /******************* SÉLECTION DE COMPTES *******************/
                case "EPARGNE" :
                    arg = evenement.getArgument();
                    // Si le serveur renvoie "OK" suivi du numéro du compte épargne
                    if (arg.startsWith("OK")) {
                        // Le numéro de compte épargne est après "OK "
                        String numeroCompte = arg.substring(3).trim();

                        // Afficher un message de succès
                        JOptionPane.showMessageDialog(panneauPrincipal, "Compte épargne créé avec succès !");

                        // Ajouter le numéro de compte à la liste des comptes
                        panneauPrincipal.ajouterCompte(numeroCompte);  // Ajouter à la liste des numéros de comptes affichée à l'écran
                    } else {
                        // Si la création échoue, afficher un message d'erreur
                        JOptionPane.showMessageDialog(panneauPrincipal, "Erreur lors de la création du compte épargne.");
                    }

                    break;
                case "SELECT":
                    // Récupérer la réponse du serveur
                    arg = evenement.getArgument();

                    // Vérifier si le serveur a répondu "NO"
                    if (arg.startsWith("NO")) {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : sélection du compte refusée. Aucun compte trouvé.");
                        break;
                    }

                    // Vérifier si le serveur a répondu "OK <numCompte> <solde>"
                    if (arg.startsWith("OK")) {
                        // Découper la réponse pour prendre le solde
                        String[] responseParts = arg.split(" ");
                        if (responseParts.length < 3) {
                            JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : réponse invalide reçue du serveur.");
                            break;
                        }

                        double solde = Double.parseDouble(responseParts[2]); // Solde

                        // Mettre à jour le panneau des opérations avec le solde
                        panneauPrincipal.getPanneauOperationsCompte().afficherSolde(solde);

                    } else {
                        JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : réponse inconnue du serveur.");
                    }
                    break;

                /******************* OPÉRATIONS BANCAIRES *******************/
                case "DEPOT":
                    arg = evenement.getArgument();
                    if (arg.startsWith("OK")) {
                        // Découper la réponse pour prendre le solde
                        String[] responseParts = arg.split(" ");
                        if (responseParts.length >= 2) {
                            double solde = Double.parseDouble(responseParts[1]);
                            panneauPrincipal.getPanneauOperationsCompte().afficherSolde(solde);
                            JOptionPane.showMessageDialog(panneauPrincipal, "Dépôt effectué. Nouveau solde : " + solde + " $");
                        } else {
                            JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : réponse invalide du serveur pour le dépôt.");
                        }
                    }
                    break;

                case "RETRAIT":
                    arg = evenement.getArgument();
                    if (arg.startsWith("OK")) {
                        // Découper la réponse pour prendre le solde
                        String[] responseParts = arg.split(" ");
                        if (responseParts.length >= 2) {
                            double solde = Double.parseDouble(responseParts[1]);
                            panneauPrincipal.getPanneauOperationsCompte().afficherSolde(solde);
                            JOptionPane.showMessageDialog(panneauPrincipal, "Retrait effectué. Nouveau solde : " + solde + " $");
                        } else {
                            JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : réponse invalide du serveur pour le retrait.");
                        }
                    }
                    break;

                case "FACTURE":
                    arg = evenement.getArgument();
                    if (arg.startsWith("OK")) {
                        // Découper la réponse pour prendre le solde
                        String[] responseParts = arg.split(" ");
                        if (responseParts.length >= 2) {
                            double solde = Double.parseDouble(responseParts[1]);
                            panneauPrincipal.getPanneauOperationsCompte().afficherSolde(solde);
                            JOptionPane.showMessageDialog(panneauPrincipal, "Facture payée. Nouveau solde : " + solde + " $");
                        } else {
                            JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : réponse invalide du serveur pour la facture.");
                        }
                    }
                    break;

                case "TRANSFER":
                    arg = evenement.getArgument();
                    if (arg.startsWith("OK")) {
                        // Diviser la réponse pour extraire le nouveau solde
                        String[] parts = arg.split(" ");
                        if (parts.length >= 2) {
                            double nouveauSolde = Double.parseDouble(parts[1]);

                            // Mettre à jour le solde dans le panneau des opérations
                            panneauPrincipal.getPanneauOperationsCompte().afficherSolde(nouveauSolde);

                            // Afficher une confirmation à l'utilisateur
                            JOptionPane.showMessageDialog(panneauPrincipal,
                                    "Transfert effectué avec succès. Nouveau solde : " + nouveauSolde + " $");
                        }
                    } else {
                        // En cas d'erreur, afficher un message
                        JOptionPane.showMessageDialog(panneauPrincipal, "Erreur : le transfert a échoué.");
                    }
                    break;

                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default:
                    System.out.println("RECU : " + evenement.getType() + " " + evenement.getArgument());
            }
        }
    }
}