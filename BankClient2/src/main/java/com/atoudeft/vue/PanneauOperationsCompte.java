package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauOperationsCompte extends JPanel {
    private JButton bEpargne, bDepot, bRetrait, bTransfert, bFacture, bHistorique;
    private JLabel lblSolde;
    private CardLayout cardLayout; // CardLayout pour basculer entre les panneaux
    private JPanel cardPanel;
    private PanneauDepot panneauDepot;
    private PanneauFacture panneauFacture;
    private PanneauTransfert panneauTransfert;
    private PanneauRetrait panneauRetrait;

    public PanneauOperationsCompte() {
        // Initialisation des composants
        bEpargne = new JButton("Créer compte épargne");
        bDepot = new JButton("Déposer");
        bRetrait = new JButton("Retirer");
        bTransfert = new JButton("Transferer");
        bFacture = new JButton("Payer Facture");
        bHistorique = new JButton("Historique du compte");
        lblSolde = new JLabel("Solde : 0.0 $");

        // Définir les commandes d'action pour chaque bouton
        bEpargne.setActionCommand("EPARGNE");
        bDepot.setActionCommand("DEPOT");
        bRetrait.setActionCommand("RETRAIT");
        bTransfert.setActionCommand("TRANSFERT");
        bFacture.setActionCommand("FACTURE");
        bHistorique.setActionCommand("HISTORIQUE");

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(lblSolde);
        buttonPanel.add(bEpargne);
        buttonPanel.add(bDepot);
        buttonPanel.add(bRetrait);
        buttonPanel.add(bTransfert);
        buttonPanel.add(bFacture);
        buttonPanel.add(bHistorique);

        // Initialisation des panneaux d'opérations
        panneauDepot = new PanneauDepot();
        panneauRetrait = new PanneauRetrait();
        panneauFacture = new PanneauFacture();
        panneauTransfert = new PanneauTransfert();

        // Initialisation du CardLayout et ajout des panneaux
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new JPanel(), "VIDE"); // Panneau vide pour le démarrage
        cardPanel.add(panneauDepot, "DEPOT");
        cardPanel.add(panneauRetrait, "RETRAIT");
        cardPanel.add(panneauTransfert, "TRANSFERT");
        cardPanel.add(panneauFacture, "FACTURE");


        // Configuration principale du layout
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH); // Ajouter les boutons en haut
        this.add(cardPanel, BorderLayout.CENTER);  // Ajouter le CardLayout au centre

        // Afficher un panneau vide par défaut
        afficherPanneau("VIDE");
    }

    /**
     * Méthode pour afficher un panneau spécifique
     * @param panneau
     */
    public void afficherPanneau(String panneau) {
        cardLayout.show(cardPanel, panneau);
    }


    /**
     * Ajouter un écouteur pour les boutons
     * @param ecouteur
     */
    public void setEcouteur(ActionListener ecouteur) {
        bEpargne.addActionListener(ecouteur);
        bDepot.addActionListener(ecouteur);
        bRetrait.addActionListener(ecouteur);
        bTransfert.addActionListener(ecouteur);
        bFacture.addActionListener(ecouteur);
        bHistorique.addActionListener(ecouteur);

        // Écouteurs pour les boutons internes des panneaux d'opérations
        panneauDepot.getBoutonEffectuer().addActionListener(ecouteur);
        panneauRetrait.getBoutonEffectuer().addActionListener(ecouteur);
        panneauTransfert.getBoutonEffectuer().addActionListener(ecouteur);
        panneauFacture.getBtnEnvoyer().addActionListener(ecouteur);
    }

    /**
     * Mettre à jour le solde affiché
     * @param solde
     */
    public void afficherSolde(double solde) {
        lblSolde.setText("Solde : " + solde + " $");
    }

    /**
     * retourne un panneau d'operation
     * @param panneau
     * @return
     */
    public JPanel getPanneau(String panneau) {
        switch (panneau) {
            case "DEPOT":
                return panneauDepot;
            case "RETRAIT":
                return panneauRetrait;
            case "TRANSFERT":
                return panneauTransfert;
            case "FACTURE":
                return panneauFacture;
            default:
                return null;
        }
    }
}
