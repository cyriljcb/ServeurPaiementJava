package ServeurGeneriqueTCP;
import OVESP.ReponseLogin;
import OVESP.Requete;

import java.net.Socket;

public interface Protocole {
    String getNom();
    ReponseLogin TraiteRequete(Requete requete, Socket socket) throws FinConnexionException;

}
