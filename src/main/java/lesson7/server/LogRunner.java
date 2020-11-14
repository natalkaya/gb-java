package lesson7.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

interface LogRunner {

    default Logger initLogger(String logName) {
        Logger log = Logger.getLogger(logName);
        try {
            FileHandler fileHandler = new FileHandler(logName.replace(".", "-") + ".log", true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }
}
