package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire {

    int limite = 1000;
    int frais = 2;
    double tauxInterets = 0.05;
    double solde = 0;

    /**
     * Constructeur
     */
    public CompteEpargne(String numeroCompte, TypeCompte type, double solde) {
        super(numeroCompte, TypeCompte.EPARGNE);

    }

    public double ajouterInterets() {
        solde *= tauxInterets;
        return solde;
    }


    public boolean crediter(double montant) {
        if(solde > 0) {
            OperationDepot operation = new OperationDepot(montant);
            enregistrerOperation(operation);
            solde += montant;
        }
        return false;
    }

    public boolean debiter(double montant) {
        if (solde > 0 && solde-montant > 0) {
            OperationRetrait operation = new OperationRetrait(montant);
            enregistrerOperation(operation);
            solde -= montant;
            if(solde < limite) {
                solde = solde - frais;
            }

        }
        return false;
    }

    public boolean payerFacture(String numeroFacture, double montant, String description) {
        OperationFacture operation = new OperationFacture(montant, numeroFacture, description);
        enregistrerOperation(operation);
        return false;
    }


    public boolean transferer(double montant, String numeroCompteDestinataire) {
        OperationTransfert operation = new OperationTransfert(montant,numeroCompteDestinataire);
        enregistrerOperation(operation);
        return false;
    }
}
