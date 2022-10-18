package DAOPsql;

import DAO.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.productStatusEnum;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductDAOPsql implements ProductDAO {
    private static Connection localConn;
    private Statement myStatement;

    public ProductDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }

    /**
     * @param product de product aanmaken, wijzigingen opslaan
     * @return het opslaan gelukt?
     */
    @Override
    public boolean save(Product product) {
        try {
            String query_prod = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) " + "VALUES (?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query_prod);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setString(4, String.valueOf(product.getPrijs()));

            // hieronder volgt het deel relatie
            ArrayList<OVChipkaart> alleKaartenMetProduct = product.getAlleKaartenMetProduct();
            for (OVChipkaart ovchipkaart : alleKaartenMetProduct) {
                try {
                    String query_ovc_prod = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) " + "VALUES (?, ?, ?, ?)";
                    PreparedStatement ps2 = localConn.prepareStatement(query_ovc_prod);

                    ps.setString(1, String.valueOf(ovchipkaart.getKaart_nummer()));
                    ps.setString(2, String.valueOf(product.getProduct_nummer()));
                    // TODO schrijf ergens status als attribuut
                    // TODO schrijf ergens last edit als attribuut
                    //  hiervoor is ook nog een methode nodig

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return (ps.executeUpdate()) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param product het up te daten product
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Product product) {
        try {
            String query = "INSERT INTO product WHERE product_nummer = (product_nummer) (product_nummer, naam, beschrijving, prijs) " + "VALUES (?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setString(4, String.valueOf(product.getPrijs()));

            // hieronder volgt het deel relatie
            ArrayList<OVChipkaart> alleKaartenMetProduct = product.getAlleKaartenMetProduct();
            for (OVChipkaart ovchipkaart : alleKaartenMetProduct) {
                try {
                    String query_ovc_prod = "INSERT INTO ov_chipkaart_product WHERE product_nummer = (product_nummer) (product_nummer, kaart_nummer, status, last_update) " + "VALUES (?, ?)";
                    PreparedStatement ps2 = localConn.prepareStatement(query_ovc_prod);

                    ps.setString(1, String.valueOf(product.getProduct_nummer()));
                    ps.setString(2, String.valueOf(ovchipkaart.getKaart_nummer()));
                    // Verander status naar gestopt
                    ps.setString(3, String.valueOf(productStatusEnum.GEUPDATE));
                    // Wijzig last_update naar vandaag
                    ps.setString(4, LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }





            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param product het te verwijderen product
     * @return boolean of het gelukt is
     */
    @Override
    public boolean delete(Product product) {
        try {
            String query = "DELETE FROM product WHERE product_nummer = (product_nummer) VALUES (?)";
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.executeQuery();

            // hieronder volgt het deel relatie
            ArrayList<OVChipkaart> alleKaartenMetProduct = product.getAlleKaartenMetProduct();
            for (OVChipkaart ovchipkaart : alleKaartenMetProduct) {
                try {
                    String query_ovc_prod = "INSERT INTO ov_chipkaart_product WHERE product_nummer = (product_nummer) (status, last_update) " + "VALUES (?, ?)";
                    PreparedStatement ps2 = localConn.prepareStatement(query_ovc_prod);

                    // Verander status naar gestopt
                    ps.setString(1, String.valueOf(productStatusEnum.PRODUCT_GESTOPT));
                    // Wijzig last_update naar vandaag
                    ps.setString(2, LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id waarnaar gezocht moet worden
     * @return informatie over het product, of null.
     */
    public Product findByID(int id) {
        String query = "SELECT * FROM product WHERE product_nummer = (product_nummer) VALUES (?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(id));
            ResultSet myResultSet = ps.executeQuery();
            return (Product) myResultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
