package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauHistorique extends JPanel {
    private JTextArea textArea; // Zone de texte en lecture seule pour l'historique

    public PanneauHistorique() {
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    // Méthode pour définir l'historique dans le JTextArea
    public void setHistorique(String historique) {
        textArea.setText(historique);
    }

    // Méthode pour afficher l'historique dans une boîte de dialogue
    public void afficherHistoriqueDialog() {
        JOptionPane.showMessageDialog(this,
                new JScrollPane(new JTextArea(textArea.getText(), 10, 50)),
                "Historique des opérations",
                JOptionPane.INFORMATION_MESSAGE);
    }
}



