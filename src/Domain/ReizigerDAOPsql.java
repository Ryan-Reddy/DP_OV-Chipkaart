package Domain;

import java.util.List;
import java.sql.*;


public class ReizigerDAOPsql implements ReizigerDAO {

    // TODO initializeer de ReizigerDAOPsql connectie, ongeveer zo:
//    AdresDAOPsql adao = new AdresDAOPsql(mijnConn);

    /**
     * @param reiziger
     * de reiziger aanmaken, wijzigingen opslaan
     * @return het opslaan gelukt?
     */

    @Override
    public boolean save(Reiziger reiziger) {
        return false;
    }

    /**
     * @param reiziger
     * de up te daten reiziger
     * @return het updaten gelukt?
     */
    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    /**
     * @param reiziger
     * de te verwijderen reiziger
     * @return boolean of het gelukt is
     */
    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }

    /**
     * @param id
     * id waarnaar gezocht moet worden
     * @return informatie over de reiziger, of null.
     */
    @Override
    public Reiziger findById(int id) {
        return null;
    }

    /**
     * @param datum
     * De reizigers van deze specifieke geboortedatum vinden
     * @return lijst van Reizigers wiens geboortedatum overeenkomt met de invoer.
     */
    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        return null;
    }

    /**
     * @return lijst met alle Reizigers in de db
     */
    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
