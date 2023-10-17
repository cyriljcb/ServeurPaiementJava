package Bean;

import java.sql.*;

public class BeanBDgenerique {
    private Connection con;

    public BeanBDgenerique(String URLJDBC, String nom,String mdp)
    {
        try {
            con = DriverManager.getConnection(URLJDBC, nom, mdp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet executeReq(String sql)
    {
        try {
            Statement instr = con.createStatement();
            ResultSet rs = instr.executeQuery(sql);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Connection getConnection() {
        return con;
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
