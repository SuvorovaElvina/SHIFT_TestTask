package ru.company.task.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticFloatTest {
    StatisticFloat statistic;

    @BeforeEach
    public void beforeEach() {
        statistic = new StatisticFloat();
    }

    @Test
    void setStatisticValueOne() {
        String decimalRawValue = "4.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        statistic.process(decimalRawValue);

        assertEquals(1, statistic.count);
        assertEquals(decimal, statistic.getMin());
        assertEquals(decimal, statistic.getMax());
        assertEquals(decimal, statistic.getSum());
    }

    @Test
    void setStatisticByMin() {
        String decimalRawValue = "4.1";
        String minDecimalRawValue = "3.4";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        BigDecimal minDecimal = new BigDecimal(minDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(minDecimalRawValue);

        assertEquals(2, statistic.count);
        assertEquals(minDecimal, statistic.getMin());
        assertEquals(decimal, statistic.getMax());
        assertEquals(BigDecimal.valueOf(7.5), statistic.getSum());
    }

    @Test
    void setStatisticByMax() {
        String decimalRawValue = "4.1";
        String maxDecimalRawValue = "10.4";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        BigDecimal maxDecimal = new BigDecimal(maxDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(maxDecimalRawValue);

        assertEquals(2, statistic.count);
        assertEquals(decimal, statistic.getMin());
        assertEquals(maxDecimal, statistic.getMax());
        assertEquals(BigDecimal.valueOf(14.5), statistic.getSum());
    }

    @Test
    void setStatisticByMinAndMax() {
        String decimalRawValue = "4.1";
        String minDecimalRawValue = "3.4";
        String maxDecimalRawValue = "10.4";
        BigDecimal minDecimal = new BigDecimal(minDecimalRawValue);
        BigDecimal maxDecimal = new BigDecimal(maxDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(minDecimalRawValue);
        statistic.process(maxDecimalRawValue);

        assertEquals(3, statistic.count);
        assertEquals(minDecimal, statistic.getMin());
        assertEquals(maxDecimal, statistic.getMax());
        assertEquals(BigDecimal.valueOf(17.9), statistic.getSum());
    }

    @Test
    void setStatisticByNegative() {
        String decimalRawValue = "3.4";
        String minDecimalRawValue = "-10.4";
        String maxDecimalRawValue = "4.1";
        BigDecimal minDecimal = new BigDecimal(minDecimalRawValue);
        BigDecimal maxDecimal = new BigDecimal(maxDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(minDecimalRawValue);
        statistic.process(maxDecimalRawValue);

        assertEquals(3, statistic.count);
        assertEquals(minDecimal, statistic.getMin());
        assertEquals(maxDecimal, statistic.getMax());
        assertEquals(BigDecimal.valueOf(-2.9), statistic.getSum());
    }

    @Test
    void setStatisticByDoubleDecimal() {
        String decimalRawValue = "4.4";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        statistic.process(decimalRawValue);
        statistic.process(decimalRawValue);

        assertEquals(2, statistic.count);
        assertEquals(decimal, statistic.getMin());
        assertEquals(decimal, statistic.getMax());
        assertEquals(BigDecimal.valueOf(8.8), statistic.getSum());
    }

    @Test
    void getStatisticBriefWhereCountZeroWithPrefix() {
        assertEquals("", statistic.getStatisticBrief("test_"));
    }

    @Test
    void getStatisticBriefWhereCountZeroWithoutPrefix() {
        assertEquals("", statistic.getStatisticBrief(""));
    }

    @Test
    void getStatisticBriefWithPrefix() {
        statistic.process("4.0");
        String testString = String.format("%sfloats.txt%nСодержит %s элементов", "test_", 1);

        assertEquals(testString, statistic.getStatisticBrief("test_"));
    }

    @Test
    void getStatisticBriefWithoutPrefix() {
        statistic.process("4.0");
        String testString = String.format("%sfloats.txt%nСодержит %s элементов", "", 1);

        assertEquals(testString, statistic.getStatisticBrief(""));
    }

    @Test
    void getStatisticFullWhereCountZeroWithPrefix() {
        assertEquals("", statistic.getStatisticFull("test_"));
    }

    @Test
    void getStatisticFullWhereCountZeroWithoutPrefix() {
        assertEquals("", statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithPrefix() {
        String decimalRawValue = "4.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        statistic.process(decimalRawValue);
        String testString = String.format("%sfloats.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "test_", 1, decimal, decimal, decimal, decimal);

        assertEquals(testString, statistic.getStatisticFull("test_"));
    }

    @Test
    void getStatisticFullWithoutPrefix() {
        String decimalRawValue = "4.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        statistic.process(decimalRawValue);
        String testString = String.format("%sfloats.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 1, decimal, decimal, decimal, decimal);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverage() {
        String decimalRawValue = "4.0";
        String minDecimalRawValue = "3.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        BigDecimal minDecimal = new BigDecimal(minDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(minDecimalRawValue);

        String testString = String.format("%sfloats.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, minDecimal, decimal, 7.0, 3.5);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverageZero() {
        String decimalRawValue = "4.0";
        String minDecimalRawValue = "-4.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);
        BigDecimal minDecimal = new BigDecimal(minDecimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(minDecimalRawValue);
        String testString = String.format("%sfloats.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, minDecimal, decimal, 0.0, 0.0);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverageDoubleDecimal() {
        String decimalRawValue = "4.0";
        BigDecimal decimal = new BigDecimal(decimalRawValue);

        statistic.process(decimalRawValue);
        statistic.process(decimalRawValue);
        String testString = String.format("%sfloats.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, decimal, decimal, 8.0, 4.0);

        assertEquals(testString, statistic.getStatisticFull(""));
    }
}