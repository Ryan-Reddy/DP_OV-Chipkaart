package DAOPsql;

import DAO.OVChipkaartDAO;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " + "VALUES (?, ?, ?, ?, ?)";
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger_id());

            return ps.executeUpdate() == 1;

            // TODO schrijf functie product_nummer ???


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger_id());
            ps.setInt(6, ovChipkaart.getKaart_nummer());

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
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.execute();
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
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        ArrayList<OVChipkaart> alleOVChipkaarten = new ArrayList<OVChipkaart>();

        String query = "select * from ov_chipkaart";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        ResultSet myResultSet = preparedStatement.executeQuery();

        try {
            while (myResultSet.next()) {
                alleOVChipkaarten.add(new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                        myResultSet.getInt("reiziger_id")));
            }
            return alleOVChipkaarten;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public OVChipkaart findByOVChipkaartID(int ovChipkaartID) {
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaartID);

            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            return new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                    myResultSet.getDate("geldig_tot").toLocalDate(),
                    myResultSet.getInt("klasse"),
                    myResultSet.getDouble("saldo"),
                    myResultSet.getInt("reiziger_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {

        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        List<OVChipkaart> alleOVChipkaarten = null;
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ResultSet myResultSet = ps.executeQuery();

            while (myResultSet.next()) {
                alleOVChipkaarten.add(
                        new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                        myResultSet.getInt("reiziger_id")));
            }
            return alleOVChipkaarten;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
