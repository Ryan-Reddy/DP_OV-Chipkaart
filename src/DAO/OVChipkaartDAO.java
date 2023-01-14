package DAO;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Ov chipkaart dao.
 */
public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    OVChipkaart update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    OVChipkaart findByID(int ovChipkaartID);
    List<OVChipkaart> findAll() throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    void setReizigerDAO(ReizigerDAO reizigerDAO);
    void setAdresDAO(AdresDAO adresDAO);

    void setProductDAO(ProductDAO productDAO);
}
