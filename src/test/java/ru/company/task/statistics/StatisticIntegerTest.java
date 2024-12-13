package ru.company.task.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class StatisticIntegerTest {

    StatisticInteger statistic;

    @BeforeEach
    public void beforeEach() {
        statistic = new StatisticInteger();
    }

    @Test
    void setStatisticValueOne() {
        String integerRawValue = "4";
        BigInteger integer = new BigInteger(integerRawValue);
        statistic.process(integerRawValue);

        assertEquals(1, statistic.count);
        assertEquals(integer, statistic.getMin());
        assertEquals(integer, statistic.getMax());
        assertEquals(integer, statistic.getSum());
    }

    @Test
    void setStatisticByMin() {
        String integerRawValue = "4";
        String minIntegerRawValue = "3";
        BigInteger integer = new BigInteger(integerRawValue);
        BigInteger minInteger = new BigInteger(minIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(minIntegerRawValue);

        assertEquals(2, statistic.count);
        assertEquals(minInteger, statistic.getMin());
        assertEquals(integer, statistic.getMax());
        assertEquals(BigInteger.valueOf(7), statistic.getSum());
    }

    @Test
    void setStatisticByMax() {
        String integerRawValue = "4";
        String maxIntegerRawValue = "10";
        BigInteger integer = new BigInteger(integerRawValue);
        BigInteger maxInteger = new BigInteger(maxIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(maxIntegerRawValue);

        assertEquals(2, statistic.count);
        assertEquals(integer, statistic.getMin());
        assertEquals(maxInteger, statistic.getMax());
        assertEquals(BigInteger.valueOf(14), statistic.getSum());
    }

    @Test
    void setStatisticByMinAndMax() {
        String integerRawValue = "4";
        String minIntegerRawValue = "3";
        String maxIntegerRawValue = "10";
        BigInteger maxInteger = new BigInteger(maxIntegerRawValue);
        BigInteger minInteger = new BigInteger(minIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(minIntegerRawValue);
        statistic.process(maxIntegerRawValue);

        assertEquals(3, statistic.count);
        assertEquals(minInteger, statistic.getMin());
        assertEquals(maxInteger, statistic.getMax());
        assertEquals(BigInteger.valueOf(17), statistic.getSum());
    }

    @Test
    void setStatisticByNegative() {
        String integerRawValue = "3";
        String minIntegerRawValue = "-10";
        String maxIntegerRawValue = "4";
        BigInteger maxInteger = new BigInteger(maxIntegerRawValue);
        BigInteger minInteger = new BigInteger(minIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(minIntegerRawValue);
        statistic.process(maxIntegerRawValue);

        assertEquals(3, statistic.count);
        assertEquals(minInteger, statistic.getMin());
        assertEquals(maxInteger, statistic.getMax());
        assertEquals(BigInteger.valueOf(-3), statistic.getSum());
    }

    @Test
    void setStatisticByDoubleInteger() {
        String integerRawValue = "4";
        BigInteger integer = new BigInteger(integerRawValue);
        statistic.process(integerRawValue);
        statistic.process(integerRawValue);

        assertEquals(2, statistic.count);
        assertEquals(integer, statistic.getMin());
        assertEquals(integer, statistic.getMax());
        assertEquals(BigInteger.valueOf(8), statistic.getSum());
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
        String integerRawValue = "4";
        statistic.process(integerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов", "test_", 1);

        assertEquals(testString, statistic.getStatisticBrief("test_"));
    }

    @Test
    void getStatisticBriefWithoutPrefix() {
        String integerRawValue = "4";
        statistic.process(integerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов", "", 1);

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
        String integerRawValue = "4";
        BigInteger integer = new BigInteger(integerRawValue);
        statistic.process(integerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "test_", 1, integer, integer, integer, integer);

        assertEquals(testString, statistic.getStatisticFull("test_"));
    }

    @Test
    void getStatisticFullWithoutPrefix() {
        String integerRawValue = "4";
        BigInteger integer = new BigInteger(integerRawValue);
        statistic.process(integerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 1, integer, integer, integer, integer);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverage() {
        String integerRawValue = "4";
        String minIntegerRawValue = "3";
        BigInteger integer = new BigInteger(integerRawValue);
        BigInteger minInteger = new BigInteger(minIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(minIntegerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, minInteger, integer, 7, 3.5);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverageZero() {
        String integerRawValue = "4";
        String minIntegerRawValue = "-4";
        BigInteger integer = new BigInteger(integerRawValue);
        BigInteger minInteger = new BigInteger(minIntegerRawValue);
        statistic.process(integerRawValue);
        statistic.process(minIntegerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, minInteger, integer, 0, 0);

        assertEquals(testString, statistic.getStatisticFull(""));
    }

    @Test
    void getStatisticFullWithAverageDoubleInteger() {
        String integerRawValue = "4";
        BigInteger integer = new BigInteger(integerRawValue);
        statistic.process(integerRawValue);
        statistic.process(integerRawValue);
        String testString = String.format("%sintegers.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                "", 2, integer, integer, 8, 4);

        assertEquals(testString, statistic.getStatisticFull(""));
    }
}