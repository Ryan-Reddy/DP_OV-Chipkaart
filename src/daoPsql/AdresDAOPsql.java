package daoPsql;

import dao.AdresDAO;
import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Adres dao psql.
 */
public class AdresDAOPsql implements AdresDAO {
    public static final String POSTCODE = "postcode";
    public static final String ADRES_ID = "adres_id";
    public static final String REIZIGER_ID = "reiziger_id";
    public static final String WOONPLAATS = "woonplaats";
    public static final String STRAAT = "straat";
    public static final String HUISNUMMER = "huisnummer";
    private static Connection localConn;

    /**
     * Instantiates a new Adres dao psql.
     *
     * @param conn the connection
     */
    public AdresDAOPsql(Connection conn) {
        localConn = conn;
    }


    public Adres save(Adres adres) {
        String query = "INSERT INTO adres (postcode, huisnummer, straat, woonplaats, reiziger_id) " +
                "VALUES (?, ?, ?, ?, ?)" +
                "ON CONFLICT (reiziger_id) DO " +
                "UPDATE SET " +
                "postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getReizigerId());

            ps.setString(6, adres.getPostcode());
            ps.setString(7, adres.getHuisnummer());
            ps.setString(8, adres.getStraat());
            ps.setString(9, adres.getWoonplaats());
            ps.setInt(10, adres.getReizigerId());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creëren van user gefaald, niks veranderd in DB.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    adres.setAdresId(generatedKeys.getInt(AdresDAOPsql.ADRES_ID));
                } else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            ps.close();

            return adres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Adres update(Adres adres) {
        String query = "UPDATE adres SET adres_id =?, postcode =?, huisnummer =?, straat =?, woonplaats =?, reiziger_id =? WHERE reiziger_id = ? ";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres.getAdresId());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReizigerId());
            ps.setInt(7, adres.getReizigerId());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();

            return findByID(adres.getAdresId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Adres adres) {
        String query = "DELETE FROM adres WHERE adres_id = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adres.getAdresId());
            int response = ps.executeUpdate();
            if (response == 0) {System.out.println("Delete failed, geen rijen gewijzigd.");
                return false;}
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param adresIdInput int ID van het adres te zoeken
     */
    @Override
    public Adres findByID(int adresIdInput) {
        String query = "SELECT * FROM adres WHERE adres_id = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, adresIdInput);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("No Adres found with this adres ID");
            }
            Adres adres = extractAdresFromRs(rs);

                ps.close();
                return adres;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ResultSet rs = ps.executeQuery();
            Adres adres = null;
            if(rs.next()) {
                adres = new Adres(rs.getString(POSTCODE),
                        rs.getString(HUISNUMMER),
                        rs.getString(STRAAT),
                        rs.getString(WOONPLAATS),
                        rs.getInt(REIZIGER_ID),
                        rs.getInt(ADRES_ID)
                );
            }

            ps.close();
            return adres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Adres> findAll() {
        String query = "select * from adres";
        ArrayList<Adres> alleAdressen = new ArrayList<>();

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ResultSet myResultSet = ps.executeQuery();
            try {
                while (myResultSet.next()) {
                    Adres adres = extractAdresFromRs(myResultSet);
                    alleAdressen.add(adres);
                }
                ps.close();
                return alleAdressen;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Adres extractAdresFromRs(ResultSet myResultSet) throws SQLException {
        int adresId = myResultSet.getInt(AdresDAOPsql.ADRES_ID);
        String postcode = myResultSet.getString(POSTCODE);
        String huisnummer = myResultSet.getString(HUISNUMMER);
        String straat = myResultSet.getString(STRAAT);
        String woonplaats = myResultSet.getString(WOONPLAATS);
        int reizigerId = myResultSet.getInt(REIZIGER_ID);
        return new Adres(postcode, huisnummer, straat, woonplaats, reizigerId, adresId);
    }
}
