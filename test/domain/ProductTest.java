//package domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ProductTest {
//
//    private Product productUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        productUnderTest = new Product(0, "naam", "beschrijving", 0);
//    }
//
//    @Test
//    void testVoegKaartToeAanLijstKaartenMetProduct() {
//        // Setup
//        final OVChipkaart ovChipkaart = new OVChipkaart(0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
//                0, "saldo", 0, List.of(new Product(0, "naam", "beschrijving", 0)));
//
//        // Run the test
//        final boolean result = productUnderTest.voegKaartToeAanLijstKaartenMetProduct(ovChipkaart);
//
//        // Verify the results
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void testVerwijderKaartUitLijstKaartenMetProduct() {
//        // Setup
//        final OVChipkaart ovChipkaart = new OVChipkaart(0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
//                0, "saldo", 0, List.of(new Product(0, "naam", "beschrijving", 0)));
//
//        // Run the test
//        final boolean result = productUnderTest.verwijderKaartUitLijstKaartenMetProduct(ovChipkaart);
//
//        // Verify the results
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void testRemoveKaartVanProductKaartenLijst() {
//        // Setup
//        final OVChipkaart ovChipkaart = new OVChipkaart(0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
//                0, "saldo", 0, List.of(new Product(0, "naam", "beschrijving", 0)));
//
//        // Run the test
//        final boolean result = productUnderTest.removeKaartVanProductKaartenLijst(ovChipkaart);
//
//        // Verify the results
//        assertThat(result).isFalse();
//    }
//}
