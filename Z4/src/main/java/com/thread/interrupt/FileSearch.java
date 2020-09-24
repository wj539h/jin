package com.thread.interrupt;

import java.io.File;
import java.util.concurrent.TimeUnit;

//例子5：一个interrupt在搜索文件的小应用中是如何使用的

public class FileSearch implements Runnable {
    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
	this.initPath = initPath;
	this.fileName = fileName;
    }

    @Override
    public void run() {
	File file = new File(initPath);
	if (file.isDirectory()) {
	    try {
		directoryProcess(file);
	    } catch (InterruptedException e) {
		System.out.printf("%s: The search has been interrupted", Thread.currentThread().getName());
	    }
	}
    }

    private void directoryProcess(File file) throws InterruptedException {
	//System.out.println(file.getAbsolutePath());
	File[] list = file.listFiles();
	if (list != null) {
	    for (int i = 0; i < list.length; i++) {
		if (list[i].isDirectory()) {
		    directoryProcess(list[i]);
		} else {
		    fileProcess(list[i]);
		}
	    }
	}
	if (Thread.interrupted()) {
	    throw new InterruptedException();
	}
    }

    private void fileProcess(File file) throws InterruptedException {
	if (file.getName().equals(fileName)) {
	    System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
	}
	if (Thread.interrupted()) {
	    throw new InterruptedException();
	}
    }

    /**
     * Main method of the core. Search for the autoexect.bat file on the Windows root folder and its
     * subfolders during ten seconds and then, interrupts the Thread
     * 
     * @param args
     */
    public static void main(String[] args) {
	// Creates the Runnable object and the Thread to run it
	FileSearch searcher = new FileSearch("C:", "99.txt");
	Thread thread = new Thread(searcher);

	// Starts the Thread
	thread.start();

	// Wait for 3 seconds
	try {
	    TimeUnit.SECONDS.sleep(1);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	// Interrupts the thread
	System.out.println(thread.isInterrupted());
	thread.interrupt();
	System.out.println(thread.isInterrupted());
    }
}
