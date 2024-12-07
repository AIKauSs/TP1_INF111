package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauDepot extends JPanel {
    private JTextField txtMontant;
    private JButton btnEffectuer;

    public PanneauDepot() {
        setLayout(new GridBagLayout());
        GridBagConstraints grilleOperation = new GridBagConstraints();

        JLabel lblMontant = new JLabel("Montant à déposer :");
        txtMontant = new JTextField(10);
        btnEffectuer = new JButton("Effectuer Dépôt");
        btnEffectuer.setActionCommand("Effectuer Dépôt");

        grilleOperation.insets = new Insets(5, 5, 5, 5);
        grilleOperation.gridx = 0;
        grilleOperation.gridy = 0;
        add(lblMontant, grilleOperation);

        grilleOperation.gridx = 1;
        add(txtMontant, grilleOperation);

        grilleOperation.gridx = 0;
        grilleOperation.gridy = 1;
        grilleOperation.gridwidth = 2;
        add(btnEffectuer, grilleOperation);

        setBorder(BorderFactory.createTitledBorder("Dépôt"));
    }

    // Getter pour le champ de montant
    public JTextField getChampMontant() {
        return txtMontant;
    }

    // Getter pour le bouton effectuer
    public JButton getBoutonEffectuer() {
        return btnEffectuer;
    }
}
