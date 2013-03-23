package org.rapidpm.lang;

import org.junit.Assert;
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
        final List<Class> classes = classLoader.getClassesWithAnnotation("org.rapidpm", annotation);
        for (final Class aClass : classes) {
            System.out.println(aClass);
            assertTrue(aClass.isAnnotationPresent(annotation));
        }
    }

    @Test
    public void testGetClasses() throws Exception {
        final PackageClassLoader classLoader = new PackageClassLoader();
        final List<Class> classes = classLoader.getClasses("org.rapidpm");
        Assert.assertNotNull(classes);
        Assert.assertFalse(classes.isEmpty());
        for (final Class aClass : classes) {
            final String name = aClass.getName();
            Assert.assertTrue(name.startsWith("org.rapidpm"));
            if(!name.endsWith("Test") && !name.endsWith("Factory") && !name.contains("$") && !name.contains("DAO")&& !name.contains("annotations") ){

                if (name.startsWith("org.rapidpm.persistence")) System.out.println("<class>"+name+"</class>");
            }
        }
    }
}
