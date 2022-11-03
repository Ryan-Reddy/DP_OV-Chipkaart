package DAOPsql;

import DAO.OVChipkaartDAO;
import domain.OVChipkaart;

import java.sql.*;
import java.util.ArrayList;

/**
 * The type Ov chipkaart dao psql.
 */
public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private static Connection localConn;
    private Statement myStatement;

    /**
     * Instantiates a new Ov chipkaart dao psql.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
    public OVChipkaartDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
        // 2. Creeer een statement
        Statement myStatement = localConn.createStatement();
    }

    public boolean save(OVChipkaart ovChipkaart) {
        String query = "INSERT INTO ov_chipkaart (kaart_nummer, product_nummer, status, last_update) " + "VALUES (?, ?, ?, ?, ?)";
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(ovChipkaart.getKaart_nummer()));
            ps.setString(2, String.valueOf(ovChipkaart.getGeldig_tot()));
            ps.setString(3, String.valueOf(ovChipkaart.getKlasse()));
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setString(5, String.valueOf(ovChipkaart.getReiziger_id()));

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?, ?) WHERE kaart_nummer = " + ovChipkaart.getKaart_nummer();
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setString(3, String.valueOf(ovChipkaart.getKlasse()));
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger_id());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param ovChipkaart
     * @return
     */
    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(ovChipkaart.getKaart_nummer()));
            ps.executeQuery();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find all object.
     *
     * @return the object
     * @throws SQLException the sql exception
     */
    public Object findAll() throws SQLException {
        ArrayList<OVChipkaart> alleOVChipkaarten = new ArrayList<OVChipkaart>();

        String query = "select * from ov_chipkaart";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        ResultSet myResultSet = preparedStatement.executeQuery();

        try {
            while (myResultSet.next()) {
                int kaart_nummer = myResultSet.getInt("kaart_nummer");
                Date geldig_tot = myResultSet.getDate("geldig_tot");
                int klasse = myResultSet.getInt("klasse");
                double saldo = myResultSet.getInt("saldo");
                int reiziger_id = myResultSet.getInt("reiziger_id");

                alleOVChipkaarten.add(new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            return alleOVChipkaarten;
        }
    

    public OVChipkaart findByOVChipkaartID(int ovChipkaartID) {
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaartID);

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            int kaart_nummer = myResultSet.getInt("kaart_nummer");
            Date geldig_tot = myResultSet.getDate("geldig_tot");
            int klasse = myResultSet.getInt("klasse");
            double saldo = myResultSet.getInt("saldo");
            int reiziger_id = myResultSet.getInt("reiziger_id");

            return new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
