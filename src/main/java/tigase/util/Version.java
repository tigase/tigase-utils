/*
 * Tigase Jabber/XMPP Server
 *  Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License,
 *  or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program. Look for COPYING file in the top folder.
 *  If not, see http://www.gnu.org/licenses/.
 *
 */

package tigase.util;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version
		implements Comparable<Version> {

	private static final Comparator<Version> versionComparator = Comparator.comparingInt(Version::getMajor)
			.thenComparingInt(Version::getMinor)
			.thenComparingInt(Version::getBugfix)
			.thenComparing(Version::getVersionType)
			.thenComparingInt(Version::getTypeNumber)
			.thenComparingInt(Version::getBuild);
	private static final Logger log = Logger.getLogger("tigase.util.updater.UpdatesChecker");
	private static final Pattern PATTERN = Pattern.compile(
			"(.*?)-?((\\d{1,20}\\.){2}\\d{1,20})(-(SNAPSHOT|RC|BETA)(\\d*))?(-b(\\d{1,50})(/([0-9a-f]{4,16}))?)?",
			Pattern.CASE_INSENSITIVE);
	public static final Version ZERO = Version.of("0.0.0-b0000");

	public enum TYPE {
		SNAPSHOT("-SNAPSHOT"),
		BETA("-BETA"),
		RC("-RC"),
		FINAL("");

		private String id;

		TYPE(String s) {
			id = s;
		}

		public String getId() {
			return id;
		}
	}

	private final int bugfix;
	private final int build;
	private final String commit;
	private final String component;
	private final int major;
	private final int minor;
	private final int typeNumber;
	private final TYPE versionType;

	/**
	 * Supports both tigase-server-7.2.0-SNAPSHOT-b4895-dist-max.tar.gz and version strings
	 *
	 * @param str string to be parsed. Must match the supported formats
	 *
	 * @return a Version object based on the provided string.
	 *
	 * @throws IllegalArgumentException when provided input doesn't match supported formats
	 */
	public static Version of(String str) throws IllegalArgumentException {

		String component = null;
		int major = 0;
		int minor = 0;
		int bugfix = 0;
		int build = -1;
		String commit = null;
		TYPE versionType = TYPE.FINAL;
		int typeNumber = -1;

		final Matcher matcher = PATTERN.matcher(str);
		if (matcher.find()) {
			String mainVersionPart = null;
			String buildStr = null;
			String typeNumberStr = null;
			switch (matcher.groupCount()) {
				case 10:
					commit = matcher.group(10);
				case 8:
					if (matcher.group(8) != null) {
						buildStr = matcher.group(8);
					}
				case 6:
					if (matcher.group(6) != null && !matcher.group(6).trim().isEmpty()) {
						typeNumberStr = matcher.group(6);
					}
				case 5:
					if (matcher.group(5) != null) {
						versionType = TYPE.valueOf(matcher.group(5).toUpperCase());
					}
				case 2:
					mainVersionPart = matcher.group(2);

				case 1:
					component = matcher.group(1);
			}

			try {
				if (mainVersionPart != null) {
					String[] versionParts = mainVersionPart.split("\\.");

					switch (versionParts.length) {
						case 3:
							bugfix = Integer.parseInt(versionParts[2]);
						case 2:
							minor = Integer.parseInt(versionParts[1]);
						case 1:
							major = Integer.parseInt(versionParts[0]);
					}

					if (buildStr != null && !buildStr.trim().isEmpty()) {
						build = Integer.valueOf(buildStr);
					}
					if (typeNumberStr != null && !typeNumberStr.trim().isEmpty()) {
						typeNumber = Integer.valueOf(typeNumberStr);
					}
				}
			} catch (NumberFormatException e) {
				log.warning("Can not detect the server version.... " + str);
			} catch (Exception e) {
				log.log(Level.WARNING, "Problem parsing server version.... " + str, e);
			}

		} else {
			throw new IllegalArgumentException("Wrong Version format provided");
		}
		return new Version(component, versionType, major, minor, bugfix, build, typeNumber, commit);
	}

	private Version(Builder builder) {
		this.component = builder.component;
		this.versionType = builder.versionType;
		this.typeNumber = builder.typeNumber;
		this.major = builder.major;
		this.minor = builder.minor;
		this.bugfix = builder.bugfix;
		this.build = builder.build;
		this.commit = builder.commit;
	}

	private Version(String component, TYPE type, int major, int minor, int bugfix, int build, int typeNumber,
	                String commit) {
		this.component = component;
		this.typeNumber = typeNumber;
		this.versionType = type;
		this.major = major;
		this.minor = minor;
		this.bugfix = bugfix;
		this.build = build;
		this.commit = commit;
	}

	@Override
	public int compareTo(Version that) {
		return versionComparator.compare(this, that);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Version version = (Version) o;

		if (major != version.major) {
			return false;
		}
		if (minor != version.minor) {
			return false;
		}
		if (bugfix != version.bugfix) {
			return false;
		}
		if (versionType != version.versionType) {
			return false;
		}
		if (typeNumber != version.typeNumber) {
			return false;
		}
		if (build != version.build) {
			return false;
		}
		return (commit != null ? !commit.equals(version.commit) : version.commit != null);
	}

	public int getBugfix() {
		return bugfix;
	}

	public int getBuild() {
		return build;
	}

	public String getCommit() {
		return commit;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getTypeNumber() {
		return typeNumber;
	}

	public TYPE getVersionType() {
		return versionType;
	}

	@Override
	public int hashCode() {
		int result = bugfix;
		result = 31 * result + build;
		result = 31 * result + (commit != null ? commit.hashCode() : 0);
		result = 31 * result + major;
		result = 31 * result + minor;
		result = 31 * result + (versionType != null ? versionType.hashCode() : 0);
		return result;
	}

	public boolean isZero() {
		return major == 0 && minor == 0 && bugfix == 0 && build == 0;
	}

	@Override
	public String toString() {
		return String.format("%1$s.%2$s.%3$s%4$s%5$s%6$s", major, minor, bugfix,
		                     versionType != null ? versionType.getId() + (typeNumber > 0 ? typeNumber : "") : "",
		                     build > 0 ? ("-b" + build) : "", commit != null ? ("/" + commit) : "");
	}

	public String toString(int padding) {
		return String.format("%1$s.%2$s.%3$s%4$s%5$s%6$s", String.format("%0" + padding + "d", major),
		                     String.format("%0" + padding + "d", minor), String.format("%0" + padding + "d", bugfix),
		                     versionType != null ? versionType.getId() + (typeNumber > 0 ? typeNumber : "") : "",
		                     build > 0 ? ("-b" + String.format("%0" + 3 * padding + "d", build)) : "",
		                     commit != null ? ("/" + commit) : "");
	}

	static class Builder {

		private int bugfix = 0;
		private int build = -1;
		private String commit = null;
		private String component = null;
		private int major = 0;
		private int minor = 0;
		private int typeNumber = -1;
		private TYPE versionType = TYPE.FINAL;

		public Builder(int major, int minor, int bugfix) {
			this.major = major;
			this.minor = minor;
			this.bugfix = bugfix;
		}

		public Version build() {
			return new Version(this);
		}

		public Builder setBuild(int build) {
			this.build = build;
			return this;
		}

		public Builder setCommit(String commit) {
			this.commit = commit;
			return this;
		}

		public Builder setVersionType(TYPE versionType) {
			this.versionType = versionType;
			return this;
		}
	}
}
