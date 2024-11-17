package com.atoudeft.banque;

/**
 * Classe opération de type FACTURE
 * cette classe hérite de la classe Operation
 */
public class OperationFacture extends  Operation{
    private double montant;
    private String numFacture;
    private String description;


    /**
     * Crée une opération avec le type et la date
     * actuelle en millisecondes
     *
     * @param montant montant de la facture
     * @param numFacture numéro de la facture
     * @param description description de la facture
     */
    public OperationFacture(double montant, String numFacture, String description) {
        super(TypeOperation.FACTURE);
        this.montant = montant;
        this.numFacture = numFacture;
        this.description = description;
    }

    /**
     * Classe opération affiche la date et le type
     * Ici on affiche le montant, numero de facture et description
     */
    @Override
    public String toString(){
        return super.toString() + " " + montant + " " + numFacture + " " + description;
    }
}
