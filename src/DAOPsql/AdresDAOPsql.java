package DAOPsql;

import DAO.AdresDAO;
import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Adres dao psql.
 */
public class AdresDAOPsql implements AdresDAO {
    private static Connection localConn;
    private Statement myStatement;

    /**
     * Instantiates a new Adres dao psql.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
    public AdresDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }


    public Adres save(Adres adres) {
        String query = "INSERT INTO adres (postcode, huisnummer, straat, woonplaats, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getReiziger_id());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creeren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    adres.setAdres_ID(generatedKeys.getInt("adres_id"));
                }
                else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            ps.close();

            return adres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Adres update(Adres adres) {
        String query = "UPDATE adres SET adres_id =?, postcode =?, huisnummer =?, straat =?, woonplaats =?, reiziger_id =? WHERE reiziger_id = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres.getAdres_ID());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReiziger_id());
            ps.setInt(7, adres.getReiziger_id());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();

            return findByID(adres.getAdres_ID());
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
        String query = "DELETE FROM adres WHERE adres_id = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres.getAdres_ID());
            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Delete failed, geen rijen gewijzigd.");
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();

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
    public Adres findByID(int adres_id_toFIND) {
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
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());

            ResultSet rs = ps.executeQuery();
            rs.next();
//
//            ArrayList<Adres> adressen = new ArrayList<Adres>();
//            while (rs.next()) {

                Adres adres = new Adres(rs.getString("postcode"),
                        rs.getString("huisnummer"),
                        rs.getString("straat"),
                        rs.getString("woonplaats"),
                        rs.getInt("reiziger_id"),
                        rs.getInt("adres_id"));
//                adressen.add(adres);
//            }
            return adres;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
