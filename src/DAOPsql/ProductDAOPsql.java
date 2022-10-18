package DAOPsql;

import DAO.ProductDAO;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
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
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) " +
                    "VALUES (?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setString(4, String.valueOf(product.getPrijs()));

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
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
            String query = "INSERT INTO product WHERE product_nummer = (product_nummer) (product_nummer, naam, beschrijving, prijs) " +
                    "VALUES (?, ?, ?, ?)";

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setString(4, String.valueOf(product.getPrijs()));

            // TODO schrijf breid update uit zodat er relaties kunnen worden gepersisteerd

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
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

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(product.getProduct_nummer()));
            ps.executeQuery();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id waarnaar gezocht moet worden
     * @return informatie over het product, of null.
     */
    public  Product findByID(int id) {
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
