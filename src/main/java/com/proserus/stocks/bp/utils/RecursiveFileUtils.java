package com.proserus.stocks.bp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileUtils {

	private static final String DATA_FOLDER = "data";

	public static List<File> listFiles(File path, int i, String... filters) {
		List<File> files = new ArrayList<File>();
		if (i > 0) {
			for (File f : path.listFiles()) {
				if (f.isDirectory()) {
					if (isNotHiddenFolder(f) || isAnyFolderContaingWordData(f)) {
						files.addAll(listFiles(f, i - 1, filters));
					}
				} else if (filters.length == 0) {
					files.add(f);
				} else {
					for (String filter : filters) {
						if (f.getAbsolutePath().endsWith(filter)) {
							files.add(f);
						}
					}

				}
			}
		}
		return files;
	}

	private static boolean isNotHiddenFolder(File f) {
		return f.isDirectory() && !f.isHidden();
	}

	private static boolean isAnyFolderContaingWordData(File f) {
		return f.isDirectory() && f.getName().contains(DATA_FOLDER);
	}

}
