package DAOPsql;

import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
import DAO.ReizigerDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.productStatusEnum;

import java.sql.*;
import java.util.ArrayList;

/**
 * The type Product dao psql.
 */
public class ProductDAOPsql implements ProductDAO {
    private static Connection localConn;
    private Statement myStatement;
    private ReizigerDAO reizigerDAO;
    private ProductDAO productDAO;
    private AdresDAO adresDAO;
    private OVChipkaartDAO ovChipkaartDAO;

    /**
     * Instantiates a new Product dao psql.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
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
     * @return boolean = het opslaan gelukt?
     */
    @Override
    public Product save(Product product) {
        try {
            String query_prod = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = localConn.prepareStatement(query_prod,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, product.getProduct_nummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setInt(4, product.getPrijs());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creeren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProduct_nummer(generatedKeys.getInt("product_nummer"));
                }
                else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            ps.close();
            // TODO connect met andere tabel via ov_chipkaart_productDAOSQL

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param product het up te daten product
     * @return het updaten gelukt?
     */
    @Override
    public Product update(Product product) {
        try {
            String query = "UPDATE product SET product_nummer = ?, naam= ?, beschrijving= ?, prijs= ? WHERE product_nummer = ?";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, product.getProduct_nummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setDouble(4, product.getPrijs());
            ps.setInt(5, product.getProduct_nummer());


            int response = ps.executeUpdate();

            if (response == 0) System.out.println("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();
            // TODO implementeer een save naar de ovchipkaart_product combi table

            return findByID(product.getId());
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
            PreparedStatement ps = localConn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            ps.setInt(1, product.getId());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Delete failed, geen rijen gewijzigd.");
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find by id product.
     *
     * @param product the product
     * @return informatie over het product, of null.
     */
    public Product findByID(int id) {
        String query = "SELECT * FROM product WHERE product_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, id);
//            ps.setInt(1, product.getId());

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            int product_nummer = myResultSet.getInt("product_nummer");
            String naam = myResultSet.getString("naam");
            String beschrijving = myResultSet.getString("beschrijving");
            int prijs = myResultSet.getInt("prijs");

            return new Product(naam, beschrijving, prijs, product_nummer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param ovChipkaartDAOPsql
     */
    @Override
    public void setOVChipkaartDAO(OVChipkaartDAO ovChipkaartDAOPsql) { this.ovChipkaartDAO = ovChipkaartDAOPsql; }

    /**
     * Find all array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
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

                alleProducten.add(new Product(naam, beschrijving, prijs, product_nummer));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleProducten;
    }
}
