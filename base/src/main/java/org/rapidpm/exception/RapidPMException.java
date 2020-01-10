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


public class RapidPMException extends Exception {

    public RapidPMException() {
        super();
    }

    public RapidPMException(final String message) {
        super(message);
    }

    public RapidPMException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RapidPMException(final Throwable cause) {
        super(cause);
    }

}