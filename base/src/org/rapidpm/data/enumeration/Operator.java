/*
 * Copyright (c) 2009. This is part of the NeoScio Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

/**
 * NeoScio
 * @author svenruppert
 * @since 25.07.2008
 * Time: 11:03:50
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 *
 */
package org.rapidpm.data.enumeration;

public class Operator {
    public static final Operator AND = new Operator("AND");
    public static final Operator NOT = new Operator("NOT");
    public static final Operator MAIN = new Operator("MAIN");
    public static final Operator XOR = new Operator("XOR");
    public static final Operator OR = new Operator("OR");

    private final String myName; // for debug only

    private Operator(final String name) {
        super();
        myName = name;
    }

    @Override
    public String toString() {
        return myName;
    }
}