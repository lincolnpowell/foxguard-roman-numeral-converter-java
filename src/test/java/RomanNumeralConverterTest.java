import com.foxguardsolutions.RomanNumeralConverter;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralConverterTest {
    private RomanNumeralConverter romanNumeralConverter;

    @BeforeEach
    public void setUp() {
        romanNumeralConverter = new RomanNumeralConverter();
    }

    @AfterEach
    public void tearDown() {
        romanNumeralConverter = null;
    }

    @Test
    public void testInvalidFilePathShouldThrowException() {
        String invalidFilePath = "invalid.txt";
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            romanNumeralConverter.readFile(invalidFilePath);
        });
        assertEquals("File not found using path " + invalidFilePath, throwable.getMessage());
    }

    @Test
    public void testEmptyFirstLineInFileShouldReturnSecondLineOfIII() throws URISyntaxException {
        assertEquals("III", romanNumeralConverter.readFile(new File(this.getClass().getClassLoader().getResource("test1.txt").toURI()).getAbsolutePath()), "test1.txt, despite empty first line, must read entire file returning III");
    }

    @Test
    public void testEmptyFileShouldThrowException() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            romanNumeralConverter.readFile(new File(this.getClass().getClassLoader().getResource("test2.txt").toURI()).getAbsolutePath());
        });
        assertEquals("File is empty", throwable.getMessage());
    }

    @Test
    public void testRomanNumeralVIIShouldReturn7() {
        assertEquals(7, romanNumeralConverter.convertRomanNumeralsToDecimal("VII"), "Roman numeral VII must be 7");
    }

    @Test
    public void testRomanNumeralVXShouldReturn5() {
        assertEquals(5, romanNumeralConverter.convertRomanNumeralsToDecimal("VX"), "Roman numeral VX must be 5");
    }

    @Test
    public void testRomanNumeralMCMLXXXIVShouldReturn1984() {
        assertEquals(1984, romanNumeralConverter.convertRomanNumeralsToDecimal("MCMLXXXIV"), "Roman numeral MCMLXXXIV must be 1984");
    }

    @Test
    public void testRomanNumeralIVIShouldReturn5() {
        assertEquals(5, romanNumeralConverter.convertRomanNumeralsToDecimal("IVI"), "Roman numeral IVI must be 5");
    }

    @Test
    public void testRomanNumeralWithSpaceShouldThrowException() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            romanNumeralConverter.convertRomanNumeralsToDecimal("MCML XXXIV");
        });
        assertEquals("Roman numeral string contains space", throwable.getMessage());
    }

    @Test
    public void testRomanNumeralABCShouldThrowException() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            romanNumeralConverter.convertRomanNumeralsToDecimal("ABC");
        });
        assertEquals("Roman numeral string contains invalid character A", throwable.getMessage());
    }
}