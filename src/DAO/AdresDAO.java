package DAO;

import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Adres dao.
 */
public interface AdresDAO {


    /**
     * Save boolean.
     *
     * @param adres the adres
     * @return the boolean
     */
    boolean save(Adres adres);

    /**
     * Gets adres by id.
     *
     * @param adres_id the adres id
     * @return the adres by id
     */
    Adres getAdresByID(int adres_id);

    /**
     * Update boolean.
     *
     * @param adres the adres
     * @return the boolean
     */
    boolean update(Adres adres);

    /**
     * Delete boolean.
     *
     * @param adres the adres
     * @return the boolean
     */
    boolean delete(Adres adres);

    /**
     * Gets adres by reiziger.
     *
     * @param reiziger the reiziger
     * @return the adres by reiziger
     */
    Adres getAdresByReiziger(Reiziger reiziger);

    /**
     * Find all list.
     *
     * @return the list
     * @throws SQLException the sql exception
     */
    List<Adres> findAll() throws SQLException;
}
