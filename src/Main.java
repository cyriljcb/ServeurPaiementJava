import Bean.BeanBDgenerique;
import Bean.BeanBDmetier;
import Classe.Facture;
import OVESP.OVESP;
import ServeurGeneriqueTCP.Logger;
import ServeurGeneriqueTCP.Protocole;
import ServeurGeneriqueTCP.ThreadServeur;
import ServeurGeneriqueTCP.ThreadServeurPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    Protocole protocole;
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
    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                System.out.println("dans le run");
                Protocole protocole;
                ThreadServeur threadServeur;

                BeanBDmetier bean = new BeanBDmetier("jdbc:mysql://192.168.47.128/PourStudent" ,"Student","PassStudent1_");
                bean.LoginEmploye("cyril","123");
                bean.PayFacture("1");
                List<Facture> factures = bean.getFactures("1");
                afficherFactures(factures);

                protocole = new OVESP();
                try {
                    System.out.println("est dans le try");
                    threadServeur = new ThreadServeurPool(50000, protocole, 3);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                threadServeur.start();
            }
        });
    }
}
