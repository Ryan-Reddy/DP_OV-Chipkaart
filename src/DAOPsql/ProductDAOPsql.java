package DAOPsql;

import DAO.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.productStatusEnum;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private static Connection localConn;
    private Statement myStatement;

    public ProductDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }


//    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
//        try {
//            String query_prod = "SELECT * FROM product WHERE  = ?";
//
//            List<Product> alleProducten = new ArrayList<Product>();
//            PreparedStatement ps = localConn.prepareStatement(query_prod);
//            ps.setInt(1, ovChipkaart.getKaart_nummer());
//
//            ResultSet myResultSet = ps.executeQuery();
//
//            while (myResultSet.next()) {
//                int product_nummer = myResultSet.getInt("product_nummer");
//                String naam = myResultSet.getString("naam");
//                String beschrijving = myResultSet.getString("beschrijving");
//                int prijs = myResultSet.getInt("prijs");
//                alleProducten.add(new Product(product_nummer, naam, beschrijving, prijs));
//            }
//
//
//            return alleProducten;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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
            if (alleKaartenMetProduct != null) {
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
                    return (ps.executeUpdate() == 1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * @param product het up te daten product
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Product product) {
        try {
            String query = "UPDATE product SET product_nummer = ?, naam= ?, beschrijving= ?, prijs= ? WHERE product_nummer = ?";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, product.getProduct_nummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setDouble(4, product.getPrijs());
            ps.setInt(5, product.getProduct_nummer());

            // hieronder volgt het deel relatie
            ArrayList<OVChipkaart> alleKaartenMetProduct = product.getAlleKaartenMetProduct();
            if (alleKaartenMetProduct != null) {

                for (OVChipkaart ovchipkaart : alleKaartenMetProduct) {
                    try {
                        String query_ovc_prod = "INSERT INTO ov_chipkaart_product (product_nummer, kaart_nummer, status, last_update) VALUES (?, ?) WHERE product_nummer = (product_nummer) ";
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
                return false;
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
            String query = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, product.getProduct_nummer());
            ps.executeUpdate();

            // hieronder volgt het deel relatie
            ArrayList<OVChipkaart> alleKaartenMetProduct = product.getAlleKaartenMetProduct();
            if (alleKaartenMetProduct != null) {
                for (OVChipkaart ovchipkaart : alleKaartenMetProduct) {
                    try {
                        String query_ovc_prod = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
                        PreparedStatement ps2 = localConn.prepareStatement(query_ovc_prod);

                        // Verander status naar gestopt
                        ps.setString(1, String.valueOf(productStatusEnum.PRODUCT_GESTOPT));
                        // Wijzig last_update naar vandaag
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * @param id id waarnaar gezocht moet worden
     * @return informatie over het product, of null.
     */
    public Product findByID(Product product) {
        String query = "SELECT * FROM product WHERE product_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, 4);
//            ps.setInt(1, product.getId());

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            int product_nummer = myResultSet.getInt("product_nummer");
            String naam = myResultSet.getString("naam");
            String beschrijving = myResultSet.getString("beschrijving");
            int prijs = myResultSet.getInt("prijs");

            return new Product(product_nummer, naam, beschrijving, prijs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> findAll() throws SQLException {
        String query = "select * from product";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        ResultSet myResultSet = preparedStatement.executeQuery();
        ArrayList<Product> alleProducten = new ArrayList<Product>();

        try {
            while (myResultSet.next()) {
                int product_nummer = myResultSet.getInt("product_nummer");
                String naam = myResultSet.getString("naam");
                String beschrijving = myResultSet.getString("beschrijving");
                int prijs = myResultSet.getInt("prijs");

                alleProducten.add(new Product(product_nummer, naam, beschrijving, prijs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleProducten;
    }
}
