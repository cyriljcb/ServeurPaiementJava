package OVESP;

import Bean.BeanBDmetier;
import Classe.Facture;
import ServeurGeneriqueTCP.FinConnexionException;
import ServeurGeneriqueTCP.Logger;
import ServeurGeneriqueTCP.Protocole;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class OVESP implements Protocole {
    private HashMap<String, String> passwords;
    private HashMap<String, Socket> clientsConnectes;

    private Logger logger;
    private  BeanBDmetier bean;
    public static void afficherFactures(List<Facture> factures) {
        for (Facture facture : factures) {
            System.out.println("ID : " + facture.getId());
            System.out.println("ID du client : " + facture.getIdClient());
            System.out.println("Date : " + facture.getDate());
            System.out.println("Montant : " + facture.getMontant());
            System.out.println("Payée : " + facture.isPaye());
            System.out.println();
        }
    }

    public OVESP() {
        passwords = new HashMap<>();
        passwords.put("wagner" , "abcd");
        passwords.put("charlet" , "1234");
        passwords.put("calmant" , "azerty");
        passwords.put("a" , "a");

        //logger = log;
        System.out.println("est passé ovesp");
        clientsConnectes = new HashMap<>();
        bean = new BeanBDmetier("jdbc:mysql://192.168.47.128/PourStudent" , "Student" , "PassStudent1_");
    }

    @Override
    public String getNom() {
        return "OVESP";
    }

    @Override
    public synchronized Reponse TraiteRequete(Requete requete, Socket socket) throws FinConnexionException {
        if (requete instanceof RequeteLogin) return TraiteRequeteLOGIN((RequeteLogin) requete, socket);
        if (requete instanceof RequeteLOGOUT) TraiteRequeteLOGOUT((RequeteLOGOUT) requete);
        if (requete instanceof RequeteFacture) return TraiteRequeteFacture((RequeteFacture) requete);
        return null;
    }

    private synchronized ReponseLogin TraiteRequeteLOGIN(RequeteLogin requete, Socket socket) throws FinConnexionException {
        System.out.println("RequeteLOGIN reçue de " + requete.getLogin());
        String password = passwords.get(requete.getLogin());

       boolean v= bean.LoginEmploye(requete.getLogin() , password);
       if(v)
           clientsConnectes.put(requete.getLogin(), socket);
       return new ReponseLogin(v);

    }
    private synchronized ReponseFacture TraiteRequeteFacture(RequeteFacture requete) throws FinConnexionException{
        System.out.println("RequeteFACTURE reçue " );
        bean.PayFacture("1");
        List<Facture> factures = bean.getFactures("1");
        afficherFactures(factures);
        return new ReponseFacture(factures);

    }

    //
    private synchronized void TraiteRequeteLOGOUT(RequeteLOGOUT requete) throws FinConnexionException {
        System.out.println("RequeteLOGOUT reçue de " + requete.getLogin());
        clientsConnectes.remove(requete.getLogin());
        logger.Trace(requete.getLogin() + " correctement déloggé");
        throw new FinConnexionException(null);
    }


}