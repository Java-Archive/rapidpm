package org.rapidpm.tools; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.11.11
 * Time: 09:57
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.Constants;
import org.rapidpm.lang.PackageClassLoader;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ClassListGenerator {
    private static final Logger logger = Logger.getLogger(ClassListGenerator.class);

    public static void main(String[] args) throws IOException {
        final PackageClassLoader pcl = new PackageClassLoader();
        final List<Class> classes = pcl.getClasses("de.neoscio");
        final FileWriter fw = new FileWriter("classlist.txt");
        for (final Class aClass : classes) {
            System.out.println("aClass.getName() = " + aClass.getName());
            fw.write(aClass.getName() + Constants.LINE_BREAK);
        }
        fw.flush();
        fw.close();
    }


}