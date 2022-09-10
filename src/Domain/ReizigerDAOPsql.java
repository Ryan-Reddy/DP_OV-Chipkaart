package Domain;

import java.util.List;
import java.sql.*;


public class ReizigerDAOPsql implements ReizigerDAO {
        public static void main(String[]args) throws SQLException {
        try {
        // 1. Connect met de database
        Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");

        // 2. Creeer een statement
        Statement myStatement = mijnConn.createStatement();

        // 3. Execute een SQL query
        ResultSet myRs = myStatement.executeQuery("select * from reiziger");

        // 4. Proces de set aan resultaten
        int counter = 0;
        while (myRs.next()) {
        counter++;
        System.out.println("#" + counter + ": " + myRs.getString("achternaam") + ", { " + myRs.getString("geboortedatum") + " }");
        }
        } catch (Exception exc) {
        exc.printStackTrace();
        }
        }


    /**
     * @param reiziger
     * @return het opslaan gelukt?
     */

    @Override
    public boolean save(Reiziger reiziger) {
        return false;
    }

    /**
     * @param reiziger
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    /**
     * @param reiziger
     * @return
     */
    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }

    /**
     * @param id
     * @return informatie over de reiziger, of null.
     */
    @Override
    public Reiziger findById(int id) {
        return null;
    }

    /**
     * @param datum
     * @return lijst van Reizigers wiens geboortedatum overeenkomt met de invoer.
     */
    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        return null;
    }

    /**
     * @return lijst met alle Reizigers in de db
     */
    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
