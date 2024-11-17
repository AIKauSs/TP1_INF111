package com.atoudeft.banque;

/**
 * Classe operation de type TRANSFERT
 *
 */
public class OperationTransfert extends Operation{
    private double montant;
    private String numCompteDest;


    /**
     * Crée une opération avec le type et la date
     * actuelle en millisecondes
     *
     * @param montant montant de la facture
     * @param numCompteDest numéro de compte Destinataire
     */
    public OperationTransfert(double montant, String numCompteDest) {
        super(TypeOperation.TRANSFER);
        this.montant = montant;
        this.numCompteDest = numCompteDest;
    }

    /**
     * Date et Type de transaction affichée par classe parente
     * on affiche le montant et le numero de compte Destinataire
     */
    @Override
    public String toString(){
        return super.toString() + " " + montant + " " + numCompteDest;
    }
}
