package DAOPsql;

import DAO.ReizigerDAO;
import domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Reiziger dao psql.
 */
public class ReizigerDAOPsql implements ReizigerDAO {
    /**
     * The Local conn.
     */
    static Connection localConn;
    /**
     * The Query.
     */
    String query = null;
    private PreparedStatement myStatement;

    /**
     * Instantiates a new Reiziger dao psql.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
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

            if (myStatement.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
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

            int result = ps.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Find reiziger by id reiziger.
     *
     * @param reiziger the reiziger
     * @return the reiziger
     */
    @Override
    public Reiziger findReizigerById(Reiziger reiziger) {
        query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            int reizigerId = myResultSet.getInt("reiziger_id");
            String voorl = myResultSet.getString("voorletters");
            String tussenv = myResultSet.getString("tussenvoegsel");
            String achtern = myResultSet.getString("achternaam");
            Date gebDatum = myResultSet.getDate("geboortedatum");
            // TODO moet ik ook adres ophalen?
            LocalDate gebDatumLocal = gebDatum.toLocalDate();

            return new Reiziger(voorl, tussenv, achtern, gebDatumLocal);
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
                LocalDate gebDatumLocal = gebDatum.toLocalDate();

                alleReizigers.add(new Reiziger(voorl, tussenv, achtern, gebDatumLocal));
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
                LocalDate gebDatumLocal = gebDatum.toLocalDate();

                Reiziger opgehaaldeReiziger = new Reiziger(voorl, tussenv, achtern, gebDatumLocal, reizigerId);
                alleReizigers.add(opgehaaldeReiziger);

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
                    // delete adres dat hoort bij reiziger:
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = localConn.prepareStatement(query);
            if (reiziger.getAdres_id() != 0 ) { // if coupled with adressID
                ps.setInt(1, reiziger.getAdres_id());
                ps.execute();
            }

            // delete reiziger zelf:
            String queryReizigerDelete = "DELETE FROM reiziger WHERE reiziger_id = ?";
            ps = localConn.prepareStatement(queryReizigerDelete);
            System.out.println("deleting reiziger with ID: " + reiziger.getId());
            ps.setInt(1, reiziger.getId());
            ps.execute();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("DELETE FAILED" + e);
        }
    }

    @Override
    public int getAdresId(int reizigerId) throws SQLException {
        // Adres_ID ophalen;
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        preparedStatement.setInt(1, reizigerId);
        ResultSet myResultSet = preparedStatement.executeQuery();
        myResultSet.next();

        return myResultSet.getInt("adres_id");
    }

}
