package ru.company.task.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.company.task.statistics.StatisticType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandConfig {
    String prefix = "";

    String outputDir = "";

    boolean overwrite = true;

    StatisticType statisticType = StatisticType.NO;

    List<String> fileNames = new ArrayList<>();

    public void addFileName(String string) {
        fileNames.add(string);
    }

    public void setOutputDir(String string) {
        outputDir = string;
    }

    public boolean validate() {
        return !fileNames.isEmpty();
    }
}
