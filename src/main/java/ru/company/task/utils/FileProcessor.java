package ru.company.task.utils;


import lombok.extern.slf4j.Slf4j;
import ru.company.task.command.CommandConfig;
import ru.company.task.command.CommandParser;
import ru.company.task.statistics.Statistic;
import ru.company.task.statistics.StatisticFloat;
import ru.company.task.statistics.StatisticInteger;
import ru.company.task.statistics.StatisticString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class FileProcessor {
    private final CommandParser commandParser = new CommandParser();
    private CommandConfig config;
    private final Map<String, Statistic> statistics = new HashMap<>();
    private final Map<String, BufferedWriter> writers = new HashMap<>();
    private final String FILE_NAME_STRINGS = "strings.txt";
    private final String FILE_NAME_FLOATS = "floats.txt";
    private final String FILE_NAME_INTEGERS = "integers.txt";
    private final String REGEX_INTEGER = "[-+]?\\d+";
    private final String REGEX_FLOAT = "[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?";

    public void sortByFiles(String[] args) {
        config = commandParser.parseCommandLine(args);
        log.info("Начало обработки файлов");
        for (int i = 0; i < config.getFileNames().size(); i++) {
            try (Stream<String> lines = Files.lines(Paths.get(new File(config.getFileNames().get(i)).getPath()))) {
                log.info("Файл прочитан");
                statistics.clear();
                statistics.put(FILE_NAME_STRINGS, new StatisticString());
                statistics.put(FILE_NAME_FLOATS, new StatisticFloat());
                statistics.put(FILE_NAME_INTEGERS, new StatisticInteger());

                lines.forEach(this::matchesLine);
                writers.values().forEach(s -> {
                    try {
                        s.close();
                    } catch (IOException ignored) {}
                });
            } catch (IOException e) {
                log.error("Не возможно прочитать файл или же файл не указан");
                System.out.println("Не возможно прочитать файл или же файл не указан");
            }
            switch (config.getStatisticType()) {
                case BRIEF:
                    log.info("Запрос на получение короткой статистики");
                    statistics.values().stream().map(s -> s.getStatisticBrief(config.getPrefix())).forEach(System.out::println);
                    break;
                case FULL:
                    log.info("Запрос на получение полной статистики");
                    statistics.values().stream().map(s -> s.getStatisticFull(config.getPrefix())).forEach(System.out::println);
                default:
            }
        }
    }

    protected void matchesLine(String s) {
        if (s.matches(REGEX_INTEGER)) {
            if (statistics.get(FILE_NAME_INTEGERS).process(s)) {
                writerIntoFile(createWriter(FILE_NAME_INTEGERS), s);
            } else {
                statistics.get(FILE_NAME_STRINGS).process(s);
                writerIntoFile(createWriter(FILE_NAME_STRINGS), s);
            }
        } else if (s.matches(REGEX_FLOAT)) {
            if (statistics.get(FILE_NAME_FLOATS).process(s)) {
                writerIntoFile(createWriter(FILE_NAME_FLOATS), s);
            } else {
                writerIntoFile(createWriter(FILE_NAME_STRINGS), s);
                statistics.get(FILE_NAME_STRINGS).process(s);
            }
        } else {
            writerIntoFile(createWriter(FILE_NAME_STRINGS), s);
            statistics.get(FILE_NAME_STRINGS).process(s);
        }
    }

    private void writerIntoFile(BufferedWriter writer, String s) {
        try {
            writer.write(s + System.lineSeparator());
        } catch (IOException ignored) {
            log.error("Не возможна запись в файл для строки" + s);
            System.out.println("Не возможна запись в файл для строки" + s);
        }
    }

    private BufferedWriter createWriter(String fileName) {
        return writers.computeIfAbsent(fileName, w -> {
            Path path = Path.of(config.getOutputDir(), config.getPrefix() + fileName);
            try {
                return new BufferedWriter(new FileWriter(path.toString()));
            } catch (IOException e) {
                return null;
            }
        });
    }
}
