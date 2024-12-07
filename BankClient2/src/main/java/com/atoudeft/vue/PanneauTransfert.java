package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauTransfert extends JPanel {
    private JTextField txtMontant;
    private JTextField txtDestinataire;
    private JButton btnEffectuer;

    public PanneauTransfert() {
        setLayout(new GridBagLayout());
        GridBagConstraints grilleOperation = new GridBagConstraints();

        JLabel lblMontant = new JLabel("Montant à transférer :");
        txtMontant = new JTextField(10);
        JLabel lblDestinataire = new JLabel("Compte destinataire :");
        txtDestinataire = new JTextField(10);
        btnEffectuer = new JButton("Effectuer Transfert");
        btnEffectuer.setActionCommand("Effectuer Transfert");

        grilleOperation.insets = new Insets(5, 5, 5, 5);
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 0;
        add(lblMontant, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtMontant, grilleOperation);

        grilleOperation.gridx = 0;
        grilleOperation.gridy = 1;
        add(lblDestinataire, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtDestinataire, grilleOperation);

        grilleOperation.gridx = 0;
        grilleOperation.gridy = 2;
        grilleOperation.gridwidth = 2;
        add(btnEffectuer, grilleOperation);

        setBorder(BorderFactory.createTitledBorder("Transfert"));
    }

    // Getter pour le champ de montant
    public JTextField getChampMontant() {
        return txtMontant;
    }

    // Getter pour le champ destinataire
    public JTextField getChampDestinataire() {
        return txtDestinataire;
    }

    // Getter pour le bouton effectuer
    public JButton getBoutonEffectuer() {
        return btnEffectuer;
    }
}
