.. java:import:: java.io File

.. java:import:: java.io IOException

.. java:import:: java.lang.reflect Modifier

.. java:import:: java.util.function Predicate

.. java:import:: java.util.jar JarEntry

.. java:import:: java.util.jar JarFile

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

ClassUtil
=========

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class ClassUtil

   \ ``ClassUtil``\  file contains code used for loading all implementations of specified \ *interface*\  or \ *abstract class*\  found in classpath. As a result of calling some functions you can have \ ``Set``\  containing all required classes.

   Created: Wed Oct 6 08:25:52 2004

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Methods
-------
getClassNameFromFileName
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String getClassNameFromFileName(String fileName)
   :outertype: ClassUtil

getClassNamesFromDir
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<String> getClassNamesFromDir(File dir)
   :outertype: ClassUtil

getClassNamesFromJar
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<String> getClassNamesFromJar(File jarFile) throws IOException
   :outertype: ClassUtil

getClassesFromClassPath
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<Class<?>> getClassesFromClassPath() throws IOException, ClassNotFoundException
   :outertype: ClassUtil

getClassesFromClassPath
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<Class<?>> getClassesFromClassPath(Predicate<String> classNamesFilter) throws IOException, ClassNotFoundException
   :outertype: ClassUtil

getClassesFromNames
^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<Class<?>> getClassesFromNames(Set<String> names) throws ClassNotFoundException
   :outertype: ClassUtil

getClassesFromNames
^^^^^^^^^^^^^^^^^^^

.. java:method:: public static Set<Class<?>> getClassesFromNames(Set<String> names, Predicate<String> filter) throws ClassNotFoundException
   :outertype: ClassUtil

getClassesImplementing
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @SuppressWarnings public static <T extends Class> Set<T> getClassesImplementing(Collection<Class<?>> classes, T cls)
   :outertype: ClassUtil

getClassesImplementing
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static <T extends Class> Set<T> getClassesImplementing(T cls) throws IOException, ClassNotFoundException
   :outertype: ClassUtil

getFileListDeep
^^^^^^^^^^^^^^^

.. java:method:: public static Set<String> getFileListDeep(File path)
   :outertype: ClassUtil

getImplementations
^^^^^^^^^^^^^^^^^^

.. java:method:: @SuppressWarnings public static <T> Set<T> getImplementations(Class<T> obj) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
   :outertype: ClassUtil

walkInDirForFiles
^^^^^^^^^^^^^^^^^

.. java:method:: public static void walkInDirForFiles(File base_dir, String path, Set<String> set)
   :outertype: ClassUtil

