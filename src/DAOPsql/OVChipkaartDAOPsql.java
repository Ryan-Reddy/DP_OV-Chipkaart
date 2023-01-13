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

    public boolean save(OVChipkaart ovChipkaart) {
        String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            int ovChipkaart_getID = ovChipkaart.getKaart_nummer();
            if (ovChipkaart_getID == 0) {
                ovChipkaart_getID = findAll().size()+1;
            }
            ps.setInt(1, ovChipkaart_getID);
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            ps.setInt(6, ovChipkaart.getKaart_nummer());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
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
     * uses the input int ID to find OVChipkaart, will return just one.
     * @param ovChipkaartID
     * @return
     */
    public OVChipkaart findByID(int ovChipkaartID) {
        try {
            PreparedStatement ps = localConn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaartID);
            ResultSet myResultSet = ps.executeQuery();
            myResultSet.next();

            OVChipkaart ovChipkaart = new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                    myResultSet.getDate("geldig_tot").toLocalDate(),
                    myResultSet.getInt("klasse"),
                    myResultSet.getDouble("saldo"),
                    reizigerDAO.findByID(myResultSet.getInt("reiziger_id"))
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
                        new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                                reiziger
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
                alleOVChipkaarten.add(new OVChipkaart(myResultSet.getInt("kaart_nummer"),
                        myResultSet.getDate("geldig_tot").toLocalDate(),
                        myResultSet.getInt("klasse"),
                        myResultSet.getDouble("saldo"),
                        reizigerDAO.findByID(myResultSet.getInt("reiziger_id"))

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
