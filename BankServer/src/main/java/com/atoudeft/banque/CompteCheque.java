package com.atoudeft.banque;

public class CompteCheque extends CompteBancaire{

    double solde = 0;
    public CompteCheque (String numeroCompte, TypeCompte type, double solde) {
        super(numeroCompte, TypeCompte.CHEQUE);

    }

    /**
     * ajoute le montant au solde s’il est strictement positif. Sinon,
     * retourne false
     * @param montant
     * @return
     */
    public boolean crediter(double montant) {
        if (montant > 0) {
            OperationDepot operation = new OperationDepot(montant);
            enregistrerOperation(operation);
            solde += montant;
            return true;
        }
        return false;
    }

    /**
     * retire le montant du solde s’il est strictement positif et qu’il y a
     * assez de fonds. Sinon, retourne false
     * @param montant
     * @return
     */
    public boolean debiter(double montant) {
        if (montant < 0) {
            OperationRetrait operation = new OperationRetrait(montant);
            enregistrerOperation(operation);
            solde -= montant;
            return true;
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
