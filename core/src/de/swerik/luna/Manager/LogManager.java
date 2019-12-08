package de.swerik.luna.Manager;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogManager {

    public static final int OFF = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;

    private ApplicationLogger logger = Gdx.app.getApplicationLogger();
    private boolean writeToFile;
    private boolean writeToConsole;
    private int level;
    private FileHandle logFile;
    private SimpleDateFormat dateFormatter;

    public LogManager(int level, boolean writeToFile, boolean writeToConsole) {
        this.writeToFile = writeToFile;
        this.writeToConsole = writeToConsole;
        Gdx.app.setLogLevel(this.level = level);
        logFile = Gdx.files.local("log.txt");
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

        System.out.println("");
        if (writeToFile) {
            logFile.writeString("----- Log [" + dateFormatter.format(new Date()) + "] -----\n\r", false);
        }
    }

    public void log(String msg) {
        String tag = "";
        switch (level) {
            case OFF:
                return;
            case ERROR:
                tag = "ERROR";
                break;
            case INFO:
                tag = "Info";
                break;
            case DEBUG:
                tag = "Debug";
                break;
        }

        if (writeToConsole) {
            if (level == INFO) {
                logger.log(tag, "\t" + msg);
            } else {
                logger.log(tag, msg);
            }
        }
        if (writeToFile) {
            logFile.writeString("[" + tag + "]\t[" + dateFormatter.format(new Date()) + "}\t" + msg + "\n", true);
        }
    }

    public void log(String msg, int lvl) {
        //save temp Level
        int tempLvl = Gdx.app.getLogLevel();
        Gdx.app.setLogLevel(this.level = lvl);

        this.log(msg);

        Gdx.app.setLogLevel(this.level = tempLvl);
    }

    public void logToFile(String msg) {
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = false;
        writeToFile = true;

        this.log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
    }

    public void logToConsole(String msg) {
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = true;
        writeToFile = false;

        this.log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
    }

    public void logToFile(String msg, int level) {
        //save temp Level
        int tempLvl = this.level;
        Gdx.app.setLogLevel(this.level = level);
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = false;
        writeToFile = true;

        this.log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
        Gdx.app.setLogLevel(this.level = tempLvl);
    }

    public void logToConsole(String msg, int level) {
        //save temp Level
        int tempLvl = this.level;
        Gdx.app.setLogLevel(this.level = level);
        //save temp Booleans
        boolean tempConsole = writeToConsole;
        boolean tempFile = writeToFile;
        writeToConsole = true;
        writeToFile = false;

        this.log(msg);

        //revert changes
        writeToConsole = tempConsole;
        writeToFile = tempFile;
        Gdx.app.setLogLevel(this.level = tempLvl);
    }

    public void setWriteToFile(boolean writeToFile) {
        this.writeToFile = writeToFile;
    }

    public void setWriteToConsole(boolean writeToConsole) {
        this.writeToConsole = writeToConsole;
    }

    public void setLevel(int lvl) {
        Gdx.app.setLogLevel(this.level = lvl);
    }
}