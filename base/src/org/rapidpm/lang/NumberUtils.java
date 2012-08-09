/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.lang;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 20.10.11
 * Time: 12:24
 */
public class NumberUtils {

    public static int compare(final int a, final int b) {
        return (a < b) ? -1 : ((a > b) ? 1 : 0);
    }

    private NumberUtils() {
    }
}