package Bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Classe.Facture;

public class BeanBDmetier {
    private BeanBDgenerique BDg;

    public BeanBDmetier(String URLJDBC, String nom, String mdp) {
        BDg = new BeanBDgenerique(URLJDBC, nom, mdp);
    }

    public boolean LoginEmploye(String nom, String mdp) {
        boolean test = false;
        try {
            String sql = "SELECT * FROM employes WHERE login = ?";
            ResultSet rs = BDg.executeQuery(sql,nom);

            while (rs.next()) {
                if (rs.getString(3).equals(mdp)) {
                    System.out.println(rs.getObject(3) + "\t");
                    test = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return test;
    }
    public List<Facture> getFactures(String idCli) {
        List<Facture> factures = new ArrayList<>();

        try {
            String sql = "SELECT * FROM factures where idClient = ?";
            ResultSet rs = BDg.executeQuery(sql,idCli);

            while (rs.next()) {
                int id = rs.getInt("id");
                int idClient = rs.getInt("idClient");
                String date = rs.getString("date");
                float montant = rs.getFloat("montant");
                boolean paye = rs.getBoolean("paye");

                Facture facture = new Facture(id, idClient, date, montant, paye);
                factures.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factures;
    }
    public void PayFacture(String idFacture)
    {

            String sql = "UPDATE factures SET paye = 1 WHERE id = ?";
            BDg.executeUpdate(sql,idFacture);
    }
    public void CouperCo()
    {
        BDg.closeConnection();
    }

}
