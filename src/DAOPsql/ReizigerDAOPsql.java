package DAOPsql;

import DAO.ReizigerDAO;
import domain.Reiziger;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection localConn;
    private Statement myStatement;

    public ReizigerDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }


    /**
     * @param reiziger de reiziger aanmaken, wijzigingen opslaan
     * @return het opslaan gelukt?
     */
    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum, adres_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setString(5, reiziger.getGeboortedatum().toString());
            ps.setString(6, String.valueOf(reiziger.getAdres_id()));

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
     * @param reiziger de up te daten reiziger
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setString(5, reiziger.getGeboortedatum().toString());

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
     * @param reiziger de te verwijderen reiziger
     * @return boolean of het gelukt is
     */
    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = (reiziger_id) VALUES (?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, reiziger.getId());
            ps.executeQuery();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id waarnaar gezocht moet worden
     * @return informatie over de reiziger, of null.
     */
    @Override
    public Reiziger findById(int id) {
        String query = "SELECT * FROM reiziger WHERE reiziger_id =  VALUES (?)";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(id));

            ResultSet myResultSet = ps.executeQuery();
            return (Reiziger) myResultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param datum De reizigers van deze specifieke geboortedatum vinden
     * @return lijst van Reizigers wiens geboortedatum overeenkomt met de invoer.
     */
    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {

        List<Reiziger> alleReizigers = new ArrayList<>();
        String query = "SELECT * FROM reiziger WHERE geboortedatum =  VALUES (?)";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, datum);

            ResultSet myResultSet = ps.executeQuery();
            return (List<Reiziger>) myResultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return lijst met alle Reizigers in de db
     */
    @Override
    public List<Reiziger> findAll() throws SQLException {
        String query = "select * from reiziger";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);

            ResultSet myResultSet = ps.executeQuery();
            return (List<Reiziger>) myResultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
