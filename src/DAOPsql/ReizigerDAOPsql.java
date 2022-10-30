package DAOPsql;

import DAO.ReizigerDAO;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReizigerDAOPsql implements ReizigerDAO {
    private static Connection localConn;
    String query = null;
    private PreparedStatement myStatement;

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
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?) ";

            PreparedStatement myStatement = localConn.prepareStatement(query);
            myStatement.setInt(1, reiziger.getId());
            myStatement.setString(2, reiziger.getVoorletters());
            myStatement.setString(3, reiziger.getTussenvoegsel());
            myStatement.setString(4, reiziger.getAchternaam());
            myStatement.setDate(5, (Date) reiziger.getGeboortedatum());

//            myStatement.setInt(6, reiziger.getAdres_id());

            return myStatement.executeUpdate() == 1;
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
            String query = "UPDATE reiziger SET reiziger_id = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, (Date) reiziger.getGeboortedatum());
            ps.setInt(6, reiziger.getId());


            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id waarnaar gezocht moet worden
     * @return informatie over de reiziger, of null.
     */
    public Reiziger findReizigerById(int id) {
        query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            int reizigerId = myResultSet.getInt("reiziger_id");
            String voorl = myResultSet.getString("voorletters");
            String tussenv = myResultSet.getString("tussenvoegsel");
            String achtern = myResultSet.getString("achternaam");
            Date gebDatum = myResultSet.getDate("geboortedatum");
            // TODO moet ik ook adres ophalen?

            return new Reiziger(reizigerId, voorl, tussenv, achtern, gebDatum);
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
        String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, datum);

            ResultSet myResultSet = ps.executeQuery();

            // TODO schrijf getallcolumns

            while (myResultSet.next()) {

                int reizigerId = myResultSet.getInt("reiziger_id");
                String voorl = myResultSet.getString("voorletters");
                String tussenv = myResultSet.getString("tussenvoegsel");
                String achtern = myResultSet.getString("achternaam");
                Date gebDatum = myResultSet.getDate("geboortedatum");
                // TODO moet ik ook adres ophalen?

                alleReizigers.add(new Reiziger(reizigerId, voorl, tussenv, achtern, gebDatum));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleReizigers;
    }

    /**
     * @return lijst met alle Reizigers in de db
     */
    @Override
    public ArrayList<Reiziger> findAll() throws SQLException {
        String query = "select * from reiziger";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);

        ResultSet myResultSet = preparedStatement.executeQuery();

        ArrayList<Reiziger> alleReizigers = new ArrayList<Reiziger>();

        try {

            while (myResultSet.next()) {

                int reizigerId = myResultSet.getInt("reiziger_id");
                String voorl = myResultSet.getString("voorletters");
                String tussenv = myResultSet.getString("tussenvoegsel");
                String achtern = myResultSet.getString("achternaam");
                Date gebDatum = myResultSet.getDate("geboortedatum");
                // TODO moet ik ook adres ophalen?

                alleReizigers.add(new Reiziger(reizigerId, voorl, tussenv, achtern, gebDatum));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleReizigers;
    }

    /**
     * @param reiziger de te verwijderen reiziger
     * @return boolean of het gelukt is
     */
    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";

            PreparedStatement ps = localConn.prepareStatement(query);

            ps.setInt(1, reiziger.getId());
            ps.executeQuery();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
