package DAOPsql;

import DAO.OVChipkaartDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private static Connection localConn;
    private Statement myStatement;

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
            ps.setString(1, String.valueOf(ovChipkaart.getKaart_nummer()));
            ps.setString(2, String.valueOf(ovChipkaart.getGeldig_tot()));
            ps.setString(3, String.valueOf(ovChipkaart.getKlasse()));
            ps.setString(4, ovChipkaart.getSaldo());
            ps.setString(5, String.valueOf(ovChipkaart.getReiziger_id()));

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " + "VALUES (?, ?, ?, ?, ?) WHERE kaart_nummer = " + ovChipkaart.getKaart_nummer();
        try {

            // PreparedStatement BRON: https://stackoverflow.com/questions/35554749/creating-a-prepared-statement-to-save-values-to-a-database
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(ovChipkaart.getKaart_nummer()));
            ps.setString(2, String.valueOf(ovChipkaart.getGeldig_tot()));
            ps.setString(3, String.valueOf(ovChipkaart.getKlasse()));
            ps.setString(4, ovChipkaart.getSaldo());
            ps.setString(5, String.valueOf(ovChipkaart.getReiziger_id()));

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
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = (kaartNummer) VALUES (?)";
        try {
            PreparedStatement ps = localConn.prepareStatement(query);
            ps.setString(1, String.valueOf(ovChipkaart.getKaart_nummer()));
            ps.executeQuery();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
