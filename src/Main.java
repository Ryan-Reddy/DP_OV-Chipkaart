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
    // 1. Connect met de database
    static Connection mijnConn;

    static {
        try {
            mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static ReizigerDAOPsql reizigerDAOPsql;

    static {
        try {
            reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Maak een nieuwe reiziger aan en persisteer deze in de database
    static Reiziger sietske;

    static {
        try {
            sietske = new Reiziger(reizigerDAOPsql.findAll().size(), "S", "", "Boers", Date.valueOf("1981-03-14"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static OVChipkaart ovChipkaart = new OVChipkaart(303, java.util.Date.from(Instant.now()), 2000, "121", 234);

    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        try {

            // TESTS:
            // testreizigerDAO:
            testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie

            // TODO finish test ADRESDAO
            // testadresDAO:
//            testAdresDAO(new AdresDAOPsql(mijnConn)
////                    , new ReizigerDAOPsql(mijnConn)
//            );

            // testProductDAO:
            ProductDAOPsql productDAOPsql = new ProductDAOPsql(mijnConn);
            testProductDAO(productDAOPsql);

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
        try {
            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                System.out.println(r);
            }
            System.out.println();

            // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

            // --- // save:
            System.out.println("[Test] ReizigerDAO.save()");
            System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            System.out.println(reizigers.size() + " reizigers\n");


            // --- // update:
            System.out.println("[Test] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            System.out.println("oude achternaam = " + sietskeOudeAchternaam);
            sietske.setAchternaam("anders");
            rdao.update(sietske);
            System.out.println(sietske.getAchternaam());

            // opruimen na de test
            sietske.setAchternaam(sietskeOudeAchternaam);
            rdao.update(sietske);

            // --- // delete:
            System.out.println("[Test] ReizigerDAO.delete()");
            int preDeleteLijstSize = rdao.findAll().size();
            System.out.println("voor delete grootte reizigerslijst = " + preDeleteLijstSize);

            int afterDeleteLijstSize = rdao.findAll().size();
            System.out.println("na delete grootte reizigerslijst = " + afterDeleteLijstSize);
            int verschilNaVerwijderen = (preDeleteLijstSize - afterDeleteLijstSize);
            if (verschilNaVerwijderen == -1) {
                System.out.println("test geslaagd!");
            }
            if (verschilNaVerwijderen != -1) {
                System.out.println("test gefaald!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        }
    private static void testAdresDAO(AdresDAO adresDAO
//            , ReizigerDAO reizigerDAO

    ) {
        try {
        System.out.println("\n---------- Test AdresDAO -------------");
        // write crud tests
//        System.out.println(reizigerDAO.findReizigerById(3).getAdres_id());

        Adres adres = new Adres(1200,"1221JJ","88","Bontekoestraat","Amsterdam",sietske.getId());

//        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findReizigerById(3).getAdres_id()));
        // TODO makle ^this work
        adresDAO.save(adres);
        adresDAO.update(adres);
        adresDAO.delete(adres);


        System.out.println(adresDAO.getAdresByID(1));
//        System.out.println(adresDAO.getAdresByReiziger(reizigerDAO.findReizigerById(2)));

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private static void testProductDAO(ProductDAOPsql productDAO) {
        try {
        List<Product> productResultaten = productDAO.findByOVChipkaart(ovChipkaart);
        productResultaten.forEach(System.out::println);

        Product newProduct = new Product(10,"gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);

        productDAO.save(newProduct);
        productDAO.update(newProduct);
        productDAO.delete(newProduct);
        productDAO.findByID(10);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

