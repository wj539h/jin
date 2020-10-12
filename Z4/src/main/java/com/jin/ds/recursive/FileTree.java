package com.jin.ds.recursive;

import java.io.File;
import java.util.Stack;

public class FileTree {
    public static final String SPACE = "  ";
    public static final String ROOT_LOCATION = "e:\\Install_Files";

    public static void main(String[] args) {

        FileTree fileTree = new FileTree();
        fileTree.displayNoRecur(new File(ROOT_LOCATION));
        System.out.println("------------------------------------------------------------------------");
        fileTree.displayRecur(new File(ROOT_LOCATION), 0);
    }

    public void displayRecur(File f, int level) {
        String prefix = "";
        for (int i = 0; i < level; i++) {
            prefix += SPACE;
        }
        String fileName = f.getName();
        if (f.isDirectory()) {
            fileName = "+" + fileName;
            System.out.println(prefix + fileName);
            File fArr[] = f.listFiles();
            level++;
            for (int i = 0; i < fArr.length; i++) {
                displayRecur(fArr[i], level);
            }
        } else {
            fileName = "-" + fileName;
            System.out.println(prefix + fileName);
        }
    }

    public void displayNoRecur(File file) {
	/*
+Install_Files
  -7z1900-x64.msi
  -BaiduNetdisk_6.8.2.21.exe
  +TeamViewer
    -AlterID.exe
    -TeamViewer14（安装的时候选择个人版_要不出问题）.exe
    -TeamViewer_Setup.exe
  -ultraedit.zip
  -ZoomInstaller.exe
	 */
        Stack<File> s = new Stack<File>();
        Stack<Integer> levelStack = new Stack<Integer>();
        s.push(file);
        int level = 0;
        levelStack.push(level);
        while (!s.empty()) {
            String prefix = "";
            level = levelStack.pop();
            for (int j = 0; j < level; j++) {
                prefix += SPACE;
            }
            file = s.pop();
            String fileName = file.getName();
            if (file.isDirectory()) {
                fileName = "+" + fileName;
                File fArr[] = file.listFiles();
                level++;
                for (int i = 0; i < fArr.length; i++) {
                    file = fArr[i];
                    s.push(file);
                    levelStack.push(level);
                }
            } else {
                fileName = "-" + fileName;
            }
            System.out.println(prefix + fileName);
        }

    }
}
