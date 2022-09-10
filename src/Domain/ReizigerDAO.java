package Domain;

import java.util.List;
import java.util.Optional;

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

    Reiziger findById(int id);

    List<Reiziger> findByGbDatum(String datum);

    List<Reiziger> findAll();
}
