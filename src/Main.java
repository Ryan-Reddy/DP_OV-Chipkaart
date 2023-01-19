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
        ovChipkaartDAOPsql.setProductDAO(productDAOPsql);

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
            pprint("[Test] [update] [RESULT] nieuwe achternaam = " + updatedSietske.getAchternaam()
                + "[Test] [update] [RESULT] nieuwe sietske.toString() = " + newSietske);

            pprint("\n----------\n[Test] [delete] ReizigerDAO.delete()");
            reizigerDAOPsql.delete(newSietske);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testOVChipkaartDAO() throws SQLException {
        pprint("\n---------- Test ovChipkaartDAO -------------");
        LocalDate geldig_tot = LocalDate.of(2026, 9, 11);
        Reiziger reiziger = reizigerDAOPsql.save(new Reiziger("ovCHIP","test","reiziger", LocalDate.of(1995, 03, 21)));
        OVChipkaart newOvChipKaart = ovChipkaartDAOPsql.save(new OVChipkaart(geldig_tot, 1, 10.00, reiziger));

        try {
            pprint("----------\n[Test] [save] ovChipkaartDAO.save() with kaart nummer: "
                    + newOvChipKaart.getKaart_nummer() + " [RESULT] = " + ovChipkaartDAOPsql.save(newOvChipKaart));
            pprint("\n----------\n[Test] [findall] ovChipkaartDAO.findAll() geeft de volgende ovchips:" + " [RESULT] = \n"
                    + ovChipkaartDAOPsql.findAll().toString());
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
        Reiziger testReiziger = null;
        Adres testReizigerAdres = null;
        try {
            pprint("\n---------- Test AdresDAO -------------");


            // findByReiziger
            pprint("----------\n[Test] [adresDAO.findByReiziger()]  geeft de volgende adressen:");
            testReiziger = reizigerDAOPsql.save(new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21)));
            pprint("222222222222");
            System.out.println(testReiziger.toString());
            testReizigerAdres = adresDAOPsql.save(new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", testReiziger.getId()));
            testReiziger.setAdres(testReizigerAdres);
        } catch (Exception e) { e.printStackTrace(); } try {


            // findAll
            pprint("----------\n[Test] [adresDAO.findAll()] geeft de volgende adressen:");
            adresDAOPsql.findAll().stream().forEach(adres -> pprint(adres.toString()));
            pprint("----------");
        } catch (Exception e) { e.printStackTrace(); } try {

            // findByID
            pprint("----------\n[Test] [adresDAO.findByID()] adres_id = " +testReizigerAdres.getAdres_ID());
            String s = adresDAOPsql.findByID(testReizigerAdres.getAdres_ID()).toString();
            pprint(s);
        } catch (Exception e) { e.printStackTrace(); } try {

            // findByReiziger
            pprint("----------\n[Test] [adresDAO.findByReiziger()] reiziger_id = " +testReiziger.getId());
            pprint(adresDAOPsql.findByReiziger(testReiziger).toString());
        } catch (Exception e) { e.printStackTrace(); } try {

            // update
            pprint("----------\n[Test] [update] adresDAO.update()");
            pprint(String.valueOf(adresDAOPsql.update(testReizigerAdres)));
        } catch (Exception e) { e.printStackTrace(); } try {

            // delete
            pprint("----------\n[Test] [delete] adresDAO.delete()");
            pprint(String.valueOf(adresDAOPsql.delete(testReizigerAdres)));
            //        adresDAO.delete(adresDAO.findByID(reizigerDAO.findByID(3).getAdres_id()));
        } catch (Exception e) { e.printStackTrace(); } try {

            pprint("----------\n[Test] [findAll] adresDAO.findAll()");
            pprint("[SUCCES] grootte lijst alle adressen: " + adresDAOPsql.findAll().size());

            Reiziger testReizigerLive = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21));
            Date date = Date.valueOf(LocalDate.of(2026, 9, 11));
        } catch (Exception e) { e.printStackTrace(); } try {

//            newOvChipKaart = new OVChipkaart(adresDAOPsql.findAll().size() + 2, date, 1, 10.00, 5);
//            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
//            newOvChipKaart.addProductAanKaart(newProduct);
//            testReizigerLive.

        } catch (Exception e) { e.printStackTrace(); }
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
        System.out.println("+ updated kaart incl productA: " + ovChipkaartDAOPsql.findByID(scenarioOVChipkaart.getKaart_nummer()));

        Product productB = productDAOPsql.save( // productB
                new Product("B", "B", 10));
        scenarioOVChipkaart.addProductAanKaart(productB);
        OVChipkaart scenarioOVChipkaart2 = ovChipkaartDAOPsql.update(scenarioOVChipkaart);
        System.out.println("+ updated kaart incl productA + productB: " + ovChipkaartDAOPsql.findByID(scenarioOVChipkaart2.getKaart_nummer()));

        // updateOv_Chipkaart
//        ovChipkaartDAOPsql.update(scenarioOVChipkaart)


            //      3. Je zoekt per product welke OV-chipkaarten daarbij horen.
        System.out.println("\n----------## zoek per product welke OV-chipkaarten daarbij horen");

        Product p = productDAOPsql.findByID(2);
        ArrayList<OVChipkaart> list = p.getOvChipkaartenMetProduct();
        for(OVChipkaart oc:list) System.out.println(oc.getKaart_nummer());

            //      4. Je zoekt per OV-chipkaart welke producten er op staanJe update een attribuut van het product (bijvoorbeeld de prijs).
            //      5. Daarna laat je zien dat de verandering zowel op database-niveau als op Java-klasse-niveau zichtbaar is bij een willekeurige OV-chipkaart die dat product heeft geregistreerd.
            //      6. Je hebt een pprint-statement voor een reiziger (reiziger1.toString() o.i.d.) dat de gekoppelde OV-chipkaarten en gekoppelde producten laat zien.
            //
            //          Je laat de tests zowel zien op de code die gebruik maakt van Hibernate als op de code die gebruik maakt van DAIO's

        //      2. Je verwijdert de OV-chipkaart, maar de producten blijven bestaan.
        pprint("----------\n[Test] [update] deleting scenarioOVChipkaart");
        ovChipkaartDAOPsql.delete(scenarioOVChipkaart2);
        pprint("scenarioOVChipkaart uit db bestaat niet meer: " + (ovChipkaartDAOPsql.findByID(scenarioOVChipkaart.getKaart_nummer()) == null));
        pprint("PRODUCT A uit db bestaat nog wel: " + (productDAOPsql.findByID(productA.getId()) != null));
        pprint("PRODUCT B uit db bestaat nog wel: " + (productDAOPsql.findByID(productB.getId()) != null));


        //TODO toon dat de producten nog bestaan
    }
    /**
     * pprint.
     * @param inputToPrint the input to pprint
     */
    public static void pprint(String inputToPrint) {
        System.out.println(inputToPrint);
    }
}

