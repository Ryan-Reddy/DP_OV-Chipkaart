import Domain.Reiziger;
import Domain.ReizigerDAO;
import Domain.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // 1. Connect met de database
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
//            // TODO initializeer de ReizigerDAOPsql connectie, ongeveer zo:
//            ReizigerDAOPsql adao = new ReizigerDAOPsql(mijnConn);
//            // 2. Creeer een statement
//            Statement myStatement = mijnConn.createStatement();
//            // 3. Execute een SQL query
//            ResultSet myRs = myStatement.executeQuery("select * from reiziger");
//            // 4. Proces de set aan resultaten
//            int counter = 0;
//            while (myRs.next()) {
//                counter++;
//                System.out.println("#" + counter + ": " + myRs.getString("achternaam") + ", { " + myRs.getString("geboortedatum") + " }");
//            }
            ReizigerDAOPsql daopsql = new ReizigerDAOPsql(mijnConn);

            System.out.println(daopsql.findAll());
            testReizigerDAO(daopsql);
            mijnConn.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + ((List<?>) reizigers).size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}

