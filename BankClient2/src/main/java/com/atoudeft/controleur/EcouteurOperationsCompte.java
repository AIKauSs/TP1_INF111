package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;
    private PanneauOperationsCompte panneauOperationsCompte;



    public EcouteurOperationsCompte(Client client, PanneauOperationsCompte panneauOperationsCompte) {
        this.client = client;
        this.panneauOperationsCompte = panneauOperationsCompte;
    }

    //ecouteur pour bouton creer compte epargne appuyer
    //envoi un message epargne au serveur
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String action;

        if (source instanceof JButton) {
            action = ((JButton) source).getActionCommand();  // Récupère le texte du bouton cliqué

//Switch case pour chaque action des bouttons
            switch (action) {
                case "Créer compte épargne":
                    client.envoyer("EPARGNE");
                    break;

                case "DEPOT":
                    panneauOperationsCompte.afficherPanneau("DEPOT");
                    break;

                case "RETRAIT":
                    panneauOperationsCompte.afficherPanneau("RETRAIT");
                    break;

                case "TRANSFERT":
                    panneauOperationsCompte.afficherPanneau("TRANSFERT");
                    break;

                case "FACTURE":
                    panneauOperationsCompte.afficherPanneau("FACTURE");
                    break;

                    /*
                    pour toutes les operations suivantes on va dans le panneau correspondant
                    et on recupere les infos dans le texte de celui-ci
                     */
                case "Effectuer Dépôt":
                    PanneauDepot panneauDepot = (PanneauDepot) panneauOperationsCompte.getPanneau("DEPOT");
                    String montant = panneauDepot.getChampMontant().getText();
                    if (!montant.isEmpty()) {
                        client.envoyer("DEPOT " + montant);
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un montant valide.");
                    }
                    break;

                case "Effectuer Retrait":
                    PanneauRetrait panneauRetrait = (PanneauRetrait) panneauOperationsCompte.getPanneau("RETRAIT");
                    String montantRetrait = panneauRetrait.getChampMontant().getText();
                    if (!montantRetrait.isEmpty()) {
                        client.envoyer("RETRAIT " + montantRetrait);
                    }
                    break;

                case "Effectuer Transfert":
                    PanneauTransfert panneauTransfert = (PanneauTransfert) panneauOperationsCompte.getPanneau("TRANSFERT");
                    String montantTransfert = panneauTransfert.getChampMontant().getText(); // Montant
                    String destinataire = panneauTransfert.getChampDestinataire().getText();
                    if (!montantTransfert.isEmpty() && !destinataire.isEmpty()) {
                        client.envoyer("TRANSFER " + montantTransfert + " " + destinataire);
                    }
                    break;

                case "Envoyer Facture":
                    PanneauFacture panneauFacture = (PanneauFacture) panneauOperationsCompte.getPanneau("FACTURE");
                    String numeroFacture = panneauFacture.getTxtNumeroFacture().getText();
                    String montantFacture = panneauFacture.getTxtMontantFacture().getText();
                    String descriptionFacture = panneauFacture.getTxtDescriptionFacture().getText();
                    if (!numeroFacture.isEmpty() && !montantFacture.isEmpty()) {
                        client.envoyer("FACTURE " + montantFacture + " " + numeroFacture + " " + descriptionFacture);
                    }
                    break;

                default:
                    System.out.println("Action inconnue : " + action);
                    break;
            }

        }
    }
}
