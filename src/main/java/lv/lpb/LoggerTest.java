package lv.lpb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        logger.debug("Hello world.");
    }
}
