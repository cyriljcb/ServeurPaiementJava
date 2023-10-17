package Bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanBDmetier {
    BeanBDgenerique BDg;
    public  BeanBDmetier(String URLJDBC, String nom,String mdp)
    {
        BDg = new BeanBDgenerique(URLJDBC,nom,mdp);
//        ResultSet rs = BDg.executeReq("select * from clients");
//        try {
//            while (rs.next()) {
//                for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
//                    System.out.print(rs.getObject(j) + "\t");
//                }
//                System.out.println();
//            }
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }
    }
    public boolean Connect(String nom, String mdp) {
        boolean test = false;
        try {
            String query = "SELECT * FROM employes WHERE login = ?";   //pour éviter l'injection sql
            PreparedStatement pstmt = BDg.getConnection().prepareStatement(query);
            ((PreparedStatement) pstmt).setString(1, nom); // Remplace le paramètre dans la requête

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
//                for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
//                    System.out.print(rs.getObject(j) + "\t");
//                    test = true;
//                }
                if(rs.getString(3).equals(mdp))
                {
                    System.out.println(rs.getObject(3) + "\t");
                    test = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return test;
    }


}
