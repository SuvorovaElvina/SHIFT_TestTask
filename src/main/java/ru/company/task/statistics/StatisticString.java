package ru.company.task.statistics;

import lombok.Getter;

@Getter
public class StatisticString extends Statistic {
    private Integer min = null;

    private int max = 0;

    @Override
    public boolean process(String rawValue) {
        count++;

        if (min == null) {
            min = rawValue.length();
        } else if (min > rawValue.length()) {
            min = rawValue.length();
        }
        if (max < rawValue.length()) {
            max = rawValue.length();
        }
        return true;
    }

    @Override
    public String getStatisticBrief(String prefix) {
        String string = "";
        if (count != 0) {
            string = String.format("%sstrings.txt%nСодержит %s элементов", prefix, count);
        }
        return string;
    }

    @Override
    public String getStatisticFull(String prefix) {
        String string = getStatisticBrief(prefix);

        if (!string.isEmpty()) {
            string = String.format("%s%nМинимальное значение %s%nМаксимальное значение %s", string, min, max);
        }
        return string;
    }
}
