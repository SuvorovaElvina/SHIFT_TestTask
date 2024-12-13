package ru.company.task.command;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.company.task.statistics.StatisticType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandParserTest {
    final CommandParser commandParser = new CommandParser();
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    CommandConfig config;

    @BeforeAll
    public static void before() {
        new MockUp<System>() {
            @Mock
            public void exit(int value) {
                throw new RuntimeException(String.valueOf(value));
            }
        };
    }

    @BeforeEach
    public void beforeEach() {
        config = new CommandConfig();
        config.setFileNames(List.of("test.txt"));

        System.setOut(new PrintStream(output));
    }

    @Test
    void parseCommandLineWithoutAll() {
        String expResult, actResult;

        expResult = "Обязательно должен присутствовать один файл для прочтения";
        try {
            commandParser.parseCommandLine(new String[]{});
        } catch (RuntimeException e) {
            actResult = output.toString().replaceAll("\n", "").replaceAll("\r", "");
            assertEquals(expResult, actResult);
            assertEquals("-1", e.getMessage());
        }
    }

    @Test
    void parseCommandLineWithoutPath() {
        String expResult, actResult;

        expResult = "Неверно указан путь для выходных файлов";
        try {
            commandParser.parseCommandLine(new String[]{"-o", "test.txt"});
        } catch (RuntimeException e) {
            actResult = output.toString().replaceAll("\n", "").replaceAll("\r", "");
            assertEquals(expResult, actResult);
            assertEquals("-1", e.getMessage());
        }
    }

    @Test
    void parseCommandLineWithoutRequiredArgumentForPath() {
        String expResult, actResult;

        expResult = "Не указан обязательный агрумент";
        try {
            commandParser.parseCommandLine(new String[]{"-o"});
        } catch (RuntimeException e) {
            actResult = output.toString().replaceAll("\n", "").replaceAll("\r", "");
            assertEquals(expResult, actResult);
            assertEquals("-1", e.getMessage());
        }
    }

    @Test
    void parseCommandLineWithoutRequiredArgumentForPrefix() {
        String expResult, actResult;

        expResult = "Не указан обязательный агрумент";
        try {
            commandParser.parseCommandLine(new String[]{"-p"});
        } catch (RuntimeException e) {
            actResult = output.toString().replaceAll("\n", "").replaceAll("\r", "");
            assertEquals(expResult, actResult);
            assertEquals("-1", e.getMessage());
        }
    }

    @Test
    void parseCommandLineWithAll() throws IOException {
        config.setOverwrite(false);
        config.setPrefix("test_");
        config.setStatisticType(StatisticType.BRIEF);
        config.setOutputDir("files");
        Path path = Path.of("files");
        Files.createDirectories(path);

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-a", "-p", "test_", "-o", "files", "-s", "test.txt"});

        assertEquals(config, actConfig);

        Files.delete(path);
    }

    @Test
    void parseCommandLineWithPrefix() {
        config.setPrefix("test_");

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-p", "test_", "test.txt"});

        assertEquals(config, actConfig);
    }

    @Test
    void parseCommandLineWithOverwrite() {
        config.setOverwrite(false);

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-a", "test.txt"});

        assertEquals(config, actConfig);
    }

    @Test
    void parseCommandLineWithStatisticTypeBrief() {
        config.setStatisticType(StatisticType.BRIEF);

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-s", "test.txt"});

        assertEquals(config, actConfig);
    }

    @Test
    void parseCommandLineWithStatisticTypeFull() {
        config.setStatisticType(StatisticType.FULL);

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-f", "test.txt"});

        assertEquals(config, actConfig);
    }

    @Test
    void parseCommandLineWithOutputDir() throws IOException {
        config.setOutputDir("files");
        Path path = Path.of("files");
        Files.createDirectories(path);

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"-o", "files", "test.txt"});

        assertEquals(config, actConfig);

        Files.delete(path);
    }

    @Test
    void parseCommandLineWithOnlyFileName() {
        config.setFileNames(List.of("files", "test.txt"));

        CommandConfig actConfig = commandParser.parseCommandLine(new String[]{"files", "test.txt"});

        assertEquals(config, actConfig);
    }
}