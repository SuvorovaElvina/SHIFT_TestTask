package ru.company.task.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticStringTest {

    StatisticString statistic;

    @BeforeEach
    public void beforeEach() {
        statistic = new StatisticString();
    }

    @Test
    void setStatisticValueOne() {
        statistic.process("string");

        assertEquals(1, statistic.count);
        assertEquals("string".length(), statistic.getMin());
        assertEquals("string".length(), statistic.getMax());
    }

    @Test
    void setStatisticByMin() {
        statistic.process("string");
        statistic.process("min");

        assertEquals(2, statistic.count);
        assertEquals("min".length(), statistic.getMin());
        assertEquals("string".length(), statistic.getMax());
    }

    @Test
    void setStatisticByMax() {
        statistic.process("string");
        statistic.process("maxString");

        assertEquals(2, statistic.count);
        assertEquals("string".length(), statistic.getMin());
        assertEquals("maxString".length(), statistic.getMax());
    }

    @Test
    void setStatisticByMinAndMax() {
        statistic.process("string");
        statistic.process("min");
        statistic.process("maxString");

        assertEquals(3, statistic.count);
        assertEquals("min".length(), statistic.getMin());
        assertEquals("maxString".length(), statistic.getMax());
    }

    @Test
    void setStatisticByNegative() {
        statistic.process("string");
        statistic.process("min");
        statistic.process("maxString");

        assertEquals(3, statistic.count);
        assertEquals("min".length(), statistic.getMin());
        assertEquals("maxString".length(), statistic.getMax());
    }

    @Test
    void setStatisticByDoubleString() {
        statistic.process("string");
        statistic.process("string");

        assertEquals(2, statistic.count);
        assertEquals("string".length(), statistic.getMin());
        assertEquals("string".length(), statistic.getMax());
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
        statistic.process("string");
        String testString = String.format("%sstrings.txt%nСодержит %s элементов", "test_", 1);

        assertEquals(testString, statistic.getStatisticBrief("test_"));
    }

    @Test
    void getStatisticBriefWithoutPrefix() {
        statistic.process("string");
        String testString = String.format("%sstrings.txt%nСодержит %s элементов", "", 1);

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
        statistic.process("string");
        String testString = String.format("%sstrings.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s", "test_", 1, "string".length(), "string".length());

        assertEquals(testString, statistic.getStatisticFull("test_"));
    }

    @Test
    void getStatisticFullWithoutPrefix() {
        statistic.process("string");
        String testString = String.format("%sstrings.txt%nСодержит %s элементов%nМинимальное значение %s%n" +
                        "Максимальное значение %s", "", 1, "string".length(), "string".length());

        assertEquals(testString, statistic.getStatisticFull(""));
    }
}