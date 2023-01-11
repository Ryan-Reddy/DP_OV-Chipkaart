import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
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
import java.util.ArrayList;
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
        ReizigerDAO reizigerDAOPsql = new ReizigerDAOPsql(conn);
        ProductDAO productDAOPsql = new ProductDAOPsql(conn);
        OVChipkaartDAO ovChipkaartDAOPsql = new OVChipkaartDAOPsql(conn);
        AdresDAO adresDAOPsql = new AdresDAOPsql(conn);
        // TESTS:
        // TODO: uncomment -->
//        testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie
//        testOVChipkaartDAO(ovChipkaartDAOPsql, adresDAOPsql);
//        testAdresDAO(reizigerDAOPsql, adresDAOPsql);
//        testProductDAO(productDAOPsql);
        testScenario(reizigerDAOPsql, ovChipkaartDAOPsql, adresDAOPsql, productDAOPsql);
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
            List<Reiziger> reizigers = rdao.findAll();
            sout("[Test] [ReizigerDAO.findAll()] geeft aantal reizigers:" + reizigers.size() + "\n");

            sout("[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske);
            List<Reiziger> reizigersAfterSave = rdao.findAll();
            sout(reizigersAfterSave.size() + " reizigers");
            if (reizigersAfterSave.size() > reizigers.size()) sout("[Test] [save] [SUCCESS]\n");
            else sout("[Test] [save] [FAILURE]\n");

            sout("[Test] [update] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            sout("[Test] [update] oude achternaam = " + sietskeOudeAchternaam);
            sietske.setAchternaam("anders");
            rdao.update(sietske);
            sout("[Test] [update] [RESULT] nieuwe achternaam = " + sietske.getAchternaam());
            sout("[Test] [update] [RESULT] nieuwe sietske.toString() = " + sietske.toString());

            sout("\n[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = rdao.findAll().size();
            boolean succes = rdao.delete(sietske);
            sout("[Test] [delete] [RESULT] = " + succes + " lijst is nu: " + rdao.findAll().size() + " en was: " + preDeleteLijstSize);
            if (succes = true) {
                sout("[Test] [delete] [SUCCESS] test geslaagd!");
            } else {
                sout("[Test] [delete] [GEFAALD] test gefaald!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testOVChipkaartDAO(OVChipkaartDAO ovChipkaartDAOPsql, AdresDAO adresDAOPsql) throws SQLException {
        sout("\n---------- Test ovChipkaartDAO -------------");
        LocalDate geldig_tot = LocalDate.of(2026, 9, 11);

        int kaartNum = adresDAOPsql.findAll().size() + 1;
        OVChipkaart newOvChipKaart = new OVChipkaart(kaartNum, geldig_tot, 1, 10.00, 5);

        try {
            // TODO schrijf testOVCHIPKAART TEST

            sout("[Test] [save] ovChipkaartDAO.save() with kaart nummer: " + newOvChipKaart.getKaart_nummer() + " [RESULT] = " + ovChipkaartDAOPsql.save(newOvChipKaart));

            sout("\n[Test] [findall] ovChipkaartDAO.findAll() geeft de volgende reizigers:" + " [RESULT] = \n" + ovChipkaartDAOPsql.findAll().toString());
            sout("\n[Test] [findbyid] ovChipkaartDAO.findbyid() geeft de volgende reiziger:" + " [RESULT] = \n" + ovChipkaartDAOPsql.findByID(19).toString());

            sout("\n[Test] [save] ovChipkaartDAO.findByID() geeft de volgende OV-chipkaart: [RESULT] = " + ovChipkaartDAOPsql.findByID(newOvChipKaart.getKaart_nummer()));

            sout("\n[Test] [update] ovChipkaartDAO.update()" + " [RESULT] = " + ovChipkaartDAOPsql.update(newOvChipKaart));

            sout("\n[Test] [delete] ovChipkaartDAO.delete()" + " [RESULT] = " + ovChipkaartDAOPsql.delete(newOvChipKaart));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testAdresDAO(ReizigerDAO reizigerDAOPsql, AdresDAO adresDAOPsql) {
        try {
            sout("\n---------- Test AdresDAO -------------");

            // getAdresByID
            sout("[Test] 1 [adresDAO.getAdresByID()] adres_id = 1");
            sout(adresDAOPsql.getAdresByID(1).toString());

            // getAdresByReiziger
            sout("[Test] 2 [adresDAO.getAdresByReiziger()]  geeft de volgende adressen:");
            Reiziger testReiziger = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            Adres testReizigerAdres = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());
            testReiziger.setAdres_id(testReizigerAdres.getAdres_ID());
            reizigerDAOPsql.save(testReiziger);


            // findAll
            sout("[Test] 3 [adresDAO.findAll()] geeft de volgende adressen:");
            sout(adresDAOPsql.findAll().toString());

            // save
            sout("[Test] 4 [save] adresDAO.save()");
            adresDAOPsql.save(testReizigerAdres);

            // getAdresByReiziger
            sout(adresDAOPsql.getAdresByReiziger(testReiziger).toString());
            reizigerDAOPsql.delete(testReiziger);


            // update
            sout("[Test] 5 [update] adresDAO.update()");
            sout(String.valueOf(adresDAOPsql.update(testReizigerAdres)));

            // delete
            sout("[Test] 6 [delete] adresDAO.delete()");
            sout(String.valueOf(adresDAOPsql.delete(testReizigerAdres)));
            //        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findById(3).getAdres_id()));

            sout("[Test] 7 [findAll] adresDAO.findAll()");
            sout("[Test] 7 [findAll] [SUCCES] grootte lijst alle adressen: " + adresDAOPsql.findAll().size());


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
    private void testProductDAO(ProductDAO productDAO) {
        sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        sout("\n---------- Test testProductDAO -------------");
        Product newProduct;

        try {
            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
            sout("[Test] [save] productDAO.save() [RESULT] = " + (productDAO.save(newProduct) == true));

            sout("[Test] productDAO.findAll() geeft de volgende producten:" + productDAO.findAll().toString());

            sout("[Test] productDAO.findByID() geeft de volgende producten:" + productDAO.findByID(1));

            sout("\n[Test] [update] productDAO.update() [RESULT] = " + productDAO.update(newProduct));

            sout("\n[Test] [delete] productDAO.delete() [RESULT] = " + productDAO.delete(newProduct));

            sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testScenario(ReizigerDAO reizigerDAOPsql, OVChipkaartDAO ovChipkaartDAOPsql, AdresDAO adresDAOPsql, ProductDAO productDAO)
            throws SQLException {
        sout("---------- Test testScenario -------------");

        // [13:23] Roelant Ossewaarde
        //      1. Je maakt een reiziger, koppelt daaraan een nieuwe OV-Chipkaart, en koppelt daaraan twee producten.
        int newId = reizigerDAOPsql.findAll().size() + 1;
        Reiziger scenarioReiziger = new Reiziger("Scenario", "to", "Win", LocalDate.of(1966, 9, 21), newId);
        sout("+ saving scenarioReiziger");
        reizigerDAOPsql.save(scenarioReiziger);

        int newChipkaartId = ovChipkaartDAOPsql.findAll().size() + 1;
        LocalDate today = java.time.LocalDate.now();
        OVChipkaart scenarioOVChipkaart = new OVChipkaart(newChipkaartId, today, 1,20.32, newId);
        sout("+ saving scenarioOVChipkaart");
        ovChipkaartDAOPsql.save(scenarioOVChipkaart);

        ArrayList<Product> alleProducten = (ArrayList<Product>) productDAO.findAll();

        Product Product0 = alleProducten.get(0);
        Product Product1 = alleProducten.get(1);

        System.out.println("voegt product 0 toe aan kaart");
        scenarioOVChipkaart.addProductAanKaart(Product0);
        System.out.println("voegt product 1 toe aan kaart");
        scenarioOVChipkaart.addProductAanKaart(Product1);

        sout("+ updating scenarioOVChipkaart");
        ovChipkaartDAOPsql.update(scenarioOVChipkaart);

        //      2. Je verwijdert de OV-chipkaart, maar de producten blijven bestaan.
        sout("+ deleting scenarioOVChipkaart");
        ovChipkaartDAOPsql.delete(scenarioOVChipkaart);


            //      3. Je zoekt per product welke OV-chipkaarten daarbij horen.
            //      4. Je zoekt per OV-chipkaart welke producten er op staanJe update een attribuut van het product (bijvoorbeeld de prijs).
            //      5. Daarna laat je zien dat de verandering zowel op database-niveau als op Java-klasse-niveau zichtbaar is bij een willekeurige OV-chipkaart die dat product heeft geregistreerd.
            //      6. Je hebt een print-statement voor een reiziger (reiziger1.toString() o.i.d.) dat de gekoppelde OV-chipkaarten en gekoppelde producten laat zien.
            //
            //          Je laat de tests zowel zien op de code die gebruik maakt van Hibernate als op de code die gebruik maakt van DAIO's

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

