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
        List<Reiziger> alleReizigers = new ArrayList<>();
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
     * @param reiziger de up te daten reiziger
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Reiziger reiziger) {

            try {
                
                ResultSet myResultSet = myStatement.executeQuery(
                        "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                                "VALUES (reiziger.getId(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum()");
                return true;
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
                ResultSet myResultSet = myStatement.executeQuery(
                        String.format("DELETE FROM reiziger WHERE reiziger_id = %s", reiziger.getId()));
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
        List<Reiziger> alleReizigers = new ArrayList<>();

        Statement myStatement = null;
        try {
            myStatement = localConn.createStatement();
            ResultSet myResultSet = myStatement.executeQuery("" +
                    "SELECT * FROM reiziger WHERE reiziger_id = " + id);

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

        Statement myStatement = null;
        try {
            myStatement = localConn.createStatement();
        ResultSet myResultSet = myStatement.executeQuery("" +
                "SELECT * FROM reiziger WHERE geboortedatum = " + datum);

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
        List<Reiziger> alleReizigers = new ArrayList<>();

        Statement myStatement = null;
        try {
            myStatement = localConn.createStatement();
            ResultSet myResultSet = myStatement.executeQuery("select * from reiziger");

            int counter = 0;
            while (myResultSet.next()) {
                int id = myResultSet.getInt("reiziger_id");
                String voorletters = myResultSet.getString("voorletters");
                String tussenvoegsel = myResultSet.getString("tussenvoegsel");
                String achternaam = myResultSet.getString("achternaam");
                Date geboortedatum = myResultSet.getDate("geboortedatum");

                alleReizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
        return alleReizigers;

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        // TODO schrijf functie findAll()
        //https://github.com/eugenp/tutorials/blob/master/patterns/design-patterns-architectural/src/main/java/com/baeldung/daopattern/daos/JpaUserDao.java
    }
}
