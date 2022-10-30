package DAO;

import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {

    boolean save(Adres adres);

    boolean update(Adres adres);

    boolean delete(Adres adres);

    Adres findAdresById(int id);

    Adres findAdresByReiziger(Reiziger reiziger);

    List<Adres> findAll() throws SQLException;
}
