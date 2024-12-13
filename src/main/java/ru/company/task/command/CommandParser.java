package ru.company.task.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.company.task.statistics.StatisticType;

import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandParser {
    public CommandConfig parseCommandLine(String[] command) {
        CommandConfig config = new CommandConfig();

        log.info("Начало разбора командной строки");
        for (int i = 0; i < command.length; i++) {
            switch (command[i]) {
                case "-o" -> {
                    String outputDir = tryGetFlagValue(command, ++i);
                    if (Files.exists(Paths.get(outputDir))) {
                        log.info("Каталог сохранён");
                        config.setOutputDir(outputDir);
                    } else {
                        printExceptionAndExit("Неверно указан путь для выходных файлов");
                    }
                }
                case "-p" -> {
                    String prefix = tryGetFlagValue(command, ++i);
                    log.info("Префикс сохранён");
                    config.setPrefix(prefix);
                }
                case "-a" -> {
                    log.info("Включена запись в существующий файл");
                    config.setOverwrite(false);
                }
                case "-s" -> {
                    log.info("Получение короткой статистики включено");
                    config.setStatisticType(StatisticType.BRIEF);
                }
                case "-f" -> {
                    log.info("Получение полной статистики включено");
                    config.setStatisticType(StatisticType.FULL);
                }
                default -> {
                    log.info("Добавлено имя файла");
                    config.addFileName(command[i]);
                }
            }
        }
        if (!config.validate()) {
            printExceptionAndExit("Обязательно должен присутствовать один файл для прочтения");
        }
        return config;
    }

    private String tryGetFlagValue(String[] command, int pos) {
        if (pos >= command.length) {
            printExceptionAndExit("Не указан обязательный агрумент");
        }
        return command[pos];
    }

    private void printExceptionAndExit(String string) {
        log.error(string);
        System.out.println(string);
        System.exit(-1);
    }
}
