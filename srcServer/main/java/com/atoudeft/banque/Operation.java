package com.atoudeft.banque;

import com.atoudeft.banque.TypeOperation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;


public abstract class Operation implements Serializable {
    private TypeOperation type;
    private Date date;

    /**
     * Crée une opération avec le type et un objet Date
     * La date est en millisecondes
     *
     * @param type le type d'opération effectuée
     */
    public Operation(TypeOperation type) {
        this.type = type;
        this.date = new Date(System.currentTimeMillis());
    }

    // accesseurs
    public TypeOperation getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    /**
     * méthode pour afficher la date et le type de l'operation
     */
    @Override
    public String toString() {
        return date + " " + type;
    }

}