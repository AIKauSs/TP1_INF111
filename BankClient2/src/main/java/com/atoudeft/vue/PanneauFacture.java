package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauFacture extends JPanel {
    private JTextField txtNumeroFacture;
    private JTextField txtMontantFacture;
    private JTextField txtDescriptionFacture; // Champ pour la description
    private JButton btnEnvoyer;

    public PanneauFacture() {
        setLayout(new GridBagLayout());
        GridBagConstraints grilleOperation = new GridBagConstraints();

        // Étiquettes et champs de saisie
        JLabel lblNumero = new JLabel("Numéro de facture :");
        txtNumeroFacture = new JTextField(10);

        JLabel lblMontant = new JLabel("Montant de la facture :");
        txtMontantFacture = new JTextField(10);

        JLabel lblDescription = new JLabel("Description de la facture :");
        txtDescriptionFacture = new JTextField(15); // Nouveau champ

        btnEnvoyer = new JButton("Envoyer Facture");
        btnEnvoyer.setActionCommand("Envoyer Facture");

        // Placement des composants
        grilleOperation.insets = new Insets(5, 5, 5, 5);

        // Numéro de facture
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 0;
        add(lblNumero, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtNumeroFacture, grilleOperation);

        // Montant de la facture
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 1;
        add(lblMontant, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtMontantFacture, grilleOperation);

        // Description de la facture
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 2;
        add(lblDescription, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtDescriptionFacture, grilleOperation);

        // Bouton Envoyer Facture
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 3;
        grilleOperation.gridwidth = 2;
        grilleOperation.anchor = GridBagConstraints.CENTER;
        add(btnEnvoyer, grilleOperation);

        setBorder(BorderFactory.createTitledBorder("Paiement de Facture"));
    }
    public JTextField getTxtNumeroFacture() {
        return txtNumeroFacture;
    }

    public JTextField getTxtMontantFacture() {
        return txtMontantFacture;
    }

    public JTextField getTxtDescriptionFacture() {
        return txtDescriptionFacture; // Getter pour la description
    }

    public JButton getBtnEnvoyer() {
        return btnEnvoyer;
    }
}
