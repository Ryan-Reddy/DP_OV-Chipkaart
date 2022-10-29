import DAO.AdresDAO;
import DAOPsql.ProductDAOPsql;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import DAO.ReizigerDAO;
import DAOPsql.ReizigerDAOPsql;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {




            // 1. Connect met de database
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
            System.out.println("findById:");
            System.out.println(reizigerDAOPsql.findReizigerById(2).toString());
            System.out.println("findAll:");
            System.out.println(reizigerDAOPsql.findAll());
            testReizigerDAO(reizigerDAOPsql);


            OVChipkaart ovChipkaart = new OVChipkaart(303, java.util.Date.from(Instant.now()), 2000, "121", 234);

            ProductDAOPsql productDAOPsql = new ProductDAOPsql(mijnConn);
            List<Product> productResultaten = productDAOPsql.findByOVChipkaart(ovChipkaart);
            productResultaten.forEach(System.out::println);

            mijnConn.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    private static void testAdresDAO() {
        System.out.println("\n---------- Test AdresDAO -------------");
        System.out.println(AdresDAO.findAdresById(1));

        System.out.println(AdresDAO.findAdresByReiziger(ReizigerDAO.findReizigerById(2)));

        // TODO schrijf CRUD TESTS

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
        // TODO schrijf CRUD TESTS
    }

}

