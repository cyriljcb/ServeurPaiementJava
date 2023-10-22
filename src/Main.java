import OVESP.*;
import ServeurGeneriqueTCP.Protocole;
import ServeurGeneriqueTCP.ThreadServeur;
import ServeurGeneriqueTCP.ThreadServeurPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Properties properties = new Properties();
                FileInputStream input = null;
                int port = 0;
                int nbthread = 0;

                try {
                    input = new FileInputStream("src\\config.properties");
                    properties.load(input);
                    port = Integer.parseInt(properties.getProperty("PORT_PAIEMENT"));
                    nbthread = Integer.parseInt(properties.getProperty("NB_THREAD"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            System.out.println("erreur de lecture dans le fichier de conf");
                        }
                    }
                }
                Protocole protocole;
                ThreadServeur threadServeur;
                protocole = new OVESP();
                try {
                    //TODO faut faire qqch pour les acces concurrent (mutex,...)
                    //TODO plus interface
                    System.out.println("port : "+port+" nbthread = "+nbthread);
                    threadServeur = new ThreadServeurPool(port, protocole, nbthread);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                threadServeur.start();
            }
        });
    }
}
