package DAO;

import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

/* Dit is de DAO (Data Access Object), een structureel patroon dat ons toelaat om de
// applicatie/business layer te isoleren van de persistancy/db layer
// BRON: https://www.baeldung.com/java-dao-pattern
//
// Deze functies kunnen dus opgeroepen worden ipv direct met sql bezig te zijn vanuit de Main.
*/

/**
 * The interface Reiziger dao.
 */
public interface ReizigerDAO {

    /**
     * Save boolean.
     *
     * @param reiziger the reiziger
     * @return the boolean
     */
    boolean save(Reiziger reiziger);

    /**
     * Update boolean.
     *
     * @param reiziger the reiziger
     * @return the boolean
     */
    boolean update(Reiziger reiziger);

    /**
     * Delete boolean.
     *
     * @param reiziger the reiziger
     * @return the boolean
     */
    boolean delete(Reiziger reiziger);

    /**
     * Find reiziger by id reiziger.
     *
     * @param reiziger the reiziger
     * @return the reiziger
     */
    Reiziger findReizigerById(Reiziger reiziger);

    /**
     * Find by gb datum list.
     *
     * @param datum the datum
     * @return the list
     * @throws SQLException the sql exception
     */
    List<Reiziger> findByGbDatum(String datum) throws SQLException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws SQLException the sql exception
     */
    List<Reiziger> findAll() throws SQLException;

    /**
     * Gets adres id.
     *
     * @param reizigerId the reiziger id
     * @return the adres id
     * @throws SQLException the sql exception
     */
    int getAdresId(int reizigerId) throws SQLException;
}
