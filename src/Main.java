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
    /**
     * The constant mijnConn.
     */
// 1. Connect met de database
    static Connection mijnConn;
    /**
     * The Reiziger dao psql.
     */
    static ReizigerDAOPsql reizigerDAOPsql;
    /**
     * The Product dao psql.
     */
    static ProductDAOPsql productDAOPsql;
    /**
     * The Ov chipkaart dao psql.
     */
    static OVChipkaartDAOPsql ovChipkaartDAOPsql;
    /**
     * The Adres dao psql.
     */
    static AdresDAO adresDAOPsql;

    /**
     * The constant sietske.
     */
// Maak een nieuwe reiziger aan en persisteer deze in de database
    static Reiziger sietske;
    /**
     * The Adres sietske.
     */
    static Adres adresSietske;
    /**
     * The New ov chip kaart.
     */
    static OVChipkaart newOvChipKaart;
    /**
     * The New product.
     */
    static Product newProduct;
    static {
        try {
            mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
            productDAOPsql = new ProductDAOPsql(mijnConn);
            ovChipkaartDAOPsql = new OVChipkaartDAOPsql(mijnConn);
            adresDAOPsql = new AdresDAOPsql(mijnConn);
            sietske = new Reiziger("S", "", "Boers", LocalDate.of(1981,03,14));
            adresSietske = new Adres("1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());
            sietske.setAdres_id(adresSietske.getAdres_ID());

            Date date = Date.valueOf(LocalDate.of(2026, 9, 11));
            newOvChipKaart = new OVChipkaart(10, date, 1,10.00,5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instantiates a new Main.
     *
     * @throws SQLException the sql exception
     */
    public Main() throws SQLException {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {

            // TESTS:

            // TODO reactivate:
            // testreizigerDAO:
//            testReizigerDAO(reizigerDAOPsql); // dependency injection van de connectie

            // TODO reactivate:
            // testOVChipkaartDAO:
            testAdresDAO(adresDAOPsql);

            // TODO reactivate:
//            // testOVChipkaartDAO:
//            testOVChipkaartDAO(ovChipkaartDAOPsql);

            // TODO reactivate:
            // testProductDAO:
//            testProductDAO(productDAOPsql);

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
            sout("[Test] [update] ReizigerDAO.update()");
            String sietskeOudeAchternaam = sietske.getAchternaam();
            sout("[Test] [update] oude achternaam = " + sietskeOudeAchternaam + "\n");
            sietske.setAchternaam("anders");
            rdao.update(sietske);
            sout(sietske.getAchternaam());

            // --- // delete:
            sout("[Test] [delete] ReizigerDAO.delete()");
            int preDeleteLijstSize = rdao.findAll().size();
            sout("[Test] [delete] voor delete grootte reizigerslijst = " + preDeleteLijstSize);

            boolean succes = rdao.delete(sietske);

            sout("[Test] [delete] verkleind? = " + succes + "lijst is nu: " + rdao.findAll().size());

            if(succes = true) { sout("[Test] [delete] [SUCCESS] test geslaagd!");}
            else { sout("[Test] [delete] [GEFAALD] test gefaald!");}

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testAdresDAO(AdresDAO adresDAO) {
        try {
            sout("\n---------- Test AdresDAO -------------");
            sout("[Test] 1 [adresDAO.getAdresByID()] adres_id = 1");
            sout(adresDAO.getAdresByID(1).toString());

            sout("[Test] 2 [adresDAO.getAdresByReiziger()]  geeft de volgende reizigers:");
            Reiziger testReiziger = new Reiziger("RLJ", "van", "Lil", LocalDate.of(1991,9,21));
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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testProductDAO(ProductDAOPsql productDAO) {
        sout("\n---------- Test testProductDAO -------------");

        try {
            sout("[Test] [save] productDAO.save()");
            newProduct = new Product( "gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
            sout(String.valueOf(productDAO.save(newProduct) == true));

            sout("[Test] productDAO.findAll() geeft de volgende reizigers:");
            sout(productDAO.findAll().toString());

            sout("[Test] productDAO.findByID() geeft de volgende reizigers:");
            sout(String.valueOf(productDAO.findByID(newProduct)));


            sout("[Test] [update] productDAO.update()");
            sout(String.valueOf(productDAO.update(newProduct)));

            sout("[Test] [delete] productDAO.delete()");
            sout(String.valueOf(productDAO.delete(newProduct)));

//            List<Product> productResultaten = productDAO.findByOVChipkaart(newOvChipKaart);
//            productResultaten.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testOVChipkaartDAO(OVChipkaartDAOPsql ovChipkaartDAO) {
        sout("\n---------- Test ovChipkaartDAO -------------");


        try {
            // TODO schrijf testOVCHIPKAART TEST



            sout("[Test] [save] ovChipkaartDAO.save() with kaart nummer: " + newOvChipKaart.getKaart_nummer());
            sout(String.valueOf(ovChipkaartDAO.save(newOvChipKaart)));

            sout("[Test] ovChipkaartDAO.findAll() geeft de volgende reizigers:");
            sout(ovChipkaartDAO.findAll().toString());

            sout("[Test] ovChipkaartDAO.findByOVChipkaartID() geeft de volgende reizigers:");
            OVChipkaart ovChipkaartResultaten = ovChipkaartDAO.findByOVChipkaartID(newOvChipKaart.getKaart_nummer());
            sout(ovChipkaartResultaten.toString());

            sout("[Test] [update] ovChipkaartDAO.update()");
            sout(String.valueOf(ovChipkaartDAO.update(newOvChipKaart)));

            sout("[Test] [delete] ovChipkaartDAO.delete()");
            sout(String.valueOf(ovChipkaartDAO.delete(newOvChipKaart)));
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

