package com.atoudeft.banque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompteClient implements Serializable {
    private String numero;
    private String nip;
    private List<CompteBancaire> comptes;

    /**
     * Crée un compte-client avec un numéro et un nip.
     *
     * @param numero le numéro du compte-client
     * @param nip le nip
     */
    public CompteClient(String numero, String nip) {
        this.numero = numero;
        this.nip = nip;
        comptes = new ArrayList<>();
    }

    /**
     * Ajoute un compte bancaire au compte-client.
     *
     * @param compte le compte bancaire
     * @return true si l'ajout est réussi
     */
    public boolean ajouter(CompteBancaire compte) {
        return this.comptes.add(compte);
    }

    public String getNumeroClient(){return numero;}

    public List<CompteBancaire> getComptes() {
        return comptes;
    }


    /**
     * Récupère un compte bancaire en fonction de son numéro.
     *
     * @param numCompteActuel le numéro du compte bancaire recherché
     * @return le compte bancaire correspondant au numéro, ou null si aucun compte n'est trouvé
     */
    public CompteBancaire getCompteBancaire(String numCompteActuel) {
        for (CompteBancaire compte : comptes) {
            if (compte.getNumero().equals(numCompteActuel)) {
                return compte;
            }
        }
        return null;
    }


}