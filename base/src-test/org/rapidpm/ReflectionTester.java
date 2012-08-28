/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm;

import org.rapidpm.lang.PackageClassLoader;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.03.2010
 *        Time: 23:02:23
 */

public class ReflectionTester {
    private static final Logger logger = Logger.getLogger(ReflectionTester.class);


    public static void main(final String[] args) {
        final DemoA demoA = new DemoA();
//        final Class<? extends DemoA> aClass = demoA.getClass();
        final PackageClassLoader packageClassLoader = new PackageClassLoader();
        final List<Class> classes = packageClassLoader.getClassesWithAnnotation("org.rapidpm.stammdaten", Entity.class);
        for (final Class aClass : classes) {
            final String className = aClass.getName();
            System.out.println("aClass1.getName() = " + className);
        }
    }


    @Deprecated
    public static class DemoA {
        public DemoA() {
        }


        public DemoA(final String attributeString, final boolean attributeBoolean) {
            this.attributeString = attributeString;
            this.attributeBoolean = attributeBoolean;
        }

        @Deprecated
        private String attributeString;

        private boolean attributeBoolean;

        private List<String> stringListe;

        private Date datum;

        public List<String> getStringListe() {
            return stringListe;
        }

        public void setStringListe(final List<String> stringListe) {
            this.stringListe = stringListe;
        }

        public String getAttributeString() {
            return attributeString;
        }

        public void setAttributeString(final String attributeString) {
            this.attributeString = attributeString;
        }

        public boolean isAttributeBoolean() {
            return attributeBoolean;
        }

        public void setAttributeBoolean(final boolean attributeBoolean) {
            this.attributeBoolean = attributeBoolean;
        }
    }


    private static void printInfo(final Class<? extends Object> aClass) {
        final String aClassName = aClass.getName();
        System.out.println("aClassName = " + aClassName);
        final String pkgName = aClass.getPackage().getName();
        System.out.println("pkgName = " + pkgName);

        final String s = pkgName.replace(".", "/");
        System.out.println("s = " + s);
    }

}