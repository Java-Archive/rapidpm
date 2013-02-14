package org.rapidpm.lang;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 09:55
 */
public class PackageClassLoaderTest {
    @Test
    public void testGetClassesWithAnnotation() throws Exception {
        final PackageClassLoader classLoader = new PackageClassLoader();
        final Class<? extends Annotation> annotation = Deprecated.class;
        final List<Class> classes = classLoader.getClassesWithAnnotation("de.RapidPM", annotation);
        for (final Class aClass : classes) {
            System.out.println(aClass);
            assertTrue(aClass.isAnnotationPresent(annotation));
        }
    }

    @Test
    public void testGetClasses() throws Exception {
        final PackageClassLoader classLoader = new PackageClassLoader();
        final List<Class> classes = classLoader.getClasses("de.RapidPM");
        for (final Class aClass : classes) {
            System.out.println(aClass);
        }
    }
}
