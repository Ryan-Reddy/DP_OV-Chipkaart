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

public interface ReizigerDAO {

    boolean save(Reiziger reiziger);

    boolean update(Reiziger reiziger);

    boolean delete(Reiziger reiziger);

    Reiziger findReizigerById(int id);

    List<Reiziger> findByGbDatum(String datum) throws SQLException;

    List<Reiziger> findAll() throws SQLException;
}
