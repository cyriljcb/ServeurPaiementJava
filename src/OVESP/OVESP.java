package OVESP;

import ServeurGeneriqueTCP.FinConnexionException;
import ServeurGeneriqueTCP.Logger;
import ServeurGeneriqueTCP.Protocole;

import java.net.Socket;
import java.util.HashMap;

public class OVESP implements Protocole {
    private HashMap<String, String> passwords;
    private HashMap<String, Socket> clientsConnectes;

    private Logger logger;

    public OVESP() {
        passwords = new HashMap<>();
        passwords.put("wagner", "abcd");
        passwords.put("charlet", "1234");
        passwords.put("calmant", "azerty");
        passwords.put("a", "a");

        //logger = log;
        System.out.println("est passé ovesp");
        clientsConnectes = new HashMap<>();
    }

    @Override
    public String getNom() {
        return "OVESP";
    }

    @Override
    public synchronized ReponseLogin TraiteRequete(Requete requete, Socket socket) throws FinConnexionException {
        if (requete instanceof RequeteLogin) return TraiteRequeteLOGIN((RequeteLogin)
                requete, socket);
        if (requete instanceof RequeteLOGOUT) TraiteRequeteLOGOUT((RequeteLOGOUT)
                requete);
        return null;
    }

    private synchronized ReponseLogin TraiteRequeteLOGIN(RequeteLogin requete, Socket
            socket) throws FinConnexionException {
        System.out.println("RequeteLOGIN reçue de " + requete.getLogin());
        String password = passwords.get(requete.getLogin());
        if (password != null)
            if (password.equals(requete.getPassword())) {
                String ipPortClient = socket.getInetAddress().getHostAddress() + "/"
                        + socket.getPort();
                System.out.println(requete.getLogin() + " correctement loggé de " +
                        ipPortClient);
                clientsConnectes.put(requete.getLogin(), socket);
                return new ReponseLogin(true);
            }
        System.out.println(requete.getLogin() + " --> erreur de login");
        throw new FinConnexionException(new ReponseLogin(false));
    }

    private synchronized void TraiteRequeteLOGOUT(RequeteLOGOUT requete) throws
            FinConnexionException {
        System.out.println("RequeteLOGOUT reçue de " + requete.getLogin());
        clientsConnectes.remove(requete.getLogin());
        logger.Trace(requete.getLogin() + " correctement déloggé");
        throw new FinConnexionException(null);
    }

}