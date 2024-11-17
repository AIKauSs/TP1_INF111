package com.atoudeft.serveur;

import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.TypeCompte;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

import java.util.Objects;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;

        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;

                /******************* COMMA NDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;











                // case "EPARGNE" : // Crée un nouveau compte-épargne:
                    // if()

               // }

                case "CONNECT":
                    banque = serveurBanque.getBanque();
                    argument = evenement.getArgument();
                    t = argument.split(":");

                    numCompteClient = t[0];
                    nip = t[1];

                    t = serveurBanque.list().split(":");

                    for (String client: t) {

                        if (Objects.equals(client, numCompteClient)) {
                            cnx.envoyer("CONNECT NO");
                        }

                        else {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("CONNECT OK");
                        }

                    }

                    break;

                case "SELECT":
                    banque = serveurBanque.getBanque();
                    argument = evenement.getArgument().toUpperCase();

//temporaire 1==1
                    if (1==1) {

                        switch (argument) {

                            case "CHEQUE":
                                cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(cnx.getNumeroCompteClient()));
                                break;

                            case "EPARGNE":
                                cnx.setNumeroCompteActuel(banque.getNumeroCompteEpargne(cnx.getNumeroCompteClient()));
                                break;

                            default:
                                msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                                cnx.envoyer(msg);
                        }
                        cnx.envoyer("SELECT OK");

                    }
                    else {
                        cnx.envoyer("SELECT NO");
                    }

                /********************** COMMANDES DE GESTION DU CAPITAL *******************/
                case "DEPOT": // Dépôt d'argent
                    numCompteClient = cnx.getNumeroCompteClient();
                    banque = serveurBanque.getBanque();
                    if (numCompteClient == null) {
                        cnx.envoyer("DEPOT NO client non connecté");
                        break;
                    }
                    argument = evenement.getArgument().trim();
                    try {
                        double montant = Double.parseDouble(argument);
                        if (montant <= 0) {
                            cnx.envoyer("DEPOT NO montant invalide");
                        } else {
                            String numCompteActuel = cnx.getNumeroCompteActuel();
                            CompteBancaire compte = banque.getCompteClient(numCompteClient).getCompteBancaire(numCompteActuel);
                            if (compte == null) {
                                cnx.envoyer("DEPOT NO compte introuvable");
                            } else if (compte.crediter(montant)) {
                                cnx.envoyer("DEPOT OK " + montant + "$ déposés sur le compte " + numCompteActuel);
                            } else {
                                cnx.envoyer("DEPOT NO erreur lors du dépôt");
                            }
                        }
                    } catch (NumberFormatException e) {
                        cnx.envoyer("DEPOT NO Montant invalide");
                    }
                    break;

                case "RETRAIT": // Retrait d'argent
                    numCompteClient = cnx.getNumeroCompteClient();
                    banque = serveurBanque.getBanque();
                    if (numCompteClient == null) {
                        cnx.envoyer("RETRAIT NO client non connecté");
                        break;
                    }
                    argument = evenement.getArgument().trim();
                    try {
                        double montant = Double.parseDouble(argument);
                        if (montant <= 0) {
                            cnx.envoyer("RETRAIT NO montant invalide");
                        } else {
                            String numCompteActuel = cnx.getNumeroCompteActuel();
                            CompteBancaire compte = banque.getCompteClient(numCompteClient).getCompteBancaire(numCompteActuel);
                            if (compte != null && compte.debiter(montant)) {
                                cnx.envoyer("RETRAIT OK " + montant + "$ retirés du compte " + compte.getNumero());
                            } else {
                                cnx.envoyer("RETRAIT NO Solde insuffisant ou erreur");
                            }
                        }
                    } catch (NumberFormatException e) {
                        cnx.envoyer("RETRAIT NO Montant invalide");
                    }
                    break;

                case "TRANSFER": // Permet de transférer de l'argent d'un compte à un autre
                    numCompteClient = cnx.getNumeroCompteClient();
                    banque = serveurBanque.getBanque();
                    // Découper les arguments (par exemple : "1000 numCompteDest")
                    argument = evenement.getArgument().trim();
                    String[] args = argument.split(" ");

                    if (args.length != 2) {
                        cnx.envoyer("TRANSFER NO Arguments invalides");
                    } else {
                        try {
                            double montant = Double.parseDouble(args[0]);
                            String numCompteDest = args[1];

                            if (montant <= 0) {
                                cnx.envoyer("TRANSFER NO montant invalide");
                            } else {
                                double frais = 0;
                                String numCompteActuel = cnx.getNumeroCompteActuel();

                                // Vérifier le compte source
                                CompteBancaire compteSource = banque.getCompteClient(numCompteClient).getCompteBancaire(numCompteActuel);
                                if (compteSource == null) {
                                    cnx.envoyer("TRANSFER NO compte source introuvable");
                                    break;
                                }

                                // Appliquer des frais pour les comptes épargne
                                if (compteSource.getType() == TypeCompte.EPARGNE) {
                                    frais = 2.5; // Exemple de frais pour un compte épargne
                                }

                                // Vérifier si le solde est suffisant pour couvrir le transfert et les frais
                                if (compteSource.getSolde() < (montant + frais)) {
                                    cnx.envoyer("TRANSFER NO Solde insuffisant");
                                    break;
                                }

                                // Vérifier si le compte destinataire existe
                                CompteBancaire compteDest = banque.getCompteClient(numCompteDest).getCompteBancaire(numCompteActuel);
                                if (compteDest == null) {
                                    cnx.envoyer("TRANSFER NO compte destinataire introuvable");
                                    break;
                                }

                                // Essayer d'effectuer le transfert
                                boolean success = banque.transferer(montant, numCompteActuel, numCompteDest);
                                if (success) {
                                    // Débiter les frais du compte source, si applicable
                                    compteSource.debiter(frais);
                                    cnx.envoyer("TRANSFER OK " + montant + "$ transférés au compte " + numCompteDest);
                                } else {
                                    cnx.envoyer("TRANSFER NO erreur lors du transfert");
                                }
                            }
                        } catch (NumberFormatException e) {
                            cnx.envoyer("TRANSFER NO Montant invalide");
                        }
                    }
                    break;


                case "FACTURE": // Payer une facture
                    banque = serveurBanque.getBanque();
                    numCompteClient = cnx.getNumeroCompteClient();
                    // Vérifier la validité des arguments
                    argument = evenement.getArgument().trim();
                    String[] argsF = argument.split(" ", 3); // Diviser l'argument en 3 parties
                    if (argsF.length != 3) {
                        cnx.envoyer("FACTURE NO arguments invalides");
                    } else {
                        try {
                            // Récupérer les informations de la facture
                            double montant = Double.parseDouble(argsF[0]);
                            String numFacture = argsF[1];
                            String description = argsF[2];

                            // Vérifier que le montant est valide
                            if (montant <= 0) {
                                cnx.envoyer("FACTURE NO montant invalide");
                                break;
                            }
                            //verifier existance du client
                            CompteClient client = banque.getCompteClient(numCompteClient);
                            if (client == null) {
                                cnx.envoyer("FACTURE NO compte client introuvable");
                                break;
                            }

                            // Récupérer le compte bancaire actuel du client
                            String numCompteActuel = cnx.getNumeroCompteActuel(); // Le numéro de compte actuellement sélectionné
                            CompteBancaire compte = client.getCompteBancaire(numCompteActuel);
                            //verifier sélection d'un compte valide
                            if (compte == null) {
                                cnx.envoyer("FACTURE NO compte bancaire introuvable");
                                break;
                            }

                            // Vérifier le type de compte (ajouter des frais pour un compte épargne)
                            double frais = 0;
                            if (compte.getType() == TypeCompte.EPARGNE) {
                                frais = 2.5;  // Exemple de frais pour un compte épargne
                                montant += frais;  // Ajouter les frais au montant de la facture
                            }

                            // Vérifier si le solde est suffisant pour payer la facture
                            if (montant > compte.getSolde()) {
                                cnx.envoyer("FACTURE NO solde insuffisant");
                                break;
                            }

                            // Si le solde est suffisant, procéder au paiement de la facture
                            boolean success = compte.payerFacture(numFacture, montant, description);
                            if (success) {
                                // Envoyer la confirmation du paiement
                                cnx.envoyer("FACTURE OK " + montant + " " + numFacture + " " + description);
                            } else {
                                // En cas d'échec du paiement
                                cnx.envoyer("FACTURE NO erreur lors de la facturation");
                            }
                        } catch (NumberFormatException e) {
                            // Si le montant ne peut pas être converti en un nombre valide
                            cnx.envoyer("FACTURE NO Montant invalide");
                        }
                    }
                    break;

    /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}