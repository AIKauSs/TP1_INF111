package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire {

    int limite = 1000;
    int frais = 2;
    double tauxInterets = 0.05;
    double solde = 0;

    /**
     * Constructeur
     */
    public CompteEpargne(String numeroCompte, TypeCompte type, double solde, int limite, int frais,
                         double tauxInterets) {
        super(numeroCompte, TypeCompte.EPARGNE);
        this.limite = limite;
        this.frais = frais;
        this.tauxInterets = tauxInterets;
    }

    public double ajouterInterets() {
        solde *= tauxInterets;
        return solde;
    }


    public boolean crediter(double montant) {
        if(solde > 0) {
            solde += montant;
        }
        return false;
    }

    public boolean debiter(double montant) {
        if (solde > 0 && solde-montant > 0) {
            solde -= montant;
            if(solde < limite) {
                solde = solde - frais;
            }

        }
        return false;
    }

    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }


    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
