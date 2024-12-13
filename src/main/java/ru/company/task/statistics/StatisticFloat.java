package ru.company.task.statistics;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

@Getter
public class StatisticFloat extends Statistic {
    private BigDecimal min = null;

    private BigDecimal max = null;

    private BigDecimal sum = BigDecimal.ZERO;

    @Override
    public boolean process(String rawValue) {
        try {
            BigDecimal value = new BigDecimal(rawValue);
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
            string = String.format("%sfloats.txt%nСодержит %s элементов", prefix, count);
        }
        return string;
    }

    @Override
    public String getStatisticFull(String prefix) {
        String string = getStatisticBrief(prefix);
        if (!string.isEmpty()) {
            BigDecimal average = sum.divide(BigDecimal.valueOf(count), MathContext.DECIMAL32);
            string = String.format("%s%nМинимальное значение %s%nМаксимальное значение %s%nСуммарное значение %s%nСреднее значение %s",
                    string, min, max, sum, average);
        }
        return string;
    }
}
