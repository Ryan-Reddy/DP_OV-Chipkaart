package daoPsql;

import dao.AdresDAO;
import dao.OVChipkaartDAO;
import dao.ProductDAO;
import dao.ReizigerDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import domain.productStatusEnum;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Ov chipkaart dao psql.
 */
public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    final Connection localConn;
    ReizigerDAO reizigerDAO;
    ProductDAO productDAO;
    AdresDAO adresDAO;


    /**
     * Instantiates a new Ov chipkaart dao psql.
     *
     * @param conn the connection
     */
    public OVChipkaartDAOPsql(Connection conn) {
        localConn = conn;
    }

    public OVChipkaart save(OVChipkaart ovChipkaart) {
        String query = "INSERT INTO ov_chipkaart (geldig_tot, klasse, saldo, reiziger_id) " + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, ovChipkaart.getGeldigTot());
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setDouble(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getReiziger().getId());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creeren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ovChipkaart.setKaartNummer(generatedKeys.getInt("kaart_nummer"));
                } else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // save koppeling kaart-product
        ArrayList<Product> productenList = ovChipkaart.getProductOpDezeKaart();
            System.out.println("saving link card-products: ");

            for (Product product: productenList) { // product list iterator
                String query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status) VALUES (?, ?, ?)";
                try {
                    PreparedStatement ps2 = localConn.prepareStatement(query2);
                    ps2.setInt(1, ovChipkaart.getKaartNummer());
                    ps2.setInt(2, product.getProduct_nummer());
                    ps2.setString(3, "actief"); // TODO make a map of the list with products (product and status)

                    int gewijzigdeRijen = ps2.executeUpdate();
                    if (gewijzigdeRijen == 0) throw new SQLException("Save ovchip-product koppel gefaald, niks veranderd in DB.");
                    ps2.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        return ovChipkaart;
    }
    @Override
    public OVChipkaart update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.setDate(2, ovChipkaart.getGeldigTot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            ps.setInt(6, ovChipkaart.getKaartNummer());
            ps.executeUpdate();

            int response = ps.executeUpdate();
            if (response == 0) throw new SQLException("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();

            // purge alle gekoppelde items - kunnen in domein verwijderd zijn weet de db niet.
                System.out.println("deleting link card-products: ");

                        String query2 = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";

                        try {
                            PreparedStatement ps2 = localConn.prepareStatement(query2);
                            ps2.setInt(1, ovChipkaart.getKaartNummer());

                            int gewijzigdeRijen = ps2.executeUpdate();
                            if (gewijzigdeRijen == 0) {
                                System.out.println("Delete ovchip-product koppel gefaald, niks veranderd in DB. \tnovChip #= "
                                        + ovChipkaart.getKaartNummer());
                            }
                            ps2.close();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


            // update koppeling kaart-product
            ArrayList<Product> productenList = ovChipkaart.getProductOpDezeKaart();
            if (!productenList.isEmpty()) {

                productenList.forEach(product -> { // product list iterator
                    System.out.println("updating link card-products: " + product);

                    String query3 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status) VALUES (?, ?, ?)"
                                ;
                    try {
                        PreparedStatement ps3 = localConn.prepareStatement(query3);
                        ps3.setInt(1, ovChipkaart.getKaartNummer());
                        ps3.setInt(2, product.getProduct_nummer());
                        ps3.setString(3, "actief");

                        int gewijzigdeRijen = ps3.executeUpdate();
                        if (gewijzigdeRijen == 0) System.out.println("Update ovchip-product koppel gefaald, niks veranderd in DB. \tovChip #= "
                                + ovChipkaart.getKaartNummer() + " prod# = " + product.getProduct_nummer());
                        ps3.close();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return ovChipkaart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateOv_Chipkaart_ProductStatus(OVChipkaart ovChipkaart, Product product, productStatusEnum status) {
        // update status kaart-product
            System.out.println("updating link card-products: ");
             String query2 = "UPDATE ov_chipkaart_product SET " +
                        "status = ? " +
                        "WHERE kaart_nummer = ? AND product_nummer= ?;";
                try {
                    PreparedStatement ps2 = localConn.prepareStatement(query2);
                    ps2.setString(1, product.toString().toLowerCase());
                    ps2.setInt(2, ovChipkaart.getKaartNummer());
                    ps2.setInt(3, product.getProduct_nummer());

                    int gewijzigdeRijen = ps2.executeUpdate();
                    if (gewijzigdeRijen == 0) throw new SQLException("Update ovchip-product koppel gefaald, niks veranderd in DB. \novChip #= "
                            + ovChipkaart.getKaartNummer() + " prod# = " + product.getProduct_nummer());
                    ps2.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            // delete koppeling kaart-product
            ArrayList<Product> productenList = ovChipkaart.getProductOpDezeKaart();
            if (!productenList.isEmpty()) {
                System.out.println("deleting link card-products: ");

                productenList.forEach(product -> { // product list iterator
                    String query2 = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?";

                    try {
                        PreparedStatement ps2 = localConn.prepareStatement(query2);
                        ps2.setInt(1, ovChipkaart.getKaartNummer());
                        ps2.setInt(2, product.getProduct_nummer());

                        int gewijzigdeRijen = ps2.executeUpdate();
                        if (gewijzigdeRijen == 0) System.out.println("Delete ovchip-product koppel gefaald, niks veranderd in DB. \tovChip #= "
                                + ovChipkaart.getKaartNummer() + " prod# = " + product.getProduct_nummer());
                        ps2.close();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

            PreparedStatement ps = localConn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaart.getKaartNummer());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Delete failed, geen rijen gewijzigd.");
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * uses the input int ID to find OVChipkaart, will return just one.
     *
     * @param ovChipkaartID int ID van de ovChipkaart te zoeken in db
     * @return OVChipkaart object
     */
    public OVChipkaart findByID(int ovChipkaartID) {
        // TODO schrijf functie
        //          nu wel is dat de Ovchipkaart nu wel een lijst producten heeft met elk ook een lijst gekoppelde ovchipkaarten
        //          1. de ovChipkaart zelf ophalen,
        //          2. de producten opzoeken die erbij horen
        //          3. alle ovchipkaarten in lijsten zetten die bij de producten horen
        //          4. alle producten die bij die ovchipkaarten horen in een lijst zetten
        //          5. alles combineren?

        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaartID);
            ResultSet rs1 = ps.executeQuery();

            if (!rs1.next()) {
                return null;
            }
            OVChipkaart ovChipkaart = extractOvChipkaartRs(rs1);

            // haal alle producten op die bij kaart horen, alle ov-chipkaarten die bij de producten horen en go
            PreparedStatement ps2 = localConn.prepareStatement("SELECT prod.product_nummer, prod.naam, prod.beschrijving, prod.prijs FROM product prod " +
                    "INNER JOIN ov_chipkaart_product ovcp ON prod.product_nummer = ovcp.product_nummer " +
                    "INNER JOIN ov_chipkaart ovC ON ovc.kaart_nummer = ovcp.kaart_nummer " +
                    "WHERE ovcp.kaart_nummer = ?;");
            ps2.setInt(1, ovChipkaartID);

            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()){
                Product opgehaaldProduct = new Product(
                        rs2.getString("naam"),
                        rs2.getString("beschrijving"),
                        rs2.getInt("prijs"),
                        rs2.getInt("product_nummer")
                                );

                // haal alle ovchipkaarten op per product:
                PreparedStatement ps3 = localConn.prepareStatement("SELECT ovc.kaart_nummer, ovc.geldig_tot, ovc.klasse, ovc.saldo, ovc.reiziger_id " +
                        "FROM product AS prod " +
                                "INNER JOIN ov_chipkaart_product AS ovcp ON prod.product_nummer = ovcp.product_nummer " +
                                "INNER JOIN ov_chipkaart AS ovC ON ovc.kaart_nummer = ovcp.kaart_nummer " +
                                "WHERE ovcp.kaart_nummer = ?;");
                ps3.setInt(1, ovChipkaartID);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()){
                    OVChipkaart opgehaaldOVChip = extractOvChipkaartRs(rs3);
                    opgehaaldProduct.addOvChipKaart(opgehaaldOVChip);
                }
                ps3.close();
                ovChipkaart.addProductAanKaart(opgehaaldProduct);
            }
            ps2.close();
            return ovChipkaart;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private OVChipkaart extractOvChipkaartRs(ResultSet rs1) throws SQLException {
        return new OVChipkaart(
                rs1.getDate("geldig_tot").toLocalDate(),
                rs1.getInt("klasse"),
                rs1.getDouble("saldo"),
                reizigerDAO.findByID(rs1.getInt("reiziger_id")),
                rs1.getInt("kaart_nummer")
        );
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) throws SQLException {
        PreparedStatement ps = localConn.prepareStatement(
                "SELECT * FROM ov_chipkaart ovc RIGHT JOIN ov_chipkaart_product ocp on ovc.kaart_nummer = ocp.kaart_nummer WHERE ocp.product_nummer = ?");
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        while (rs.next()) {
            LocalDate geldigTot = rs.getDate("geldig_tot").toLocalDate();
            int klasse = rs.getInt("klasse");
            double saldo = rs.getDouble("saldo");
            Reiziger reiziger = reizigerDAO.findByID(rs.getInt("reiziger_id"));
            int kaartNummer = rs.getInt("kaart_nummer");
            OVChipkaart ovChipkaart = new OVChipkaart(geldigTot, klasse, saldo, reiziger, kaartNummer);
            ovChipkaart.addProductAanKaart(product);
            ovChipkaarten.add(ovChipkaart);
        }
        ps.close();
        return ovChipkaarten;
    }

    @Override
        public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        Map<Integer, Product> producten =
                productDAO.findAlleProductenZonderOvChipkaart().stream().collect(
                Collectors.toConcurrentMap(Product::getProduct_nummer, Function.identity())
        );

        // find ov-chipkaarten van reiziger
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        List<OVChipkaart> alleOVChipkaarten = new ArrayList<>();

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ResultSet myResultSet = ps.executeQuery();

            while (myResultSet.next()) {
                OVChipkaart ovChipkaart =
                        new OVChipkaart(myResultSet.getDate("geldig_tot").toLocalDate(),
                                myResultSet.getInt("klasse"), myResultSet.getDouble("saldo"),
                                reiziger,
                                myResultSet.getInt("kaart_nummer"));

                // find producten bij ovchipkaart
                PreparedStatement ps2 = localConn.prepareStatement("SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = ?");
                ps2.setInt(1, ovChipkaart.getKaartNummer());

            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                Product product = producten.get(rs2.getInt("product_nummer"));
                ovChipkaart.addProductAanKaart(product);
                product.addOvChipKaart(ovChipkaart);
            }
                alleOVChipkaarten.add(ovChipkaart);
            }
            return alleOVChipkaarten;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find all OVChipkaarten.
     *
     * @return the OVChipkaarten as ArrayList
     * @throws SQLException wawa
     */
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        // TODO:
        //      1. ov.findAll
        //      2. product.findAll
        //      3. loop door ov:
        //           ovchipkaart.addProduct
        //      4. loop door producten:
        //           product.addKaart
        //      5. return de gewilde ov

        ArrayList<OVChipkaart> alleOVChipkaarten = new ArrayList<>();

        String query = "select * from ov_chipkaart";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        ResultSet myResultSet = preparedStatement.executeQuery();

        try {
            while (myResultSet.next()) {
                alleOVChipkaarten.add(new OVChipkaart(myResultSet.getDate("geldig_tot").toLocalDate(), myResultSet.getInt("klasse"), myResultSet.getDouble("saldo"), reizigerDAO.findByID(myResultSet.getInt("reiziger_id")), myResultSet.getInt("kaart_nummer")));
            }
            return alleOVChipkaarten;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setReizigerDAO(ReizigerDAO reizigerDAO) {
        this.reizigerDAO = reizigerDAO;
    }

    @Override
    public void setAdresDAO(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }

    @Override
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
