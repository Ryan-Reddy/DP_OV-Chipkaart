package DAO;

import domain.Adres;
import domain.OVChipkaart;

/**
 * The interface Ov chipkaart dao.
 */
public interface OVChipkaartDAO {
    /**
     * Save boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    boolean save(OVChipkaart ovChipkaart);

    /**
     * Update boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    boolean update(OVChipkaart ovChipkaart);

    /**
     * Delete boolean.
     *
     * @param ovChipkaart the ov chipkaart
     * @return the boolean
     */
    boolean delete(OVChipkaart ovChipkaart);

    /**
     * Find by ov chipkaart id ov chipkaart.
     *
     * @param ovChipkaartID the ov chipkaart id
     * @return the ov chipkaart
     */
    OVChipkaart findByOVChipkaartID(int ovChipkaartID);
}
