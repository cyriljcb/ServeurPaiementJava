import Bean.BeanBDgenerique;
import Bean.BeanBDmetier;
import Classe.Facture;
import OVESP.*;
import ServeurGeneriqueTCP.Logger;
import ServeurGeneriqueTCP.Protocole;
import ServeurGeneriqueTCP.ThreadServeur;
import ServeurGeneriqueTCP.ThreadServeurPool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Protocole protocole;
                ThreadServeur threadServeur;
                protocole = new OVESP();
                try {
                    //TODO faut faire un fichier de conf pour le numero de port
                    threadServeur = new ThreadServeurPool(51000, protocole, 3);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                threadServeur.start();

            }
        });
    }
}
