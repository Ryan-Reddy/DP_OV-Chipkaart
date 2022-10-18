package DAO;

import domain.Product;

public interface ProductDAO {
    boolean save(Product product);

    boolean update(Product product);

    boolean delete(Product product);
}
