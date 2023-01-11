package DAO;

import domain.OVChipkaart;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Ov chipkaart dao.
 */
public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    OVChipkaart findByID(int ovChipkaartID);
    List<OVChipkaart> findAll() throws SQLException;
}
