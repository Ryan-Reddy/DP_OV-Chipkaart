package DAOPsql;

import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
import DAO.ReizigerDAO;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ov chipkaart dao psql.
 */
public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    Connection localConn;
    ReizigerDAO reizigerDAO;
    ProductDAO productDAO;
    AdresDAO adresDAO;


    /**
     * Instantiates a new Ov chipkaart dao psql.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
    public OVChipkaartDAOPsql(Connection conn) throws SQLException {
        // 1. Connect met de database
        localConn = conn;
    }

    public OVChipkaart save(OVChipkaart ovChipkaart) {
        String query = "INSERT INTO ov_chipkaart (geldig_tot, klasse, saldo, reiziger_id) "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, ovChipkaart.getGeldig_tot());
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setDouble(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getReiziger().getId());

            int gewijzigdeRijen = ps.executeUpdate();
            if (gewijzigdeRijen == 0) {
                throw new SQLException("Creeren van user gefaald, niks veranderd in DB.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ovChipkaart.setKaartNummer(generatedKeys.getInt("kaart_nummer"));
                }
                else {
                    throw new SQLException("Opslaan van user gefaald, geen ID response.");
                }
            }
            ps.close();

            return ovChipkaart;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public OVChipkaart update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            ps.setInt(6, ovChipkaart.getKaart_nummer());
            ps.executeUpdate();


            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Update failed, geen rijen gewijzigd.");
            else System.out.println("Update successful: " + response + " rijen gewijzigd.");
            ps.close();

            return findByID(ovChipkaart.getKaart_nummer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement ps = localConn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaart.getKaart_nummer());

            int response = ps.executeUpdate();
            if (response == 0) System.out.println("Delete failed, geen rijen gewijzigd.");
            else System.out.println("Delete successful: " + response + " rijen gewijzigd.");
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * uses the input int ID to find OVChipkaart, will return just one.
     * @param ovChipkaartID
     * @return OVChipkaart object
     */
    public OVChipkaart findByID(int ovChipkaartID) {
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaartID);
            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            OVChipkaart ovChipkaart = new OVChipkaart(
                    myResultSet.getDate("geldig_tot").toLocalDate(),
                    myResultSet.getInt("klasse"),
                    myResultSet.getDouble("saldo"),
                    reizigerDAO.findByID(myResultSet.getInt("reiziger_id")),
                    myResultSet.getInt("kaart_nummer")
            );

// TODO PRODUCTEN connectie implementeren:

//            productDAO.
//            ovChipkaart.addProductAanKaart();

        return ovChipkaart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        List<OVChipkaart> alleOVChipkaarten = new ArrayList<OVChipkaart>();
//        this.reizigerDAO =  new ReizigerDAOPsql(localConn);
//        this.adresDAO = new AdresDAOPsql(localConn);

        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ResultSet myResultSet = ps.executeQuery();

            while (myResultSet.next()) {
                alleOVChipkaarten.add(
                        new OVChipkaart(
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                                reiziger,
                                myResultSet.getInt("kaart_nummer")
                        ));
            }
            return alleOVChipkaarten;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Find all OVChipkaarten.
     *
     * @return the OVChipkaarten as ArrayList
     * @throws SQLException
     */
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        ArrayList<OVChipkaart> alleOVChipkaarten = new ArrayList<OVChipkaart>();

        String query = "select * from ov_chipkaart";
        PreparedStatement preparedStatement = localConn.prepareStatement(query);
        ResultSet myResultSet = preparedStatement.executeQuery();

        try {
            while (myResultSet.next()) {
                alleOVChipkaarten.add(new OVChipkaart(
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                        reizigerDAO.findByID(myResultSet.getInt("reiziger_id")),
                        myResultSet.getInt("kaart_nummer")
                ));
            }
            return alleOVChipkaarten;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setReizigerDAO(ReizigerDAO reizigerDAO) { this.reizigerDAO = reizigerDAO; }
    @Override
    public void setAdresDAO(AdresDAO adresDAO) { this.adresDAO = adresDAO; }
    @Override
    public void setProductDAO(ProductDAO productDAO) { this.productDAO = productDAO; }
}
