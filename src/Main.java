import Bean.BeanBDgenerique;
import Bean.BeanBDmetier;
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

public class Main {
    Protocole protocole;
    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                System.out.println("dans le run");
                Protocole protocole;
                ThreadServeur threadServeur;
//                try {
//                    Connection con = DriverManager.getConnection("jdbc:mysql://192.168.47.128/PourStudent" ,"Student","PassStudent1_");
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }

                BeanBDmetier bean = new BeanBDmetier("jdbc:mysql://192.168.47.128/PourStudent" ,"Student","PassStudent1_");
                bean.Connect("cyril","123");
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
