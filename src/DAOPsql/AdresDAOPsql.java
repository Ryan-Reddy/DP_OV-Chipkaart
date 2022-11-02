package DAOPsql;

import DAO.AdresDAO;
import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private static Connection localConn;
    private Statement myStatement;

    public AdresDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }


    public boolean save(Adres adres) {
        String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres.getAdres_ID());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReiziger_id());

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Adres adres) {
        String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(adres.getAdres_ID()));
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param adres
     * @return
     */
    @Override
    public boolean delete(Adres adres) {
        String query = "DELETE FROM adres WHERE adres_id = (adres_id) VALUES (?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(adres.getAdres_ID()));
            ps.executeQuery();
            return true;

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    /**
     * @param adres_id
     * @return
     */
    @Override
    public Adres getAdresByID(int adres_id_toFIND) {
        String query = "SELECT * FROM adres WHERE adres_id = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres_id_toFIND);

            ResultSet myResultSet = ps.executeQuery();
            if (myResultSet.next()) {

                int adres_id2 = myResultSet.getInt("adres_id");
                String postcode = myResultSet.getString("postcode");
                String huisnummer = myResultSet.getString("huisnummer");
                String straat = myResultSet.getString("straat");
                String woonplaats = myResultSet.getString("woonplaats");
                int reiziger_id = myResultSet.getInt("reiziger_id");

                Adres adres = new Adres(postcode, huisnummer, straat, woonplaats, reiziger_id, adres_id2);
                return adres;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * @param reiziger
     * @return
     */
    public Adres getAdresByReiziger(Reiziger reiziger) {
        Adres adres = getAdresByID(reiziger.getAdres_id());
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String query = "select * from adres";

        ArrayList<Adres> alleAdressen = new ArrayList<Adres>();


        try {
            PreparedStatement ps = localConn.prepareStatement(query);

            ResultSet myResultSet = ps.executeQuery();
            try {

                while (myResultSet.next()) {

                    int adres_id = myResultSet.getInt("adres_id");
                    String postcode = myResultSet.getString("postcode");
                    String huisnummer = myResultSet.getString("huisnummer");
                    String straat = myResultSet.getString("straat");
                    String woonplaats = myResultSet.getString("woonplaats");
                    int reiziger_id = myResultSet.getInt("reiziger_id");
                    Adres adres = new Adres(postcode, huisnummer, straat, woonplaats, reiziger_id, adres_id);
//                    adres.setAdres_ID(adres_id2);
                    alleAdressen.add(adres);
                }
                return alleAdressen;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
