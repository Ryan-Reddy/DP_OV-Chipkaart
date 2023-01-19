package dao;

import domain.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/* Dit is de DAO (Data Access Object), een structureel patroon dat ons toelaat om de
// applicatie/business layer te isoleren van de persistancy/db layer
// BRON: https://www.baeldung.com/java-dao-pattern
// /// https://jdbc.postgresql.org/documentation/query/
// Deze functies kunnen dus opgeroepen worden i.p.v. direct met sql bezig te zijn vanuit de Main.
*/

/**
 * The interface Reiziger dao.
 */
public interface ReizigerDAO  {
    Reiziger save(Reiziger reiziger);
    Reiziger update(Reiziger reiziger);
    void delete(Reiziger reiziger);
    Reiziger findByID(int reizigerID);

    List<Reiziger> findByGbDatum(LocalDate datum);

    List<Reiziger> findAll() throws SQLException;
    void setAdresDAO(AdresDAO adresDAO);
    void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAOPsql);
}
