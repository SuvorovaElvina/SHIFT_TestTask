package ru.company.task.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileProcessorTest {
    FileProcessor fileProcessor = new FileProcessor();
    File floats = new File("floats.txt");
    File strings = new File("strings.txt");
    File integers = new File("integers.txt");
    String pathToFile = "src/test/resources/testingFiles/";

    @AfterEach
    public void after() {
        floats.delete();
        strings.delete();
        integers.delete();
    }

    @Test
    void sortByFiles() {
        String[] args = new String[]{pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesWithPrefix() {
        String[] args = new String[]{"-p", "test_", pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        File floatsTest = new File("test_floats.txt");
        File stringsTest = new File("test_strings.txt");
        File integersTest = new File("test_integers.txt");

        assertTrue(floatsTest.exists());
        assertTrue(stringsTest.exists());
        assertTrue(integersTest.exists());

        floatsTest.delete();
        stringsTest.delete();
        integersTest.delete();
    }

    @Test
    void sortByFilesWithPrefixIsEmpty() {
        String[] args = new String[]{"-p", "", pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesGetBriefStatistic() {
        String[] args = new String[]{"-s", pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesGetFullStatistic() {
        String[] args = new String[]{"-f", pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesWithoutFloats() {
        String[] args = new String[]{pathToFile + "withoutFloats.txt"};
        fileProcessor.sortByFiles(args);

        assertFalse(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesWithoutStrings() {
        String[] args = new String[]{pathToFile + "withoutStrings.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertFalse(strings.exists());
        assertTrue(integers.exists());
    }

    @Test
    void sortByFilesWithoutIntegers() {
        String[] args = new String[]{"-s", pathToFile + "withoutIntegers.txt"};
        fileProcessor.sortByFiles(args);

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertFalse(integers.exists());
    }

    @Test
    void sortByFilesWithPath() throws IOException {
        Path dir = Paths.get("files");
        Files.createDirectories(dir);
        String[] args = new String[]{"-o", "files", pathToFile + "withAll.txt"};
        fileProcessor.sortByFiles(args);

        File floats = new File("files/floats.txt");
        File strings = new File("files/strings.txt");
        File integers = new File("files/integers.txt");

        assertTrue(floats.exists());
        assertTrue(strings.exists());
        assertTrue(integers.exists());

        floats.delete();
        strings.delete();
        integers.delete();
        Files.delete(dir);
    }
}