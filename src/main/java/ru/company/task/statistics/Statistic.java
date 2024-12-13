package ru.company.task.statistics;

public abstract class Statistic {
    protected int count;

    public abstract boolean process(String rawValue);

    public abstract String getStatisticBrief(String prefix);

    public abstract String getStatisticFull(String prefix);
}
