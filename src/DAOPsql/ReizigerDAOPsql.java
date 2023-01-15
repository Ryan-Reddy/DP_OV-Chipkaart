package DAOPsql;

import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
import DAO.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Reiziger dao psql.
 */
public class ReizigerDAOPsql implements ReizigerDAO {
    static Connection localConn;
    private AdresDAO adresDAO;
    private OVChipkaartDAO ovChipkaartDAO;
    private ReizigerDAO reizigerDAO;
    private ProductDAO productDAO;
    String query = null;
    private PreparedStatement myStatement;

    public ReizigerDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        this.localConn = conn;
//
//        this.adresDAO = new AdresDAOPsql(localConn);
//        this.ovChipkaartDAO = new OVChipkaartDAOPsql(localConn);
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
                throw new SQLException("Creeren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reiziger.setId(generatedKeys.getInt("reiziger_id"));
                }
                else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
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
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, (Date) reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());
            ps.executeUpdate();

            return findByID(reiziger.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param reiziger de te verwijderen reiziger
     * @return boolean of het gelukt is
     */
    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement ps = localConn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ps.execute();
            return true;
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
    public Reiziger findByID(int ID) throws SQLException {

        try {
            PreparedStatement psReiziger =
                    localConn.prepareStatement("SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, " +
                            "geboortedatum FROM reiziger WHERE reiziger_id = ?");
            psReiziger.setInt(1, ID);
            ResultSet ReizigerResultSet = psReiziger.executeQuery();
            ReizigerResultSet.next();
            Reiziger reiziger = new Reiziger(ReizigerResultSet.getString("voorletters"),
                    ReizigerResultSet.getString("tussenvoegsel"),
                    ReizigerResultSet.getString("achternaam"),
                    ReizigerResultSet.getDate("geboortedatum").toLocalDate(),
                    ReizigerResultSet.getInt("reiziger_id"));

            // haal adres van deze reiziger op
            Adres adres = adresDAO.findByReiziger(reiziger);
            reiziger.setAdres(adres);

            // haal ovchipkaarten van deze reiziger op
            List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
            reiziger.setOvChipkaarts((ArrayList<OVChipkaart>) ovchips);

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
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        List<Reiziger> alleReizigers = new ArrayList<>();
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            ps.setString(1, datum);
            ResultSet myResultSet = ps.executeQuery();

            while (myResultSet.next()) {
                Reiziger reiziger = new Reiziger(
                        myResultSet.getString("voorletters"),
                        myResultSet.getString("tussenvoegsel"),
                        myResultSet.getString("achternaam"),
                        myResultSet.getDate("geboortedatum").toLocalDate(),
                        myResultSet.getInt("reiziger_id"));

                        Adres adres = adresDAO.findByReiziger(reiziger);
                        reiziger.setAdres(adres);

                        List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
                        reiziger.setOvChipkaarts((ArrayList<OVChipkaart>) ovchips);
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
        ArrayList<Reiziger> alleReizigers = new ArrayList<Reiziger>();
        try {
            while (myResultSet.next()) {
                Reiziger reiziger = new Reiziger(
                        myResultSet.getString("voorletters"),
                        myResultSet.getString("tussenvoegsel"),
                        myResultSet.getString("achternaam"),
                        myResultSet.getDate("geboortedatum").toLocalDate(),
                        myResultSet.getInt("reiziger_id"));

                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                List<OVChipkaart> ovchips = ovChipkaartDAO.findByReiziger(reiziger);
                reiziger.setOvChipkaarts((ArrayList<OVChipkaart>) ovchips);

                alleReizigers.add(reiziger);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alleReizigers;
    }
    @Override
    public int getAdresId(int reizigerId) throws SQLException {
        // Adres_ID ophalen;
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        preparedStatement.setInt(1, reizigerId);
        ResultSet myResultSet = preparedStatement.executeQuery();
        myResultSet.next();

        return myResultSet.getInt("adres_id");
    }
    @Override
    public void setAdresDAO(AdresDAO adresDAO) { this.adresDAO = adresDAO; }
    @Override
    public void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAO) { this.ovChipkaartDAO = ovChipkaartDAO; }
    @Override
    public void setProductDAOPsql(ProductDAO productDAOPsql) { this.productDAO = productDAOPsql; }

}
