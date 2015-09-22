package org.rapidpm.exception;

/**
 * Created by Marco on 06.06.2015.
 */
public class MissingNonOptionalPropertyException extends Exception {

  public MissingNonOptionalPropertyException(String propertyName) {
    super("Missing non-optional property: " + propertyName);
  }
}
