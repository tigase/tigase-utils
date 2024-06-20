/*
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
package tigase.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <code>ClassUtil</code> file contains code used for loading all implementations of specified <em>interface</em> or
 * <em>abstract class</em> found in classpath. As a result of calling some functions you can have <code>Set</code>
 * containing all required classes.
 * <br>
 * <p> Created: Wed Oct 6 08:25:52 2004 </p>
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class ClassUtil {

	private static final Logger log = Logger.getLogger(ClassUtil.class.getName());

	private static final String[] SKIP_WHITELIST = {"tigase."};
	private static final String[] SKIP_CONTAINS = {".ui.", ".swing", ".awt", ".sql.", ".xml.", ".terracotta."};

	private static final String META_VERSION_PREFIX = "META-INF.versions.";

	private static final String[] SKIP_STARTS = {"com.mysql", "tigase.pubsub.Utils", "org.apache.derby",
												 "org.apache.xml", "org.postgresql", "com.sun", "groovy",
												 "org.codehaus.groovy", "org.netbeans", "org.python"};

	private static final int RUNTIME_FEATURE_VERSION = Runtime.version().feature();

	public static String getClassNameFromFileName(String fileName) {
		String class_name = null;

		if (fileName.endsWith(".class")) {

			// class_name = fileName.substring(0,
			// fileName.length()-6).replace(File.separatorChar, '.');
			// Above code does not works on MS Windows if we load
			// files from jar file. Jar manipulation code always returns
			// file names with unix style separators
			String tmp_class_name = fileName.substring(0, fileName.length() - 6).replace('\\', '.');

			class_name = tmp_class_name.replace('/', '.');
		} // end of if (entry_name.endsWith(".class"))

		return class_name;
	}

	public static Set<String> getClassNamesFromDir(File dir) {
		Set<String> tmp_set = getFileListDeep(dir);
		Set<String> result = new TreeSet<String>();

		for (String elem : tmp_set) {
			String class_name = getClassNameFromFileName(elem);

			if (class_name != null) {
				result.add(class_name);

				// System.out.println("class name: "+class_name);
			} // end of if (class_name != null)
		} // end of for ()

		return result;
	}

	public static Set<String> getClassNamesFromJar(File jarFile) throws IOException {
		Set<String> result = new TreeSet<String>();
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> jar_entries = jar.entries();

		while (jar_entries.hasMoreElements()) {
			JarEntry jar_entry = jar_entries.nextElement();
			String class_name = getClassNameFromFileName(jar_entry.getName());

			if (class_name != null) {
				result.add(class_name);

				// System.out.println("class name: "+class_name);
			} // end of if (entry_name.endsWith(".class"))
		} // end of while (jar_entries.hasMoreElements())

		return result;
	}

	public static Set<Class<?>> getClassesFromClassPath() throws IOException, ClassNotFoundException {
		return getClassesFromClassPath(ClassUtil::filterSkipStartWith);
	}
	
	public static Set<Class<?>> getClassesFromClassPath(Predicate<String> classNamesFilter) throws IOException, ClassNotFoundException {
		Set<Class<?>> classes_set = new TreeSet<Class<?>>(new ClassComparator());
		String classpath = System.getProperty("java.class.path");

		// System.out.println("classpath: "+classpath);
		StringTokenizer stok = new StringTokenizer(classpath, File.pathSeparator, false);

		while (stok.hasMoreTokens()) {
			String path = stok.nextToken();
			File file = new File(path);

			if (file.exists()) {
				if (file.isDirectory()) {

					// System.out.println("directory: "+path);
					Set<String> class_names = getClassNamesFromDir(file);

					classes_set.addAll(getClassesFromNames(class_names, classNamesFilter));
				} // end of if (file.isDirectory())

				if (file.isFile()) {

					// System.out.println("jar file: "+path);
					Set<String> class_names = getClassNamesFromJar(file);

					classes_set.addAll(getClassesFromNames(class_names, classNamesFilter));

					// System.out.println("Loaded jar file: "+path);
				} // end of if (file.isFile())
			} // end of if (file.exists())
		} // end of while (stok.hasMoreTokens())

		return classes_set;
	}

	public static Set<Class<?>> getClassesFromNames(Set<String> names) throws ClassNotFoundException {
		return getClassesFromNames(names, ClassUtil::filterSkipStartWith);
	}

	public static Set<Class<?>> getClassesFromNames(Set<String> names, Predicate<String> filter) throws ClassNotFoundException {
		Set<Class<?>> classes = new TreeSet<Class<?>>(new ClassComparator());

		for (String name : names) {
			try {
				boolean skip_class = false;

				for (String test_str : SKIP_CONTAINS) {
					skip_class = name.contains(test_str);

					if (skip_class) {
						break;
					}
				}
				
				if (!filter.test(name)) {
					skip_class = true;
				}

				for (String test_str : SKIP_WHITELIST) {
					if (name.startsWith(test_str)) {
						skip_class = false;
						break;
					}
				}

				if (!skip_class) {

					// System.out.println(new Date() + " - Class name: " + name);
					Class cls = Class.forName(name, false, ClassLoader.getSystemClassLoader());

					classes.add(cls);
				}
			} catch (UnsupportedClassVersionError e) {
				log.log(Level.WARNING, "Class: " + name + " compiled using newer JDK version. Please upgrade your JDK!");
			} catch (SecurityException e) {
			} catch (NoClassDefFoundError e) {
			} catch (UnsatisfiedLinkError e) {
			} catch (Throwable e) {
				Throwable cause = e.getCause();

				System.out.println("Class name: " + name);
				e.printStackTrace();

				if (cause != null) {
					cause.printStackTrace();
				}
			}
		} // end of for ()

		return classes;
	}

	@SuppressWarnings({"unchecked"})
	public static <T extends Class> Set<T> getClassesImplementing(Collection<Class<?>> classes, T cls) {
		Set<T> classes_set = new TreeSet<T>(new ClassComparator());

		for (Class c : classes) {

			// System.out.println("Checking class: " + c.getName());
			if (cls.isAssignableFrom(c)) {
				int mod = c.getModifiers();

				if (!Modifier.isAbstract(mod) && !Modifier.isInterface(mod)) {
					classes_set.add((T) c);
				} // end of if (!Modifier.isAbstract(mod) && !Modifier.isInterface(mod))
			} // end of if (cls.isAssignableFrom(c))
		} // end of for ()

		return classes_set;
	}

	public static <T extends Class> Set<T> getClassesImplementing(T cls) throws IOException, ClassNotFoundException {
		return getClassesImplementing(getClassesFromClassPath(), cls);
	}

	public static Set<String> getFileListDeep(File path) {
		Set<String> set = new TreeSet<String>();

		if (path.isDirectory()) {
			String[] files = path.list();

			for (String file : files) {
				walkInDirForFiles(path, file, set);
			} // end of for ()
		} else {
			set.add(path.toString());
		} // end of if (file.isDirectory()) else

		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> getImplementations(Class<T> obj)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Set<T> result = new TreeSet<T>(new ObjectComparator());

		for (Class cls : getClassesImplementing(obj)) {
			result.add((T) cls.newInstance());
		} // end of for ()

		return result;
	}

	public static void walkInDirForFiles(File base_dir, String path, Set<String> set) {
		File tmp_file = new File(base_dir, path);

		if (tmp_file.isDirectory()) {
			String[] files = tmp_file.list();

			for (String file : files) {
				walkInDirForFiles(base_dir, new File(path, file).toString(), set);
			} // end of for ()
		} else {

			// System.out.println("File: " + path.toString());
			set.add(path);
		} // end of if (file.isDirectory()) else
	}

	public static boolean filterIncorrectMultiVersionClasses(String name) {
		if (name.startsWith(META_VERSION_PREFIX)) {
			var prefixLength = META_VERSION_PREFIX.length();
			var version = name.substring(prefixLength, name.indexOf(".", prefixLength));
			return Integer.parseInt(version) > RUNTIME_FEATURE_VERSION;
		}
		return false;
	}

	private static boolean filterSkipStartWith(String name) {
		for (String test_str : SKIP_STARTS) {
			if (name.startsWith(test_str)) {
				return false;
			}
		}
		return true;
	}
}
