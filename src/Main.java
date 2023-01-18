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
import org.w3c.dom.ls.LSOutput;

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
    ReizigerDAO reizigerDAOPsql;
    OVChipkaartDAO ovChipkaartDAOPsql;
    ProductDAO productDAOPsql;
    AdresDAO adresDAOPsql;
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
        sietske.setAdres(adresSietske);

        Connection conn = getConnection();
        this.reizigerDAOPsql = new ReizigerDAOPsql(conn);
        this.ovChipkaartDAOPsql = new OVChipkaartDAOPsql(conn);
        this.productDAOPsql = new ProductDAOPsql(conn);
        this.adresDAOPsql = new AdresDAOPsql(conn);

        testController(conn); // runs the tests!

        closeConnection(conn);
    }
    public void testController(Connection conn) throws SQLException {


        reizigerDAOPsql.setAdresDAO(adresDAOPsql);
        reizigerDAOPsql.setProductDAOPsql(productDAOPsql);
        reizigerDAOPsql.setOvChipkaartDAO(ovChipkaartDAOPsql);

        ovChipkaartDAOPsql.setAdresDAO(adresDAOPsql);
        ovChipkaartDAOPsql.setReizigerDAO(reizigerDAOPsql);

        productDAOPsql.setOVChipkaartDAO(ovChipkaartDAOPsql);

//        testReizigerDAO(); // dependency injection van de connectie
//        testAdresDAO();
//        testOVChipkaartDAO();
//        testProductDAO();
        testScenario();
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
    private void testReizigerDAO() {
        pprint("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        pprint("\n---------- Test ReizigerDAO -------------");
        try {
            this.reizigerDAOPsql.setAdresDAO(adresDAOPsql);
            reizigerDAOPsql.setOvChipkaartDAO(ovChipkaartDAOPsql);

            List<Reiziger> reizigers = reizigerDAOPsql.findAll();
            pprint("----------\n[Test] [ReizigerDAO.findAll()] geeft aantal reizigers:"
                    + reizigers.size() + "\n");

            pprint("----------\n[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst "
                    + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            Reiziger newSietske = reizigerDAOPsql.save(sietske);

            List<Reiziger> reizigersAfterSave = reizigerDAOPsql.findAll();
            pprint(reizigersAfterSave.size() + " reizigers");
            if (reizigersAfterSave.size() > reizigers.size()) pprint("----------\n[Test] [save] [SUCCESS]\n");
            else pprint("[Test] [save] [FAILURE]\n");

            pprint("----------\n[Test] [update] ReizigerDAO.update()");
            pprint("[Test] [update] oude achternaam = "
                    + sietske.getAchternaam());
            sietske.setAchternaam("anders");
            Reiziger updatedSietske = reizigerDAOPsql.update(newSietske);
            pprint("[Test] [update] [RESULT] nieuwe achternaam = " + updatedSietske.getAchternaam());
            pprint("[Test] [update] [RESULT] nieuwe sietske.toString() = " + newSietske);

            pprint("\n----------\n[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = reizigerDAOPsql.findAll().size();
            boolean succes = reizigerDAOPsql.delete(newSietske);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testOVChipkaartDAO() throws SQLException {
        pprint("\n---------- Test ovChipkaartDAO -------------");
        LocalDate geldig_tot = LocalDate.of(2026, 9, 11);

        int kaartNum = adresDAOPsql.findAll().size();
        OVChipkaart newOvChipKaart = new OVChipkaart(geldig_tot, 1, 10.00, sietske);

        try {
            // TODO schrijf testOVCHIPKAART TEST

            pprint("----------\n[Test] [save] ovChipkaartDAO.save() with kaart nummer: "
                    + newOvChipKaart.getKaart_nummer() + " [RESULT] = " + ovChipkaartDAOPsql.save(newOvChipKaart));
            pprint("\n----------\n[Test] [findall] ovChipkaartDAO.findAll() geeft de volgende reizigers:" + " [RESULT] = \n"
                    + ovChipkaartDAOPsql.findAll().toString());
            pprint("\n----------\n[Test] [findbyid] ovChipkaartDAO.findbyid() geeft de volgende reiziger:" + " [RESULT] = \n"
                    + ovChipkaartDAOPsql.findByID(19).toString());
            pprint("\n----------\n[Test] [findByID] ovChipkaartDAO.findByID() geeft de volgende OV-chipkaart: [RESULT] = "
                    + ovChipkaartDAOPsql.findByID(newOvChipKaart.getKaart_nummer()));
            pprint("\n----------\n[Test] [update] ovChipkaartDAO.update()" + " [RESULT] = "
                    + ovChipkaartDAOPsql.update(newOvChipKaart));
            pprint("\n----------\n[Test] [delete] ovChipkaartDAO.delete()" + " [RESULT] = "
                    + ovChipkaartDAOPsql.delete(newOvChipKaart));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testAdresDAO() {
        try {
            pprint("\n---------- Test AdresDAO -------------");

            // findByID
            pprint("----------\n[Test] 1 [adresDAO.findByID()] adres_id = 1");
            pprint(adresDAOPsql.findByID(1).toString());

            // findByReiziger
            pprint("----------\n[Test] 2 [adresDAO.findByReiziger()]  geeft de volgende adressen:");
            Reiziger testReiziger = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            testReiziger = reizigerDAOPsql.save(testReiziger);
            pprint("222222222222");
            System.out.println(testReiziger.toString());
            Adres testReizigerAdres = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", testReiziger.getId());
            testReiziger.setAdres(testReizigerAdres);

            // findAll
            pprint("----------\n----------\n[Test] 3 [adresDAO.findAll()] geeft de volgende adressen:");
            adresDAOPsql.findAll().stream().forEach(adres -> pprint(adres.toString()));
            pprint("----------");

            // save
            pprint("----------\n[Test] 4 [save] adresDAO.save()");
            adresDAOPsql.save(testReizigerAdres);

            // findByReiziger
            pprint(adresDAOPsql.findByReiziger(testReiziger).toString());
            reizigerDAOPsql.delete(testReiziger);

            // update
            pprint("----------\n[Test] 5 [update] adresDAO.update()");
            pprint(String.valueOf(adresDAOPsql.update(testReizigerAdres)));

            // delete
            pprint("----------\n[Test] 6 [delete] adresDAO.delete()");
            pprint(String.valueOf(adresDAOPsql.delete(testReizigerAdres)));
            //        adresDAO.delete(adresDAO.findByID(reizigerDAO.findByID(3).getAdres_id()));

            pprint("----------\n[Test] 7 [findAll] adresDAO.findAll()");
            pprint("----------\n[Test] 7 [findAll] [SUCCES] grootte lijst alle adressen: " + adresDAOPsql.findAll().size());

            Reiziger testReizigerLive = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            Date date = Date.valueOf(LocalDate.of(2026, 9, 11));

//            newOvChipKaart = new OVChipkaart(adresDAOPsql.findAll().size() + 2, date, 1, 10.00, 5);
//            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
//            newOvChipKaart.addProductAanKaart(newProduct);
//            testReizigerLive.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testProductDAO() {
        pprint("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        pprint("\n---------- Test testProductDAO -------------");
        Product newProduct;

        try {
            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
            pprint("----------\n[Test] [save] productDAO.save() New ID = " + (this.productDAOPsql.save(newProduct).getId()));

            pprint("----------\n[Test] productDAO.findAll() geeft de volgende producten:" + this.productDAOPsql.findAll().toString());

            pprint("----------\n[Test] productDAO.findByID() geeft de volgende producten:" + this.productDAOPsql.findByID(1));

            pprint("----------\n[Test] [update] productDAO.update() [RESULT] = " + this.productDAOPsql.update(newProduct));

            pprint("----------\n[Test] [delete] productDAO.delete() [RESULT] = " + this.productDAOPsql.delete(newProduct));

            pprint("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testScenario() throws SQLException {
        pprint("---------- Test testScenario -------------");

        //      Je maakt een reiziger,
        pprint("\n----------## nieuwe scenarioReiziger, incl newReizigerID");
        Reiziger scenarioReiziger = new Reiziger("Scenario", "to", "Win", LocalDate.of(1966, 9, 21));
        pprint("+ saving scenarioReiziger: " + scenarioReiziger);
        Reiziger opgeslagenScenarioReiziger =  reizigerDAOPsql.save(scenarioReiziger);
        pprint(opgeslagenScenarioReiziger.toString()); // nu incl. ID#

        //      Je maakt een adres, koppel adres aan reiziger
        pprint("\n----------## nieuwe scenarioAdresse");
        Adres scenarioAdresse= adresDAOPsql.save(new Adres("1067AA", "10", "SesameStreet", "Amsterdam", opgeslagenScenarioReiziger.getId()));
        System.out.println("scenarioAdresse incl new ID" + scenarioAdresse + "with reizigerID: " + scenarioAdresse.getReiziger_id());

        opgeslagenScenarioReiziger.setAdres(scenarioAdresse);
        System.out.println("update reiziger adres: "+ opgeslagenScenarioReiziger.getAdres().toString());
        opgeslagenScenarioReiziger = reizigerDAOPsql.update(scenarioReiziger);
        System.out.println("updated reiziger: " + opgeslagenScenarioReiziger.toString());

        //koppelt daaraan een nieuwe OV-Chipkaart,
        pprint("\n----------## nieuwe scenarioOVChipkaart, gelijk gekoppeld aan de newReizigerID");
        LocalDate today = java.time.LocalDate.now();
        OVChipkaart scenarioOVChipkaart = new OVChipkaart(today, 1,20.32, opgeslagenScenarioReiziger);

        pprint("+ saving scenarioOVChipkaart");
        scenarioOVChipkaart = ovChipkaartDAOPsql.save(scenarioOVChipkaart);
        pprint(scenarioOVChipkaart.toString());

        // en koppelt daaraan twee producten.
        System.out.println("\n----------## Koppel daar aan twee producten");
        Product productA = productDAOPsql.save( // productA
                new Product("A", "A", 10));
        scenarioOVChipkaart.addProductAanKaart(productA);
        System.out.println("+ updated kaart incl productA: " + ovChipkaartDAOPsql.update(scenarioOVChipkaart));

        Product productB = productDAOPsql.save( // productB
                new Product("B", "B", 10));
        scenarioOVChipkaart.addProductAanKaart(productB);
        System.out.println("+ updated kaart incl productA + productB: " + ovChipkaartDAOPsql.update(scenarioOVChipkaart));

        //      2. Je verwijdert de OV-chipkaart, maar de producten blijven bestaan.
        pprint("----------\n[Test] [update] deleting scenarioOVChipkaart");
        ovChipkaartDAOPsql.delete(scenarioOVChipkaart);

            //      3. Je zoekt per product welke OV-chipkaarten daarbij horen.
            //      4. Je zoekt per OV-chipkaart welke producten er op staanJe update een attribuut van het product (bijvoorbeeld de prijs).
            //      5. Daarna laat je zien dat de verandering zowel op database-niveau als op Java-klasse-niveau zichtbaar is bij een willekeurige OV-chipkaart die dat product heeft geregistreerd.
            //      6. Je hebt een pprint-statement voor een reiziger (reiziger1.toString() o.i.d.) dat de gekoppelde OV-chipkaarten en gekoppelde producten laat zien.
            //
            //          Je laat de tests zowel zien op de code die gebruik maakt van Hibernate als op de code die gebruik maakt van DAIO's
    }
    /**
     * pprint.
     * @param inputToPrint the input to pprint
     */
    public static void pprint(String inputToPrint) {
        System.out.println(inputToPrint);
    }
}

