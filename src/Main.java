import DAO.AdresDAO;
import DAOPsql.AdresDAOPsql;
import DAOPsql.ProductDAOPsql;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import DAO.ReizigerDAO;
import DAOPsql.ReizigerDAOPsql;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class Main {
    // Maak een nieuwe reiziger aan en persisteer deze in de database
    static String gbdatum = "1981-03-14";
    static Reiziger sietske = new Reiziger(6, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
    static OVChipkaart ovChipkaart = new OVChipkaart(303, java.util.Date.from(Instant.now()), 2000, "121", 234);

    public static void main(String[] args) throws SQLException {
        try {




            // 1. Connect met de database
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);

            // TESTS:
            // testreizigerDAO:
            testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie

            // testadresDAO:
            testAdresDAO(new AdresDAOPsql(mijnConn));


            ProductDAOPsql productDAOPsql = new ProductDAOPsql(mijnConn);
            List<Product> productResultaten = productDAOPsql.findByOVChipkaart(ovChipkaart);
            productResultaten.forEach(System.out::println);

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


        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        Reiziger reiziger = new Reiziger(12,"voorl","tusv","ach", new java.util.Date());
        System.out.println(reiziger.toString());
        rdao.save(reiziger);



        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        System.out.print("[Test] Eerst " + ((List<?>) reizigers).size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


    }
    private static void testAdresDAO(AdresDAO adresDAO) {
        System.out.println("\n---------- Test AdresDAO -------------");
        // write crud tests

        adresDAO.delete(AdresDAO.getAdresByID(ReizigerDAO.findReizigerById(5).getAdres_id()));
        // TODO makle ^this work

        Adres adres = new Adres(1200,"1221JJ","88","Bontekoestraat","Amsterdam",sietske.getId());
        adresDAO.save(adres);
        adresDAO.update(adres);
        adresDAO.delete(adres);


        System.out.println(adresDAO.findAdresById(1));
        System.out.println(adresDAO.findAdresByReiziger(ReizigerDAO.findReizigerById(2)));

        // TODO schrijf CRUD TESTS

    }
}

