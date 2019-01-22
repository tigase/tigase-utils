/**
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
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
package tigase.annotations;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import tigase.util.ClassUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by andrzej on 15.07.2017.
 */
public class DeprecationTest {

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	private VersionBiggerMatcher versionMatcher;

	@Test
	public void test() throws IOException, ClassNotFoundException {
		String version = System.getProperty("project.version");
		versionMatcher = new VersionBiggerMatcher(Version.parse(version).get());
		Set<String> classNames = ClassUtil.getClassNamesFromDir(new File(System.getProperty("project.target")));
		Set<Class<?>> classes = ClassUtil.getClassesFromNames(classNames);

		classes.stream().forEach(this::checkClass);
		Assert.assertNotSame("Classes not found!", 0, classes.size());
	}

	private void checkClass(Class<?> cls) {
		checkClassAnnotations(cls);
		Arrays.stream(cls.getDeclaredFields()).forEach(this::checkFieldAnnotations);
		Set<Class<?>> interfaces = new HashSet<>();
		Class tmp = cls;
		while (tmp != null) {
			Class<?>[] tmpInterfaces = tmp.getInterfaces();
			if (tmpInterfaces != null) {
				interfaces.addAll(Arrays.asList(tmpInterfaces));
			}
			tmp = tmp.getSuperclass();
		}
		Arrays.stream(cls.getDeclaredMethods()).forEach(method -> checkMethodAnnotations(method, interfaces));
	}

	private void checkClassAnnotations(Class<?> clazz) {
		TigaseDeprecated tigaseDeprecated = clazz.getAnnotation(TigaseDeprecated.class);
		boolean markedAsDeprecated = clazz.isAnnotationPresent(Deprecated.class);
		if (markedAsDeprecated) {
			collector.checkThat("Class " + clazz + " missing @TigaseDeprecated annotation", tigaseDeprecated,
								CoreMatchers.notNullValue());
		} else {
			collector.checkThat("Class " + clazz + " missing @Deprecated annotation", tigaseDeprecated,
								CoreMatchers.nullValue());
		}
		if (tigaseDeprecated != null) {
			Version removeIn = Version.parse(tigaseDeprecated.removeIn())
					.orElseGet(() -> Version.parse(tigaseDeprecated.since()).get().nextMinor());
			collector.checkThat("Class " + clazz + " should be removed in this version, deprecated since " +
										tigaseDeprecated.since(), removeIn, versionMatcher);
		}
	}

	private void checkFieldAnnotations(Field field) {
		TigaseDeprecated tigaseDeprecated = field.getAnnotation(TigaseDeprecated.class);
		boolean markedAsDeprecated = field.isAnnotationPresent(Deprecated.class);
		if (markedAsDeprecated) {
			if (tigaseDeprecated == null) {
				tigaseDeprecated = field.getDeclaringClass().getAnnotation(TigaseDeprecated.class);
			}
			collector.checkThat("Field " + field + " missing @TigaseDeprecated annotation", tigaseDeprecated,
								CoreMatchers.notNullValue());
		} else {
			collector.checkThat("Field " + field + " missing @Deprecated annotation", tigaseDeprecated,
								CoreMatchers.nullValue());
		}
		if (tigaseDeprecated != null) {
			String since = tigaseDeprecated.since();
			Version removeIn = Version.parse(tigaseDeprecated.removeIn())
					.orElseGet(() -> Version.parse(since).get().nextMinor());
			collector.checkThat("Field " + field + " should be removed in this version, deprecated since " +
										tigaseDeprecated.since(), removeIn, versionMatcher);
		}
	}

	private void checkMethodAnnotations(Method method, Set<Class<?>> interfaces) {
		TigaseDeprecated tigaseDeprecated = method.getAnnotation(TigaseDeprecated.class);
		boolean markedAsDeprecated = method.isAnnotationPresent(Deprecated.class);
		if (markedAsDeprecated) {
			if (tigaseDeprecated == null) {
				tigaseDeprecated = findTigaseDeprecationAnnotationForMethod(method, interfaces);
//				for (Class<?> ifc : interfaces) {
//					try {
//						Method ifcMethod = ifc.getDeclaredMethod(method.getName(), method.getParameterTypes());
//						tigaseDeprecated = ifcMethod.getAnnotation(TigaseDeprecated.class);
//						break;
//					} catch (NoSuchMethodException | SecurityException ex) {
//						// ignoring excetion
//					}
//				}
			}
			collector.checkThat("Method " + method + " missing @TigaseDeprecated annotation", tigaseDeprecated,
								CoreMatchers.notNullValue());
		} else {
			collector.checkThat("Method " + method + " missing @Deprecated annotation", tigaseDeprecated,
								CoreMatchers.nullValue());
		}
		if (tigaseDeprecated != null) {
			String since = tigaseDeprecated.since();
			Version removeIn = Version.parse(tigaseDeprecated.removeIn())
					.orElseGet(() -> Version.parse(since).get().nextMinor());
			collector.checkThat("Method " + method + " should be removed in this version, deprecated since " +
										tigaseDeprecated.since(), removeIn, versionMatcher);
		}

	}

	private TigaseDeprecated findTigaseDeprecationAnnotationForMethod(Method method, Collection<Class<?>> interfaces) {
		TigaseDeprecated tigaseDeprecated = null;
		for (Class<?> ifc : interfaces) {
			try {
				Method ifcMethod = ifc.getDeclaredMethod(method.getName(), method.getParameterTypes());
				tigaseDeprecated = ifcMethod.getAnnotation(TigaseDeprecated.class);
				if (tigaseDeprecated == null) {
					Class<?>[] tmp = ifc.getInterfaces();
					if (tmp != null) {
						tigaseDeprecated = findTigaseDeprecationAnnotationForMethod(method, Arrays.asList(tmp));
					}
				}
				if (tigaseDeprecated != null) {
					return tigaseDeprecated;
				}
			} catch (NoSuchMethodException | SecurityException ex) {
				// ignoring excetion
			}
			try {
				Method ifcMethod = ifc.getMethod(method.getName(), method.getParameterTypes());
				tigaseDeprecated = ifcMethod.getAnnotation(TigaseDeprecated.class);
				if (tigaseDeprecated == null) {
					Class<?>[] tmp = ifc.getInterfaces();
					if (tmp != null) {
						tigaseDeprecated = findTigaseDeprecationAnnotationForMethod(method, Arrays.asList(tmp));
					}
				}
				if (tigaseDeprecated != null) {
					return tigaseDeprecated;
				}
			} catch (NoSuchMethodException | SecurityException ex) {
				// ignoring excetion
			}
		}
		return null;
	}

	public static class Version
			implements Comparable<Version> {

		private final int major;
		private final int minor;
		private final int release;

		public static Optional<Version> parse(String version) {
			if (version == null || version.isEmpty()) {
				return Optional.empty();
			}
			int idx = version.indexOf("-");
			if (idx >= 0) {
				version = version.substring(0, idx);
			}
			String[] parts = version.split("\\.");
			int major = Integer.parseInt(parts[0]);
			int minor = Integer.parseInt(parts[1]);
			int release = Integer.parseInt(parts[2]);

			return Optional.of(new Version(major, minor, release));
		}

		private Version(int major, int minor, int release) {
			this.major = major;
			this.minor = minor;
			this.release = release;
		}

		@Override
		public String toString() {
			return "" + major + "." + minor + "." + release;
		}

		public Version nextMinor() {
			return new Version(major, minor + 1, 0);
		}

		@Override
		public int compareTo(Version o) {
			return Long.compare(toLong(), o.toLong());
		}

		private long toLong() {
			return ((major * 100) + minor) * 100 + release;
		}
	}

	public static class VersionBiggerMatcher
			extends BaseMatcher<Version> {

		private final Version version;

		public VersionBiggerMatcher(Version version) {
			this.version = version;
		}

		@Override
		public boolean matches(Object o) {
			if (o instanceof Version) {
				return version.compareTo((Version) o) < 0;
			}
			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("version bigger than ").appendValue(version);
		}
	}

	public static class VersionMatcher
			extends BaseMatcher<String> {

		private final int major;
		private final int minor;
		private final int release;
		private final String version;

		public VersionMatcher(String version) {
			int idx = version.indexOf("-");
			if (idx >= 0) {
				version = version.substring(0, idx);
			}
			this.version = version;
			String[] parts = version.split("\\.");
			major = Integer.parseInt(parts[0]);
			minor = Integer.parseInt(parts[1]);
			release = Integer.parseInt(parts[2]);
		}

		@Override
		public boolean matches(Object o) {
			if (o instanceof String) {
				String version = (String) o;
				int idx = version.indexOf("-");
				if (idx >= 0) {
					version = version.substring(0, idx);
				}
				String[] parts = version.split("\\.");
				int major = Integer.parseInt(parts[0]);
				int minor = Integer.parseInt(parts[1]);
				int release = Integer.parseInt(parts[2]);
				if (this.major < major) {
					return true;
				}
				if (this.major == major) {
					if (this.minor <= minor) {
						return true;
					}
				}
				return false;
			}
			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendValue(version);
		}
	}

}
