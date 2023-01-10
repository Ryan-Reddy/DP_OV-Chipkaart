import DAO.AdresDAO;
import DAO.ReizigerDAO;
import DAOPsql.AdresDAOPsql;
import DAOPsql.OVChipkaartDAOPsql;
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
import java.time.LocalDate;
import java.util.List;

/**
 * The type Main.
 */
public class Main {
    Reiziger sietske;

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        try {
            new Main();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    public Main() throws SQLException {
        sietske = new Reiziger("S", "", "Boers", LocalDate.of(1981, 03, 14));
        Adres adresSietske = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());
        sietske.setAdres_id(adresSietske.getAdres_ID());

        OVChipkaart newOvChipKaart;
        Connection conn = getConnection();
        testController(conn);
        closeConnection(conn);
    }



    public void testController(Connection conn) throws SQLException {
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(conn);
        ProductDAOPsql productDAOPsql = new ProductDAOPsql(conn);
        OVChipkaartDAOPsql ovChipkaartDAOPsql = new OVChipkaartDAOPsql(conn);
        AdresDAOPsql adresDAOPsql = new AdresDAOPsql(conn);

        // TESTS:
        testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie
        testAdresDAO(adresDAOPsql, reizigerDAOPsql, adresDAOPsql);
        testOVChipkaartDAO(ovChipkaartDAOPsql, adresDAOPsql);
        testProductDAO(productDAOPsql);

    }

    private void closeConnection(Connection conn) throws SQLException {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
    }

    private void testReizigerDAO(ReizigerDAO rdao) {
        sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        sout("\n---------- Test ReizigerDAO -------------");
        try {
            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            sout("[Test] [ReizigerDAO.findAll()] geeft aantal reizigers:" + reizigers.size());

            sout("[Test] [CRUD]");
            // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

            // --- // save:
            sout("[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske); // gebeurt al in constructor
            List<Reiziger> reizigersAfterSave = rdao.findAll();
            sout(reizigersAfterSave.size() + " reizigers\n");

            if (reizigersAfterSave.size() > reizigers.size()) sout("[Test] [save] [SUCCESS]");
            else sout("[Test] [save] [FAILURE]");

            // --- // update:
            sout("\n\n[Test] [update] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            sout("[Test] [update] oude achternaam = " + sietskeOudeAchternaam + "\n");
            sietske.setAchternaam("anders");
            rdao.update(sietske);
            sout(" [RESULT] nieuwe achternaam = " + sietske.getAchternaam());
            sout(" [RESULT] nieuwe sietske.tostring = " + sietske.toString());


            // --- // delete:
            sout("[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = rdao.findAll().size();

            boolean succes = rdao.delete(sietske);

            sout("[Test] [delete] [RESULT] = " + succes + " lijst is nu: " + rdao.findAll().size() + " en was: " + preDeleteLijstSize);

//            if (succes = true) {
//                sout("[Test] [delete] [SUCCESS] test geslaagd!");
//            } else {
//                sout("[Test] [delete] [GEFAALD] test gefaald!");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testAdresDAO(AdresDAO adresDAO, ReizigerDAOPsql reizigerDAOPsql, AdresDAOPsql adresDAOPsql) {
        try {
            sout("\n---------- Test AdresDAO -------------");
            sout("[Test] 1 [adresDAO.getAdresByID()] adres_id = 1");
            sout(adresDAO.getAdresByID(1).toString());

            sout("[Test] 2 [adresDAO.getAdresByReiziger()]  geeft de volgende reizigers:");
            Reiziger testReiziger = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            Adres testReizigerAdres = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());
            testReiziger.setAdres_id(testReizigerAdres.getAdres_ID());
            reizigerDAOPsql.save(testReiziger);

            sout("[Test] 4 [save] adresDAO.save()");
            adresDAOPsql.save(testReizigerAdres);

            sout(adresDAO.getAdresByReiziger(testReiziger).toString());
            reizigerDAOPsql.delete(testReiziger);

            sout("[Test] 3 [adresDAO.findAll()] geeft de volgende reizigers:");
            sout(adresDAO.findAll().toString());
//        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findReizigerById(3).getAdres_id()));

            // crud tests

            sout("[Test] 5 [update] adresDAO.update()");
            sout(String.valueOf(adresDAO.update(testReizigerAdres)));

            sout("[Test] 6 [delete] adresDAO.delete()");
            sout(String.valueOf(adresDAO.delete(testReizigerAdres)));

            sout("[Test] 7 [findAll] adresDAO.findAll()");
            sout("[Test] 7 [findAll] [SUCCES] grootte lijst alle adressen: " + adresDAO.findAll().size());


            Reiziger testReizigerLive = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            Date date = Date.valueOf(LocalDate.of(2026, 9, 11));

//
//            newOvChipKaart = new OVChipkaart(adresDAOPsql.findAll().size() + 2, date, 1, 10.00, 5);
//
//            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
//
//            newOvChipKaart.addProductAanKaart(newProduct);
//
//            testReizigerLive.



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testProductDAO(ProductDAOPsql productDAO) {
        sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        sout("\n---------- Test testProductDAO -------------");
        Product newProduct;

        try {
            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
            sout("[Test] [save] productDAO.save() [RESULT] = " + (productDAO.save(newProduct) == true));

            sout("[Test] productDAO.findAll() geeft de volgende reizigers:" + productDAO.findAll().toString());

            sout("[Test] productDAO.findByID() geeft de volgende reizigers:" + productDAO.findByID(newProduct));

            sout("[Test] [update] productDAO.update() [RESULT] = " + productDAO.update(newProduct));

            sout("[Test] [delete] productDAO.delete() [RESULT] = " + productDAO.delete(newProduct));

//            List<Product> productResultaten = productDAO.findByOVChipkaart(newOvChipKaart);
//            productResultaten.forEach(System.out::println);


            sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testOVChipkaartDAO(OVChipkaartDAOPsql ovChipkaartDAO, AdresDAOPsql adresDAOPsql) throws SQLException {
        sout("\n---------- Test ovChipkaartDAO -------------");
        Date date = Date.valueOf(LocalDate.of(2026, 9, 11));
        OVChipkaart newOvChipKaart = new OVChipkaart(adresDAOPsql.findAll().size() + 2, date, 1, 10.00, 5);

        try {
            // TODO schrijf testOVCHIPKAART TEST


            sout("[Test] [save] ovChipkaartDAO.save() with kaart nummer: " + newOvChipKaart.getKaart_nummer() + " [RESULT] = " + ovChipkaartDAO.save(newOvChipKaart));

            sout("[Test] ovChipkaartDAO.findAll() geeft de volgende reizigers:" + " [RESULT] = \n" + ovChipkaartDAO.findAll().toString());

            sout("[Test] ovChipkaartDAO.findByOVChipkaartID() geeft de volgende reizigers: [RESULT] = " + ovChipkaartDAO.findByOVChipkaartID(newOvChipKaart.getKaart_nummer()) + "\n");

            sout("[Test] [update] ovChipkaartDAO.update()" + " [RESULT] = \n" + ovChipkaartDAO.update(newOvChipKaart));

            sout("[Test] [delete] ovChipkaartDAO.delete()" + " [RESULT] = \n" + ovChipkaartDAO.delete(newOvChipKaart));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Sout.
     *
     * @param inputToPrint the input to print
     */
    public static void sout(String inputToPrint) {
        System.out.println(inputToPrint);
    }
}

