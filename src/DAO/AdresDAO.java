package DAO;

import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {

    boolean save(Adres adres);

    boolean update(Adres adres);

    boolean delete(Adres adres);

    static Adres findAdresById(int id) {
        return AdresDAO.findAdresById(id);
    };



    static Adres findAdresByReiziger(Reiziger reiziger){
        return AdresDAO.findAdresByReiziger(reiziger);
    };

    List<Adres> findAll() throws SQLException;
}
