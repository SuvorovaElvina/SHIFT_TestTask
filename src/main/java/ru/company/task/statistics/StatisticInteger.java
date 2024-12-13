package ru.company.task.statistics;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@Getter
public class StatisticInteger extends Statistic {
    private BigInteger min = null;
    private BigInteger max = null;
    private BigInteger sum = BigInteger.ZERO;

    @Override
    public boolean process(String rawValue) {
        try {
            BigInteger value = new BigInteger(rawValue);
            count++;

            if (min == null) {
                min = value;
            } else {
                min = min.min(value);
            }
            if (max == null) {
                max = value;
            } else {
                max = max.max(value);
            }
            sum = sum.add(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getStatisticBrief(String prefix) {
        String string = "";
        if (count != 0) {
            string = String.format("%sintegers.txt%nСодержит %s элементов", prefix, count);
        }
        return string;
    }

    @Override
    public String getStatisticFull(String prefix) {
        String string = getStatisticBrief(prefix);
        if (!string.isEmpty()) {
            BigDecimal average = averageNumbers(new BigDecimal(sum), count);
            string = String.format("%s%n" +
                            "Минимальное значение %s%n" +
                            "Максимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                    string, min, max, sum, average);
        }
        return string;
    }

    private BigDecimal averageNumbers(BigDecimal sum, long count) {
        return sum.divide(BigDecimal.valueOf(count), MathContext.DECIMAL32);
    }
}
