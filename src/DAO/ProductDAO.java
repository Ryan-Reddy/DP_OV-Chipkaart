package DAO;

import domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Product dao.
 */
public interface ProductDAO {
    Product save(Product product);
    Product update(Product product);
    boolean delete(Product product);
    List<Product> findAll() throws SQLException;
    Product findByID(int id);
    void setOVChipkaartDAO(OVChipkaartDAO ovChipkaartDAOPsql);
}
