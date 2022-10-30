import DAO.AdresDAO;
import DAO.ReizigerDAO;
import DAOPsql.ProductDAOPsql;
import DAOPsql.ReizigerDAOPsql;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
    // 1. Connect met de database
    static Connection mijnConn;
    static ReizigerDAOPsql reizigerDAOPsql;
    static ProductDAOPsql productDAOPsql;
    // Maak een nieuwe reiziger aan en persisteer deze in de database
    static Reiziger sietske;
    static OVChipkaart ovChipkaart = new OVChipkaart(303, java.util.Date.from(Instant.now()), 2000, "121", 234);

    static {
        try {
            reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
            productDAOPsql = new ProductDAOPsql(mijnConn);
            mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            sietske = new Reiziger(reizigerDAOPsql.findAll().size(), "S", "", "Boers", Date.valueOf("1981-03-14"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        try {

            // TESTS:
            // testreizigerDAO:
            testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie

            // testProductDAO:
            testProductDAO(productDAOPsql);
            mijnConn.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        south("\n---------- Test ReizigerDAO -------------");
        try {
            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            south("[Test] [ReizigerDAO.findAll()] geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                south(r.toString());
            }
            south("[Test] [CRUD]");

            // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

            // --- // save:
            south("[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            south(reizigers.size() + " reizigers\n");


            // --- // update:
            south("[Test] [update] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            south("[Test] [update] oude achternaam = " + sietskeOudeAchternaam + "\n");
            sietske.setAchternaam("anders");
            rdao.update(sietske);
            south(sietske.getAchternaam());

            // opruimen na de test
            sietske.setAchternaam(sietskeOudeAchternaam);
            rdao.update(sietske);

            // --- // delete:
            south("[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = rdao.findAll().size();
            south("[Test] [delete] voor delete grootte reizigerslijst = " + preDeleteLijstSize);

            int afterDeleteLijstSize = rdao.findAll().size();
            south("[Test] [delete] na delete grootte reizigerslijst = " + afterDeleteLijstSize);
            int verschilNaVerwijderen = (preDeleteLijstSize - afterDeleteLijstSize);
            if (verschilNaVerwijderen == -1) {
                south("[Test] [delete] test geslaagd!");
            }
            if (verschilNaVerwijderen != -1) {
                south("[Test] [delete] test gefaald!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testAdresDAO(AdresDAO adresDAO
//            , ReizigerDAO reizigerDAO

    ) {
        try {
            south("\n---------- Test AdresDAO -------------");
            // write crud tests
//        south(reizigerDAO.findReizigerById(3).getAdres_id());

            Adres adres = new Adres(1200, "1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());

//        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findReizigerById(3).getAdres_id()));
            // TODO makle ^this work
            south("[Test] [save] ReizigerDAO.save()");
            adresDAO.save(adres);
            south("[Test] [update] ReizigerDAO.update()");
            adresDAO.update(adres);
            south("[Test] [delete] ReizigerDAO.delete()");
            adresDAO.delete(adres);


            south("[Test] [adresDAO.getAdresByID()] adres_id = 1");
            south(adresDAO.getAdresByID(1).toString());

            south("[Test] [ReizigerDAO.findAll()]  geeft de volgende reizigers:");
            south(adresDAO.getAdresByReiziger(sietske).toString());

            south("[Test] [ReizigerDAO.findAll()] geeft de volgende reizigers:");
            south(adresDAO.findAll().toString());

            // TODO
            //  test find allAdressen()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testProductDAO(ProductDAOPsql productDAO) {
        south("\n---------- Test testProductDAO -------------");

        try {
            List<Product> productResultaten = productDAO.findByOVChipkaart(ovChipkaart);
            productResultaten.forEach(System.out::println);

            Product newProduct = new Product(10, "gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);

            south("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            south("[Test] [save] ReizigerDAO.save()");
            south("[Test] [update] ReizigerDAO.update()");
            south("[Test] [delete] ReizigerDAO.delete()");
            // TODO verdeel
            productDAO.save(newProduct);
            productDAO.update(newProduct);
            productDAO.delete(newProduct);
            productDAO.findByID(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void south(String inputToPrint) {
        System.out.println(inputToPrint);
    }
}

