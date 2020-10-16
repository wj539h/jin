package com.jin;

import com.jin.eudic.EudicConst;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JinLog {
    private static Logger logger = null;
    private JinLog(){}

    public static Logger getLogger(String className){
        InputStream is = null;
        if (null == logger) {
            try {
                is  = new FileInputStream(new File(EudicConst.FILE_LOG_PROP));
                LogManager.getLogManager().readConfiguration(is);
            } catch (Exception e) {
                logging.warning("input properties file is error.\n" + e.toString());
            }finally{
                try {
                    is.close();
                } catch (IOException e) {
                    logging.warning("close FileInputStream a case.\n" + e.toString());
                }
            }

            logger = Logger.getLogger(className);
        }
        return logger;
    }

    private static Logger logging = Logger.getLogger(JinLog.class.getName());
}
