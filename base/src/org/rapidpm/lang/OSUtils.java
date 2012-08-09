/*
 * Copyright (c) 2005 - 2008 Aduna.
 * All rights reserved.
 * 
 * Licensed under the Open Software License version 3.0.
 */
package org.rapidpm.lang;

import java.io.File;
import java.lang.reflect.Method;

public abstract class OSUtils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /**
     * Checks if the given directory is a MacOS X bundle. This only makes sense on MaxOS X, and will always
     * return false on other OS.
     */
    public static boolean isMacOSXBundle(final File f) {
        if (!isMac()) {
            return false;
        }

        try {
            final Class<?> apc = OSUtils.class.getClassLoader().loadClass("org.gnowsis.util.AppleUtils");
            final Method m = apc.getMethod("isBundle", new Class[]{File.class});
            return (Boolean) m.invoke(null, new Object[]{f});
        } catch (Exception e) {
            throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
        }
    }
}