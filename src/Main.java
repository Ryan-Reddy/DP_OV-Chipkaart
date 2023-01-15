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
        sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        sout("\n---------- Test ReizigerDAO -------------");
        try {
            this.reizigerDAOPsql.setAdresDAO(adresDAOPsql);
            reizigerDAOPsql.setOvChipkaartDAO(ovChipkaartDAOPsql);

            List<Reiziger> reizigers = reizigerDAOPsql.findAll();
            sout("----------\n[Test] [ReizigerDAO.findAll()] geeft aantal reizigers:" + reizigers.size() + "\n");

            sout("----------\n[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            Reiziger newSietske = reizigerDAOPsql.save(sietske);

            List<Reiziger> reizigersAfterSave = reizigerDAOPsql.findAll();
            sout(reizigersAfterSave.size() + " reizigers");
            if (reizigersAfterSave.size() > reizigers.size()) sout("----------\n[Test] [save] [SUCCESS]\n");
            else sout("[Test] [save] [FAILURE]\n");

            sout("----------\n[Test] [update] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            sout("[Test] [update] oude achternaam = " + sietskeOudeAchternaam);
            sietske.setAchternaam("anders");
            Reiziger updatedSietske = reizigerDAOPsql.update(newSietske);
            sout("[Test] [update] [RESULT] nieuwe achternaam = " + newSietske.getAchternaam());
            sout("[Test] [update] [RESULT] nieuwe sietske.toString() = " + newSietske);

            sout("\n----------\n[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = reizigerDAOPsql.findAll().size();
            boolean succes = reizigerDAOPsql.delete(newSietske);
            sout("[Test] [delete] [RESULT] = " + succes + " lijst is nu: " + reizigerDAOPsql.findAll().size() + " en was: " + preDeleteLijstSize);
            if (succes = true) {
                sout("[Test] [delete] [SUCCESS] test geslaagd!");
            } else {
                sout("[Test] [delete] [GEFAALD] test gefaald!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testOVChipkaartDAO() throws SQLException {
        sout("\n---------- Test ovChipkaartDAO -------------");
        LocalDate geldig_tot = LocalDate.of(2026, 9, 11);

        int kaartNum = adresDAOPsql.findAll().size();
        OVChipkaart newOvChipKaart = new OVChipkaart(geldig_tot, 1, 10.00, sietske);

        try {
            // TODO schrijf testOVCHIPKAART TEST

            sout("----------\n[Test] [save] ovChipkaartDAO.save() with kaart nummer: " + newOvChipKaart.getKaart_nummer() + " [RESULT] = " + ovChipkaartDAOPsql.save(newOvChipKaart));

            sout("\n----------\n[Test] [findall] ovChipkaartDAO.findAll() geeft de volgende reizigers:" + " [RESULT] = \n" + ovChipkaartDAOPsql.findAll().toString());
            sout("\n----------\n[Test] [findbyid] ovChipkaartDAO.findbyid() geeft de volgende reiziger:" + " [RESULT] = \n" + ovChipkaartDAOPsql.findByID(19).toString());

            sout("\n----------\n[Test] [findByID] ovChipkaartDAO.findByID() geeft de volgende OV-chipkaart: [RESULT] = " + ovChipkaartDAOPsql.findByID(newOvChipKaart.getKaart_nummer()));

            sout("\n----------\n[Test] [update] ovChipkaartDAO.update()" + " [RESULT] = " + ovChipkaartDAOPsql.update(newOvChipKaart));

            sout("\n----------\n[Test] [delete] ovChipkaartDAO.delete()" + " [RESULT] = " + ovChipkaartDAOPsql.delete(newOvChipKaart));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testAdresDAO() {
        try {
            sout("\n---------- Test AdresDAO -------------");

            // getAdresByID
            sout("----------\n[Test] 1 [adresDAO.getAdresByID()] adres_id = 1");
            sout(adresDAOPsql.getAdresByID(1).toString());

            // findByReiziger
            sout("----------\n[Test] 2 [adresDAO.findByReiziger()]  geeft de volgende adressen:");
            Reiziger testReiziger = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991, 9, 21), reizigerDAOPsql.findAll().size());
            System.out.println(testReiziger);
            testReiziger = reizigerDAOPsql.save(testReiziger);
            sout("222222222222");
            System.out.println(testReiziger.toString());
            Adres testReizigerAdres = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", testReiziger.getId());
            testReiziger.setAdres(testReizigerAdres);


            // findAll
            sout("----------\n----------\n[Test] 3 [adresDAO.findAll()] geeft de volgende adressen:");
            adresDAOPsql.findAll().stream().forEach(adres -> sout(adres.toString()));
            sout("----------");

            // save
            sout("----------\n[Test] 4 [save] adresDAO.save()");
            adresDAOPsql.save(testReizigerAdres);

            // findByReiziger
            sout(adresDAOPsql.findByReiziger(testReiziger).toString());
            reizigerDAOPsql.delete(testReiziger);


            // update
            sout("----------\n[Test] 5 [update] adresDAO.update()");
            sout(String.valueOf(adresDAOPsql.update(testReizigerAdres)));

            // delete
            sout("----------\n[Test] 6 [delete] adresDAO.delete()");
            sout(String.valueOf(adresDAOPsql.delete(testReizigerAdres)));
            //        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findByID(3).getAdres_id()));

            sout("----------\n[Test] 7 [findAll] adresDAO.findAll()");
            sout("----------\n[Test] 7 [findAll] [SUCCES] grootte lijst alle adressen: " + adresDAOPsql.findAll().size());


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
    private void testProductDAO() {
        sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        sout("\n---------- Test testProductDAO -------------");
        Product newProduct;

        try {
            newProduct = new Product("gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
            sout("----------\n[Test] [save] productDAO.save() [RESULT] = " + (this.productDAOPsql.save(newProduct) == true));

            sout("----------\n[Test] productDAO.findAll() geeft de volgende producten:" + this.productDAOPsql.findAll().toString());

            sout("----------\n[Test] productDAO.findByID() geeft de volgende producten:" + this.productDAOPsql.findByID(1));

            sout("----------\n[Test] [update] productDAO.update() [RESULT] = " + this.productDAOPsql.update(newProduct));

            sout("----------\n[Test] [delete] productDAO.delete() [RESULT] = " + this.productDAOPsql.delete(newProduct));

            sout("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void testScenario() throws SQLException {
        sout("---------- Test testScenario -------------");

        //      Je maakt een reiziger,
        sout("\n----------## nieuwe scenarioReiziger, incl newReizigerID");
        Reiziger scenarioReiziger = new Reiziger("Scenario", "to", "Win", LocalDate.of(1966, 9, 21));
        sout("+ saving scenarioReiziger: " + scenarioReiziger);
        Reiziger opgeslagenScenarioReiziger = reizigerDAOPsql.save(scenarioReiziger);
        sout(opgeslagenScenarioReiziger.toString());

        //koppelt daaraan een nieuwe OV-Chipkaart,
        sout("\n----------## nieuwe scenarioOVChipkaart, gelijk gekoppeld aan de newReizigerID");
        LocalDate today = java.time.LocalDate.now();
        OVChipkaart scenarioOVChipkaart = new OVChipkaart(today, 1,20.32, opgeslagenScenarioReiziger);

        sout("\n+ saving scenarioOVChipkaart");
        scenarioOVChipkaart = ovChipkaartDAOPsql.save(scenarioOVChipkaart);
        sout(scenarioOVChipkaart.toString());

        // en koppelt daaraan twee producten.
        System.out.println("\n----------## Koppel daar aan twee producten");
        ArrayList<Product> alleProducten = (ArrayList<Product>) this.productDAOPsql.findAll();
        Product productA = alleProducten.get(0);
        Product productB = alleProducten.get(1);

        System.out.println("voegt product ID#:" + productA.getId() + " toe aan kaart");
        scenarioOVChipkaart.addProductAanKaart(productA);
        sout(scenarioOVChipkaart.toString());
        scenarioOVChipkaart = ovChipkaartDAOPsql.update(scenarioOVChipkaart);

        System.out.println("voegt product ID#:"+ productA.getId() + " toe aan kaart");
        scenarioOVChipkaart.addProductAanKaart(productB);
        sout("scenarioOVChipkaart with 2 products" + scenarioOVChipkaart.toString());

        // update kaart in db met de nieuwe producten gekoppeld!
        scenarioOVChipkaart = ovChipkaartDAOPsql.update(scenarioOVChipkaart);

        sout("----------\n##  updating scenarioOVChipkaart");
        OVChipkaart updatedOvChipkaart = ovChipkaartDAOPsql.update(scenarioOVChipkaart);
        sout("updatedOvChipkaart" + updatedOvChipkaart);



        //      2. Je verwijdert de OV-chipkaart, maar de producten blijven bestaan.
        sout("----------\n[Test] [update] deleting scenarioOVChipkaart");
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

