package Bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Classe.Caddie;
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
            ResultSet rs = BDg.executeQuery(sql, nom);

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
            ResultSet rs = BDg.executeQuery(sql, idCli);

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

    public List<Caddie> getCaddie(String idFacture) {
        List<Caddie> list = new ArrayList<>();


        try {
            String sql = "SELECT * FROM ventes where idFacture = ?";
            ResultSet rs = BDg.executeQuery(sql, idFacture);

            while (rs.next()) {
                String idArticle = rs.getString("idArticle");
                int quant = rs.getInt("quantite");
                String sql1 = "SELECT * FROM articles where id = ?";
                ResultSet rs1 = BDg.executeQuery(sql1, idArticle);
                while (rs1.next()) {
                    String intitule = rs1.getString("intitule");
                    String image = rs1.getString("image");
                    Caddie listeCaddie = new Caddie(quant, intitule, image);
                    list.add(listeCaddie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public void PayFacture(String idFacture) {

        String sql = "UPDATE factures SET paye = 1 WHERE id = ?";
        BDg.executeUpdate(sql, idFacture);
    }

    public void CouperCo() {
        BDg.closeConnection();
    }

}
