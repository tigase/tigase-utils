/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2018 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

package tigase.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

class StatisticsAnalyser {

	private final static Logger log = Logger.getLogger(StatisticsAnalyser.class.getName());

	public static void main(String[] args) {

		if (args.length < 1 || args.length > 2) {
			System.out.println("Wrong parameter list (either 1 or 2 supported (path + filter)");
		}

		System.out.println("Using path: " + args[0]);

		final Path directoryPath = Paths.get(args[0]);
		final File directory = directoryPath.toFile();
		File[] files;
		if (args.length == 2) {
			System.out.println("Filtering files with prefix: " + args[1]);
			files = directory.listFiles((dir, name) -> name.startsWith(args[1]));
		} else {
			files = directory.listFiles();
		}

		Map<String, Map<String, String>> stats = new TreeMap<>();

		System.out.println("Files: " + files.length);
		for (File file : files) {
			List<String> lines = null;
			try {
				lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			} catch (IOException e) {
				System.out.println("File: " + file.toPath());
				e.printStackTrace();
			}

			// map statistic metrics to file/value pair
			for (String line : lines) {
				String[] keyval;
				if (!line.contains("Statistics time")) {
					keyval = line.split("(\\t+|\\s{2,})");
					if (keyval.length != 2) {
						System.out.println(line + " : " + Arrays.toString(keyval));
					}
					Map<String, String> vals = stats.computeIfAbsent(keyval[0], k -> new TreeMap<>());
					try {
						vals.put(file.getName(), keyval[1]);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(line);
						System.out.println(Arrays.toString(keyval));
					}
				}
			}
		}

		System.out.println("Statistics count: " + stats.size());

		final File outputFile = new File(directoryPath + "/statisticsSeries.txt");
		System.out.println("Writing to: " + outputFile.getAbsolutePath());
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, Map<String, String>> stringMapEntry : stats.entrySet()) {
			final Long count = stringMapEntry.getValue().values().stream().distinct().count();
//			if (count > 1 && count/(double)stringMapEntry.getValue().size() < 0.2) {
			if (count > 1) {
				if (pw != null) {
					pw.println();
				} else {
					System.out.println();
				}
				Long previousValue = null;
				for (Map.Entry<String, String> stringStringEntry : stringMapEntry.getValue().entrySet()) {
					Long diff = null;
					try {
						final Long aLong = Long.valueOf(stringStringEntry.getValue());
						if (previousValue != null) {
							diff = aLong - previousValue;
						}
						previousValue = aLong;
					} catch (Exception e) {
						// just ignore
					}

					final String x = stringMapEntry.getKey() + " | " + stringStringEntry.getKey() + " ~ " +
							stringStringEntry.getValue() + (diff != null ? " / " + diff : "");
					if (pw != null) {
						pw.println(x);
					} else {
						System.out.println(x);
					}
				}
			}
		}
	}
}