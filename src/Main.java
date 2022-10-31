import DAO.AdresDAO;
import DAO.ReizigerDAO;
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
import java.util.List;

public class Main {
    // 1. Connect met de database
    static Connection mijnConn;
    static ReizigerDAOPsql reizigerDAOPsql;
    static ProductDAOPsql productDAOPsql;
    // Maak een nieuwe reiziger aan en persisteer deze in de database
    static Reiziger sietske;
    static OVChipkaart ovChipkaart;
    static Product newProduct;
    static {
        try {
            mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");
            reizigerDAOPsql = new ReizigerDAOPsql(mijnConn);
            productDAOPsql = new ProductDAOPsql(mijnConn);
            sietske = new Reiziger(reizigerDAOPsql.findAll().size(), "S", "", "Boers", Date.valueOf("1981-03-14"));
            newProduct = new Product(7, "gratis reizen", "sleutel tot de trein, altijd gratis reizen!", 100000);
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
        sout("\n---------- Test ReizigerDAO -------------");
        try {
            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            sout("[Test] [ReizigerDAO.findAll()] geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                sout(r.toString());
            }
            sout("[Test] [CRUD]");

            // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

            // --- // save:
            sout("[Test] [save] ReizigerDAO.save()");
            System.out.print("[Test] [save] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske);
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

            rdao.delete(sietske);

            int afterDeleteLijstSize = rdao.findAll().size();
            sout("[Test] [delete] na delete grootte reizigerslijst = " + afterDeleteLijstSize);

            if (afterDeleteLijstSize < afterDeleteLijstSize) sout("[Test] [delete] [SUCCESS] test geslaagd!");
            else sout("[Test] [delete] [GEFAALD] test gefaald!");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testAdresDAO(AdresDAO adresDAO
//            , ReizigerDAO reizigerDAO

    ) {
        try {
            sout("\n---------- Test AdresDAO -------------");
            // write crud tests
//        sout(reizigerDAO.findReizigerById(3).getAdres_id());

            Adres adres = new Adres(1200, "1221JJ", "88", "Bontekoestraat", "Amsterdam", sietske.getId());

            sout("[Test] [adresDAO.getAdresByID()] adres_id = 1");
            sout(adresDAO.getAdresByID(1).toString());

            sout("[Test] [ReizigerDAO.findAll()]  geeft de volgende reizigers:");
            sout(adresDAO.getAdresByReiziger(sietske).toString());

            sout("[Test] [ReizigerDAO.findAll()] geeft de volgende reizigers:");
            sout(adresDAO.findAll().toString());

//        adresDAO.delete(adresDAO.getAdresByID(reizigerDAO.findReizigerById(3).getAdres_id()));
            // TODO makle ^this work
            sout("[Test] [save] ReizigerDAO.save()");
            adresDAO.save(adres);
            sout("[Test] [update] ReizigerDAO.update()");
            adresDAO.update(adres);
            sout("[Test] [delete] ReizigerDAO.delete()");
            adresDAO.delete(adres);



            // TODO
            //  test find allAdressen()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testProductDAO(ProductDAOPsql productDAO) {
        sout("\n---------- Test testProductDAO -------------");

        try {
            List<Product> productResultaten = productDAO.findByOVChipkaart(ovChipkaart);
            productResultaten.forEach(System.out::println);


            sout("[Test] [save] productDAO.save()");
            productDAO.save(newProduct);

            sout("[Test] productDAO.findAll() geeft de volgende reizigers:");
            sout(productDAO.findAll().toString());

            sout("[Test] productDAO.findByID() geeft de volgende reizigers:");
            productDAO.findByID(newProduct);


            sout("[Test] [update] productDAO.update()");
            productDAO.update(newProduct);

            sout("[Test] [delete] productDAO.delete()");
            productDAO.delete(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testOVChipkaartDAO(OVChipkaartDAOPsql ovChipkaartDAO) {
        sout("\n---------- Test ovChipkaartDAO -------------");

        try {
            // TODO schrijf testOVCHIPKAART TEST

            OVChipkaart ovChipkaart = new OVChipkaart(10, (Date) new java.util.Date(), 1,10.00,5);


            sout("[Test] [save] ovChipkaartDAO.save()");
            ovChipkaartDAO.save(ovChipkaart);

            sout("[Test] ovChipkaartDAO.findAll() geeft de volgende reizigers:");
            sout(ovChipkaartDAO.findAll().toString());

            sout("[Test] ovChipkaartDAO.findByOVChipkaartID() geeft de volgende reizigers:");
            OVChipkaart ovChipkaartResultaten = ovChipkaartDAO.findByOVChipkaartID(ovChipkaart.getKaart_nummer());
            sout(ovChipkaartResultaten.toString());

            sout("[Test] [update] ovChipkaartDAO.update()");
            ovChipkaartDAO.update(ovChipkaart);

            sout("[Test] [delete] ovChipkaartDAO.delete()");
            ovChipkaartDAO.delete(ovChipkaart);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void sout(String inputToPrint) {
        System.out.println(inputToPrint);
    }
}

