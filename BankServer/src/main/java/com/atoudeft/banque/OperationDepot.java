package com.atoudeft.banque;

/**
 * Cette Classe hérite de la classe opération
 * Elle définit une opération de dépot
 */

public class OperationDepot extends Operation{
    private double montant;

    /**
     * Crée une opération Depot avec le type et la date
     * actuelle en millisecondes
     *
     * @param montant le numéro du compte-client
     */
    public OperationDepot(double montant) {
        super(TypeOperation.DEPOT);
        this.montant = montant;
    }
    @Override
    public String toString(){
        return super.toString() + "Montant : " + montant;
    }
}
