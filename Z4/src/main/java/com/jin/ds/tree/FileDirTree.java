package com.jin.ds.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDirTree {
	private String s = " ";

	private String genSpace(int level) {
		String resu = "";
		for (int i = 0; i < level; i++) {
			resu += "  ";
		}
		return resu;
	}

	public void readFileDir(File f, int level) {
		String outStr = "";
		if (f.isDirectory()) {
			outStr = genSpace(level) + "+" + f.getPath();
			System.out.println(outStr);
			level++;
			for (File fs : f.listFiles()) {
				readFileDir(fs, level);
			}
		} else {
			outStr = genSpace(level) + "-" + f.getPath();
			System.out.println(outStr);
		}
	}

	public void readFileDirNoRecur(File f) {
		int level = 0;
		String outStr = genSpace(level) + "+" + f.getPath();
		List<String> l = new ArrayList<String>();
		l.add(outStr);
		while (true) {
			if (f.isDirectory()) {
				for (File fs : f.listFiles()) {

				}
			} else {

			}
		}
	}

	public static void main(String[] args) {
		File f = new File("d:/asm");
		FileDirTree fdt = new FileDirTree();
		fdt.readFileDir(f, 0);
	}

}
