package DAO;

import domain.Adres;
import domain.OVChipkaart;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);

    boolean update(OVChipkaart ovChipkaart);

    boolean delete(OVChipkaart ovChipkaart);
}
