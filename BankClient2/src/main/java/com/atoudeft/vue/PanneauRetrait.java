package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauRetrait extends JPanel {
    private JTextField txtMontant;
    private JButton btnEffectuer;

    public PanneauRetrait() {
        setLayout(new GridBagLayout());
        GridBagConstraints grilleOperation = new GridBagConstraints();

        JLabel lblMontant = new JLabel("Montant à retirer :");
        txtMontant = new JTextField(10);
        btnEffectuer = new JButton("Effectuer Retrait");
        btnEffectuer.setActionCommand("Effectuer Retrait");

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

        setBorder(BorderFactory.createTitledBorder("Retrait"));
    }


    public JTextField getChampMontant() {
        return txtMontant;
    }


    public JButton getBoutonEffectuer() {
        return btnEffectuer;
    }
}
