package testing.demo.computation_demo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class) // Enforce method order based on @Order
public class CalculatorTest {

    @BeforeAll
    public static void setUpBeforeAll() {
        System.out.println("[Before All] Calculator Test suite starting ... by chmart");
    }

    @BeforeEach
    public void setUpBeforeEach(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.println(); // Add blank line before each test
        System.out.println("[Before Each] " + displayName);
    }

    @AfterEach
    public void tearDownAfterEach(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.println("[After Each] " + displayName);
        System.out.println(); // Add blank line after each test
    }

    @AfterAll
    public static void tearDownAfterAll() {
        System.out.println("[After All] completed 13 test invocations by chmart");
    }

    // MethodSource for add
    static Stream<Arguments> addProvider() {
        return Stream.of(
            Arguments.of(100, 2, 102, "Starting Test #1: 100 + 2 = 102"),
            Arguments.of(100, -2, 98, "Starting Test #2: 100 + -2 = 98"),
            Arguments.of(-100, 2, -98, "Starting Test #3: -100 + 2 = -98"),
            Arguments.of(-100, -2, -102, "Starting Test #4: -100 + -2 = -102")
        );
    }

    @ParameterizedTest
    @MethodSource("addProvider")
    @DisplayName("Add two numbers") // Use the fourth argument as the display name
    @Order(1) // Order for add tests
    void testAdd(int value1, int value2, int expected, String displayName) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.add(value1, value2));
    }

    // CsvSource for subtract
    @ParameterizedTest
    @CsvSource({
        "100, 2, 98, Starting Test #5: 100 - 2 = 98",
        "100, -2, 102, Starting Test #6: 100 - -2 = 102",
        "-100, 2, -102, Starting Test #7: -100 - 2 = -102",
        "-100, -2, -98, Starting Test #8: -100 - -2 = -98"
    })
    @DisplayName("Subtract two numbers") // Use the fourth argument as the display name
    @Order(2) // Order for subtract tests
    void testSubtract(int value1, int value2, int expected, String displayName) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.substract(value1, value2));
    }

    // CsvFileSource for multiply
    @ParameterizedTest
    @CsvFileSource(resources = "/data/multiply.csv", numLinesToSkip = 1)
    @DisplayName("Multiply two numbers")
    @Order(3) // Order for multiply tests
    void testMultiply(int value1, int value2, int expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.multiple(value1, value2));
    }

    // Negative test for divide
    @Test
    @DisplayName("Starting Test #13: divide_byZero()")
    @Order(4) // Order for divide by zero test (last)
    void testDivideByZero() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.divide(100, 0),
            "Denominator value cannot be zero.");
    }
}