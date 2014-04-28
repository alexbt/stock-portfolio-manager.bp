package com.proserus.stocks.bp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileUtils {

	public static List<String> listFiles(File path, String filter, int i) {
		List<String> files = new ArrayList<String>();
		if (i > 0) {
			for (File f : path.listFiles()) {
				if (f.isDirectory()) {
					files.addAll(listFiles(f, filter, i - 1));
				} else if (f.getAbsolutePath().endsWith(filter)) {
					files.add(f.getAbsolutePath());
				}
			}
		}
		return files;
	}

}
