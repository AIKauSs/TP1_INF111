package com.atoudeft.banque;

/**
 * Classe d'opération de type RETRAIT
 * Cette classe hérite de la classe Operation
 */
public class OperationRetrait extends Operation{
    private double montant;
    /**
     * Crée une opération de type retrait avec le type et la date
     * actuelle en millisecondes
     *
     * @param montant à retiré
     */
    public OperationRetrait(Double montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }

    public double getMontant(){
        return montant;
    }

    @Override
    public String toString(){
        return super.toString() + "Montant :" + montant;
    }
}
