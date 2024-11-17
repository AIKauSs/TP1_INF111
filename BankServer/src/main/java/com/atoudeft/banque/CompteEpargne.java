package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire {

    int limite = 1000;
    int frais = 2;
    double tauxInterets = 0.05;
    double solde = 0;

    /**
     * Constructeur par parametres
     */
    public CompteEpargne(String numeroCompte, TypeCompte type, double solde) {
        super(numeroCompte, TypeCompte.EPARGNE);

    }

    /**
     * calcule les intérêts et les ajoute au solde
     * @return
     */
    public double ajouterInterets() {
        solde *= tauxInterets;
        return solde;
    }

    /**
     * ajoute le montant au solde s’il est strictement positif. Sinon,
     * retourne false
     * @param montant
     * @return
     */
    public boolean crediter(double montant) {
        if(solde > 0) {
            OperationDepot operation = new OperationDepot(montant);
            enregistrerOperation(operation);
            solde += montant;
        }
        return false;
    }

    /**
     * : retire le montant du solde s’il est strictement positif et qu’il y a
     * assez de fonds. Sinon, retourne false. Si l’opération a réussi et qu’il y a moins
     * de 1000$ dans le compte avant l’opération, on prélève des frais de 2$
     * @param montant
     * @return
     */
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
