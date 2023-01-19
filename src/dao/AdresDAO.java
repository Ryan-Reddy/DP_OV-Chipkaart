package dao;

import domain.Adres;
import domain.Reiziger;

import java.util.List;

/**
 * The interface Adres dao.
 */
public interface AdresDAO {
    Adres save(Adres adres);
    Adres findByID(int adres_id);
    Adres update(Adres adres);
    boolean delete(Adres adres);
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll();
}
