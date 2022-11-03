package DAO;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Product dao.
 */
public interface ProductDAO {
    /**
     * Save boolean.
     *
     * @param product the product
     * @return the boolean
     */
//    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    boolean save(Product product);

    /**
     * Update boolean.
     *
     * @param product the product
     * @return the boolean
     */
    boolean update(Product product);

    /**
     * Delete boolean.
     *
     * @param product the product
     * @return the boolean
     */
    boolean delete(Product product);
}
