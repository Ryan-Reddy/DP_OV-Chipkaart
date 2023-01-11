package DAO;

import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Adres dao.
 */
public interface AdresDAO {
    boolean save(Adres adres);
    Adres getAdresByID(int adres_id);
    boolean update(Adres adres);
    boolean delete(Adres adres);
    Adres getAdresByReiziger(Reiziger reiziger);
    List<Adres> findAll() throws SQLException;
}
