package DAO;

import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

/* Dit is de DAO (Data Access Object), een structureel patroon dat ons toelaat om de
// applicatie/business layer te isoleren van de persistancy/db layer
// BRON: https://www.baeldung.com/java-dao-pattern
// /// https://jdbc.postgresql.org/documentation/query/
// Deze functies kunnen dus opgeroepen worden ipv direct met sql bezig te zijn vanuit de Main.
*/

/**
 * The interface Reiziger dao.
 */
public interface ReizigerDAO  {

    Reiziger save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findByID(int reizigerID) throws SQLException;
    List<Reiziger> findByGbDatum(String datum) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
    int getAdresId(int reizigerId) throws SQLException;
    void setAdresDAO(AdresDAO adresDAO);
    void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAOPsql);
    void setProductDAOPsql(ProductDAO productDAOPsql);
}
