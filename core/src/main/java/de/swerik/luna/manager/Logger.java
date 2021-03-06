package de.swerik.luna.manager;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.swerik.luna.Luna;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {

    // Colors:
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    // Levels:
    public static final int OFF = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;

    private static final ApplicationLogger logger = Gdx.app.getApplicationLogger();
    private static FileHandle logFile;
    private static SimpleDateFormat dateFormatter;
    private static boolean writeToConsole;
    private static boolean writeToFile;
    private static int level;

    public static void initLogManager(int lvl, boolean file, boolean console) {
        writeToFile = file;
        writeToConsole = console;
        Gdx.app.setLogLevel(level = lvl);
        logFile = Gdx.files.local("log.txt");
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

        System.out.println();
        if (writeToFile) {
            logFile.writeString("----- Log [" + dateFormatter.format(new Date()) + "] -----\n\r", false);
        }
    }

    public static void log(String msg) {
        String coloredTag = "";
        String tag = "";
        switch (level) {
            case OFF:
                return;
            case ERROR:
                coloredTag = ANSI_RED;
                tag = "ERROR";
                break;
            case INFO:
                tag = "Info";
                coloredTag = ANSI_WHITE;
                break;
            case DEBUG:
                if (!Luna.DEBUG) {
                    return;
                }
                coloredTag = ANSI_BLUE;
                tag = "Debug";
                break;
        }
        coloredTag += tag + ANSI_RESET;

        if (writeToConsole) {
            if (level == INFO) {
                logger.log(coloredTag, "\t" + msg);
            } else {
                logger.log(coloredTag, msg);
            }
        }
        if (writeToFile) {
            logFile.writeString("[" + tag + "]\t[" + dateFormatter.format(new Date()) + "]\t" + msg + "\n", true);
        }
    }

    public static void log(String msg, int lvl) {
        //save temp Level
        int tempLvl = Gdx.app.getLogLevel();
        Gdx.app.setLogLevel(level = lvl);

        log(msg);

        Gdx.app.setLogLevel(level = tempLvl);
    }

    public static void newLine() {
        if (writeToConsole) {
            System.out.println();
        }
        if (writeToFile) {
            logFile.writeString("\n", true);
        }
    }

    public static void logToFile(String msg) {
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = false;
        writeToFile = true;

        log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
    }

    public static void logToConsole(String msg) {
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = true;
        writeToFile = false;

        log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
    }

    public static void logToFile(String msg, int lvl) {
        //save temp Level
        int tempLvl = level;
        Gdx.app.setLogLevel(level = lvl);
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = false;
        writeToFile = true;

        log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
        Gdx.app.setLogLevel(level = tempLvl);
    }

    public static void logToConsole(String msg, int lvl) {
        //save temp Level
        int tempLvl = level;
        Gdx.app.setLogLevel(level = lvl);
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = true;
        writeToFile = false;

        log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
        Gdx.app.setLogLevel(level = tempLvl);
    }

    public static void setWriteToFile(boolean file) {
        writeToFile = file;
    }

    public static void setWriteToConsole(boolean console) {
        writeToConsole = console;
    }

    public static void setLevel(int lvl) {
        Gdx.app.setLogLevel(level = lvl);
    }
}