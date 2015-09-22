/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

/**
 * RapidPM
 *
 * @author svenruppert
 * @since 02.08.2008
 * Time: 20:14:07
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 */
package org.rapidpm;

import java.util.HashSet;
import java.util.Set;

public class MimeType {
  public static final MimeType PDF = new MimeType("pdf", true);
  public static final MimeType PS = new MimeType("ps", true);

  public static final MimeType XLS = new MimeType("xls", true);
  public static final MimeType XLSX = new MimeType("xlsx", true);
  public static final MimeType PPT = new MimeType("ppt", true);
  public static final MimeType PPTX = new MimeType("pptx", true);
  public static final MimeType DOC = new MimeType("doc", true);
  public static final MimeType DOCX = new MimeType("docx", true);

  public static final MimeType ZIP = new MimeType("zip", true);
  public static final MimeType BZ2 = new MimeType("bz2", true);
  public static final MimeType TARGZ = new MimeType("tar.gz", true);
  public static final MimeType GZ = new MimeType("gz", true);//TODO immer hinter tar.gz

  public static final MimeType JPG = new MimeType("jpg", false);
  public static final MimeType JPEG = new MimeType("jpeg", false);
  public static final MimeType BMP = new MimeType("bmp", false);
  public static final MimeType AVI = new MimeType("avi", false);
  public static final MimeType MPG = new MimeType("mpg", false);
  public static final MimeType MPEG = new MimeType("mpeg", false);
  public static final MimeType MOV = new MimeType("mov", false);
  private static final Set<MimeType> types;

  static {
    types = new HashSet<>();
  }

  private final String mimetype; // for debug only
  private final boolean parseable;

  private MimeType(final String name, final boolean parseable) {
    super();
    mimetype = name;
    this.parseable = parseable;
//        types.add(this);
  }

  /**
   * @param type
   * @return
   */
  public static boolean contains(final String type) {
    init();


    for (final MimeType mimeType : types) {
      final String s = mimeType.mimetype.toLowerCase();
      if (s.equals(type.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  private static void init() {
    //TODO schrott Code!!
    if (types.size() < 1) {
      types.add(PDF);
      types.add(PS);
      types.add(XLS);
      types.add(XLSX);
      types.add(PPT);
      types.add(PPTX);
      types.add(DOC);
      types.add(DOCX);
      types.add(ZIP);
      types.add(BZ2);
      types.add(TARGZ);
      types.add(GZ);
      types.add(JPG);
      types.add(JPEG);
      types.add(BMP);
      types.add(AVI);
      types.add(MPG);
      types.add(MPEG);
      types.add(MOV);
    }
  }

  public static Set<MimeType> getMimeTypes() {
    init();
    return types;
  }

  public boolean isParseable() {
    return parseable;
  }

  public String getMimetype() {
    return mimetype;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("MimeType");
    sb.append("{mimetype='").append(mimetype).append('\'');
    sb.append(", parseable=").append(parseable);
    sb.append('}');
    return sb.toString();
  }
}