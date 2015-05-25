/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.exception;/**
 * RapidPM
 * @author svenruppert
 * @since 19.07.2008
 * Time: 21:45:23
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

public class NotYetImplementedException extends Exception {
    private static final Logger logger = Logger.getLogger(NotYetImplementedException.class);

    public NotYetImplementedException() {
        super("Not yet Implemented!");
    }

}