package utilities;

import org.apache.log4j.Logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

public class Logging {

        private static Logging instance;
        private static final Logger logger = Logger.getLogger(Logging.class);

        public static Logging getInstance() {
            if (instance == null){
                instance = new Logging();
                BasicConfigurator.configure();
                logger.setLevel(Level.ALL);
            }
            return instance;
        }

        public void info(String myclass, String msg) {
            logger.info("[" + myclass + "] " + msg);

        }

        public void error(String myclass, String msg, Exception ce) {
            logger.error("[" + myclass + "] " + msg, ce);
        }

        public void warning(String myclass, String msg) {
            logger.warn("[" + myclass + "] " + msg);
        }
}
