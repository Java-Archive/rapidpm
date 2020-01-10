/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.lang;
/**
 * RapidPM
 * User: svenruppert
 * Date: 07.04.2010
 * Time: 23:20:02
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class PackageClassLoader {

    public static final String CLASSLIST_FILE = "classlist.txt";

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages
     * and are annotated by the given annotation.
     *
     * @param packageName The base package
     * @param annotation  The annotation
     * @return The classes
     */
    public List<Class> getClassesWithAnnotation(final String packageName, final Class<? extends Annotation> annotation) {
        final List<Class> classList = new ArrayList<>();
        final List<Class> classes = getClasses(packageName);
        for (final Class aClass : classes) {
            if (aClass.isAnnotationPresent(annotation)) {
                classList.add(aClass);
            } else {
                //
            }
        }
        return classList;
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws java.io.IOException if an io-error occurs.
     */
    public List<Class> getClasses(final String packageName) {
        final ClassLoader startClassLoader = Thread.currentThread().getContextClassLoader();
        //        final ClassLoader startClassLoader = ClassLoader..getContextClassLoader();
        final List<Class> classes = new ArrayList<>();
        final String path = packageName.replace('.', '/');

        assert startClassLoader != null;
        final Set<ClassLoader> classLoaderSet = new HashSet<>();
        classLoaderSet.add(startClassLoader);

        ClassLoader childClassLoader = startClassLoader;

        while (childClassLoader.getParent() != null) {
            final ClassLoader parent = childClassLoader.getParent();
            if (parent != null) {
                classLoaderSet.add(parent);
                childClassLoader = parent;
            } else {
            }
        }

        final List<File> dirs = new ArrayList<>();
        final List<File> jars = new ArrayList<>();

        for (final ClassLoader loader : classLoaderSet) {
            try {
                final Enumeration<URL> resources = loader.getResources(path);
                while (resources.hasMoreElements()) {
                    final URL resource = resources.nextElement();
                    //                final String fileName = resource.getFile();
                    final String fileName = resource.getFile().replace("%20", " "); // REFAC Leerzeichen im Pfad (%20)!?
                    if (fileName.contains(".jar")) {
                        final String jarPath = resource.getPath();
                        final String jarFileName = jarPath.split(".jar")[0] + ".jar";
                        jars.add(new File(jarFileName.replace("file:", ".")));
                    } else {
                        dirs.add(new File(fileName));
                    }
                }
            } catch (IOException e) {
            }


            for (final File directory : dirs) {
                final List<Class> list = findClassesInDirectories(directory, packageName);
                classes.addAll(list);
            }

            for (final File jar : jars) {
                findClassesInJars(classLoaderSet, classes, path, jar);
            }

            try {
                final InputStream resourceAsStream = startClassLoader.getResourceAsStream(CLASSLIST_FILE);
                if (resourceAsStream == null) {
                } else {
                    final BufferedReader bis = new BufferedReader(new InputStreamReader(resourceAsStream));

                    String line = bis.readLine();
                    while (line != null) {
                        try {
                            final Class<?> aClass = startClassLoader.loadClass(line);
                            classes.add(aClass);
                        } catch (ClassNotFoundException e) {
                        }
                        line = bis.readLine();
                    }

                }
            } catch (IOException e) {
            }
        }


        return classes;
    }

    private void findClassesInJars(final Set<ClassLoader> classLoaderSet, final List<Class> classes, final String path, final File jar) {
        try {
            final ZipFile zipFile = new ZipFile(jar);
            //        final Enumeration<ZipEntry> entries = zipFile.entries();
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = entries.nextElement();
                final String name = zipEntry.getName();
                if (name.contains(path) && name.endsWith(".class")) {
                    try {
                        for (final ClassLoader classLoader : classLoaderSet) {
                            final String nameForLoading = name.replace("/", ".").replace(".class", "");
                            final Class<?> aClass = classLoader.loadClass(nameForLoading);
                            classes.add(aClass);
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        } catch (ZipException e) {
        } catch (IOException e) {
        }
    }


    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private List<Class> findClassesInDirectories(final File directory, final String packageName) {
        final List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {

        } else {
            final File[] files = directory.listFiles();
            for (final File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClassesInDirectories(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    try {
                        final String classNameForLoading = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        final ClassLoader startClassLoader = Thread.currentThread().getContextClassLoader();
                        //                        final Class<?> aClass = Class.forName(classNameForLoading);
                        final Class<?> aClass = startClassLoader.loadClass(classNameForLoading);
                        classes.add(aClass);
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        }
        return classes;
    }

}
