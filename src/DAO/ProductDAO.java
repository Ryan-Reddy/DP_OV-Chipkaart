package DAO;

import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface Product dao.
 */
public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findAll() throws SQLException;
    Product findByID(int id);
}
