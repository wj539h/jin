package com.jin.log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLog {

    private static Logger fileLogger;

    static {
	fileLogger = Logger.getLogger("com.ckcs.log.customlog");
	fileLogger.setLevel(Level.INFO);
	Handler[] hs = fileLogger.getHandlers();
	for (Handler h : hs) {
	    h.close();
	    fileLogger.removeHandler(h);
	}
	try {
	    //文件 日志文件名为mylog 日志最大写入为4000个字节 保存5天内的日志文件 如果文件没有达到规定大小则将日志文件添加到已有文件
	    CustomFileStreamHandler fh = new CustomFileStreamHandler("d:/log/mylog", 4000, 5, true);
	    //CustomFileStreamHandler fh = new CustomFileStreamHandler("d:/log/mylog/", 1024, 5, true); //目录
	    fh.setEncoding("UTF-8");
	    fh.setFormatter(new CustomFormatter());
	    fileLogger.setUseParentHandlers(false);
	    fileLogger.addHandler(fh);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    /**
     * Creates a new instance of MyLog
     */
    private MyLog() {
    }

    /**
     * 返回一个文件记录实例
     */
    public static synchronized Logger getFileLogger() {
	return fileLogger;
    }

    public static void main(String[] args) {
	for (int i = 0; i < 10; i++) {
	    fileLogger.log(Level.INFO, "我被记录了吗?");
	}
    }
}
