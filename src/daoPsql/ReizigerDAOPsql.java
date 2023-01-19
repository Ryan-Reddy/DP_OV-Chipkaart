package daoPsql;

import dao.AdresDAO;
import dao.OVChipkaartDAO;
import dao.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Reiziger dao psql.
 */
public class ReizigerDAOPsql implements ReizigerDAO {
    static Connection localConn;
    private AdresDAO adresDAO;
    private OVChipkaartDAO ovChipkaartDAO;
    public ReizigerDAOPsql(Connection conn) {
        // 1. Connect met de database
        localConn = conn;
    }
    /**
     * @param reiziger de reiziger aanmaken, wijzigingen opslaan
     * @return het opslaan gelukt?
     */
    @Override
    public Reiziger save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger (voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                    "VALUES (?, ?, ?, ?) ";
            PreparedStatement ps = localConn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creëren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reiziger.setId(generatedKeys.getInt("reiziger_id"));
                }
                else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            Adres adres= reiziger.getAdres();
            if(adres!=null) adresDAO.save(adres);
            ps.close();

            return reiziger;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param reiziger de up te daten reiziger
     * @return het updaten gelukt?
     */
    @Override
    public Reiziger update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? " +
                    "WHERE reiziger_id = ?";
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();

            Adres adres= reiziger.getAdres();
            if(adres!=null) adresDAO.save(adres);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reiziger;
    }
    /**
     * @param reiziger de te verwijderen reiziger
     */
    @Override
    public void delete(Reiziger reiziger) {
        try {
            adresDAO.delete(reiziger.getAdres());
            for(OVChipkaart ovChipkaart:reiziger.getOvChipkaarten()) ovChipkaartDAO.delete(ovChipkaart);

            PreparedStatement ps = localConn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Delete failed, geen rijen gewijzigd.");
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("DELETE FAILED" + e);
        }
    }
    /**
     * Find reiziger by id reiziger.
     *
     * @param ID the reiziger id
     * @return the reiziger
     */
    @Override
    public Reiziger findByID(int ID) {

        try {
            PreparedStatement psReiziger =
                    localConn.prepareStatement("SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?");
            psReiziger.setInt(1, ID);
            ResultSet rs = psReiziger.executeQuery();

            if(!rs.next()) throw new RuntimeException("No Reiziger found with this Reiziger ID");

            String tussenvoegsel = rs.getString("tussenvoegsel");
            Reiziger reiziger = new Reiziger(rs.getString("voorletters"),
                    tussenvoegsel,
                    rs.getString("achternaam"),
                    rs.getDate("geboortedatum").toLocalDate(),
                    rs.getInt("reiziger_id"));

            // haal adres van deze reiziger op
            Adres adres = adresDAO.findByReiziger(reiziger);
            if(adres!=null) reiziger.setAdres(adres);

            // haal ovchipkaarten van deze reiziger op
            List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
            reiziger.setOvChipkaarten((ArrayList<OVChipkaart>) ovchips);

            return reiziger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param datum De reizigers van deze specifieke geboortedatum vinden
     * @return lijst van Reizigers wiens geboortedatum overeenkomt met de invoer.
     */
    @Override
    public List<Reiziger> findByGbDatum(LocalDate datum) {
        List<Reiziger> alleReizigers = new ArrayList<>();
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            ps.setDate(1, Date.valueOf(datum));
            ResultSet myResultSet = ps.executeQuery();

            while (myResultSet.next()) {
                Reiziger reiziger = extractReizigerRs(myResultSet);

                Adres adres = adresDAO.findByReiziger(reiziger);
                        reiziger.setAdres(adres);

                        List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
                        reiziger.setOvChipkaarten((ArrayList<OVChipkaart>) ovchips);
                alleReizigers.add(reiziger);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleReizigers;
    }

    /**
     * @return lijst met alle Reizigers in de db
     */
    @Override
    public ArrayList<Reiziger> findAll() throws SQLException {
        PreparedStatement preparedStatement = localConn.prepareStatement("select * from reiziger");
        ResultSet myResultSet = preparedStatement.executeQuery();

        ArrayList<Reiziger> alleReizigers = new ArrayList<>();

        try {
            while (myResultSet.next()) {
                Reiziger reiziger = extractReizigerRs(myResultSet);

                Adres adres = adresDAO.findByReiziger(reiziger);
                if(adres != null) reiziger.setAdres(adres);

                List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
                reiziger.setOvChipkaarten((ArrayList<OVChipkaart>) ovchips);

                alleReizigers.add(reiziger);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleReizigers;
    }
    private Reiziger extractReizigerRs(ResultSet myResultSet) throws SQLException {
        return new Reiziger(
                myResultSet.getString("voorletters"),
                myResultSet.getString("tussenvoegsel"),
                myResultSet.getString("achternaam"),
                myResultSet.getDate("geboortedatum").toLocalDate(),
                myResultSet.getInt("reiziger_id"));
    }
    @Override
    public void setAdresDAO(AdresDAO adresDAO) { this.adresDAO = adresDAO; }
    @Override
    public void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAO) { this.ovChipkaartDAO = ovChipkaartDAO; }
}
