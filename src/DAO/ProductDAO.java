package DAO;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
//    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    boolean save(Product product);

    boolean update(Product product);

    boolean delete(Product product);
}
