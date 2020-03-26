package com.es.account.log;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogTest {

    private static Logger logger = Logger.getLogger(LogTest.class);

    @Test
    public void logLevel() {
        // 记录debug级别的信息
        logger.debug("This is debug message."); // 记录info级别的信息
        logger.info("This is info message."); // 记录error级别的信息
        logger.error("This is error message.");
    }
}
