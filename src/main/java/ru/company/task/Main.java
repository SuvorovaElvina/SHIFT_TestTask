package ru.company.task;

import ru.company.task.utils.FileProcessor;

public class Main {
    public static void main(String[] args) {
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.sortByFiles(args);
    }
}
