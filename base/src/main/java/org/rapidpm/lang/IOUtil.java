/*
 * Copyright (c) since 2009
 * Dieses ist Bestandteil des RapidPM-Projektes und Eigentum der RapidPM - www.rapidpm.org (haftungsbeschraenkt)
 * Sven Ruppert : sven.ruppert@me.com
 * .
 */
package org.rapidpm.lang;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.zip.Adler32;

/**
 * I/O utility methods for working with Readers, Writers, InputStreams, OutputStreams and URLs.
 */
public class IOUtil {

  /**
   * Reads all characters from the supplied reader and returns them as an array
   */
  public static char[] readChars(final Reader r) throws IOException {
    return readFully(r).toCharArray();
  }

  private static CharArrayWriter readFully(final Reader r) throws IOException {
    final char[] buf = new char[4096];
    int charsRead = 0;
    final CharArrayWriter result = new CharArrayWriter();

    while ((charsRead = r.read(buf)) != -1) {
      result.write(buf, 0, charsRead);
    }

    return result;
  }

  /**
   * Reads the contents from the given file as a String.
   */
  public static String readString(final File file) throws IOException {
    try (FileInputStream in = new FileInputStream(file)) {
      return readString(in);
    }

  }

  /**
   * Read the contents of the given stream as a String, using the default Charset.
   */
  public static String readString(final InputStream in) throws IOException {
    return readString(new InputStreamReader(in));
  }

  /**
   * Reads all characters from the supplied reader and returns them as a String.
   */
  public static String readString(final Reader r) throws IOException {
    return readFully(r).toString();
  }

  /**
   * Reads the contents from the given URL as a String.
   */
  public static String readString(final URL url) throws IOException {
    String result = null;

    try (Reader reader = urlToReader(url)) {
      result = readString(reader);
    }


    return result;
  }

  /**
   * Creates a Reader accessing the contents of the specified URL.
   */
  public static Reader urlToReader(final URL url) throws IOException {
    final URLConnection con = url.openConnection();
    return new InputStreamReader(con.getInputStream());
  }

  /**
   * Read the contents of the given stream as a String, using the given Charset.
   */
  public static String readString(final InputStream in, final Charset charset) throws IOException {
    return readString(new InputStreamReader(in, charset));
  }

  /**
   * Reads a string of at most length <tt>maxChars</tt> from the supplied Reader.
   *
   * @param r        The Reader to read the string from.
   * @param maxChars The maximum number of characters to read.
   *
   * @return A String of length <tt>maxChars</tt>, or less if the supplied Reader did not contain
   * that much characters.
   */
  public static String readString(final Reader r, final int maxChars) throws IOException {
    final char[] charBuf = new char[maxChars];
    final int charsRead = fillCharArray(r, charBuf);
    return new String(charBuf, 0, charsRead);
  }

  /**
   * Fills the supplied character array with characters read from the specified Reader. This method
   * will only stop reading when the character array has been filled completely, or when the end of the
   * stream has been reached.
   *
   * @param r         The Reader to read the characters from.
   * @param charArray The character array to fill with characters.
   *
   * @return The number of characters written to the character array.
   */
  public static int fillCharArray(final Reader r, final char[] charArray) throws IOException {
    int result = 0;

    int charsRead = r.read(charArray);

    while (charsRead >= 0) {
      result += charsRead;

      if (result == charArray.length) {
        break;
      }

      charsRead = r.read(charArray, result, charArray.length - result);
    }

    return result;
  }

  /**
   * Reads all bytes from the supplied input stream and returns them as a byte array.
   *
   * @param in The InputStream supplying the bytes.
   *
   * @return A byte array containing all bytes from the supplied input stream.
   */
  public static byte[] readBytes(final InputStream in) throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
    writeStream(in, out);
    return out.toByteArray();
  }

  /**
   * Writes all data that can be read from the supplied InputStream to the supplied OutputStream.
   */
  public static void writeStream(final InputStream in, final OutputStream out) throws IOException {
    final byte[] buf = new byte[4096];
    int bytesRead = 0;

    while ((bytesRead = in.read(buf)) != -1) {
      out.write(buf, 0, bytesRead);
    }
  }

  /**
   * Reads bytes from the supplied input stream up until a maximum number of bytes has been reached and
   * returns them as a byte array.
   *
   * @param in       The InputStream supplying the bytes.
   * @param maxBytes The maximum number of bytes to read from the input stream.
   *
   * @return A byte array of size maxBytes if the input stream can produce that amount of bytes, or a
   * smaller array containing all available bytes from the stream otherwise.
   */
  public static byte[] readBytes(final InputStream in, final int maxBytes) throws IOException {
    byte[] result = new byte[maxBytes];

    final int bytesRead = fillByteArray(in, result);

    if (bytesRead < maxBytes) {
      // create smaller byte array
      final byte[] tmp = new byte[bytesRead];
      System.arraycopy(result, 0, tmp, 0, bytesRead);
      result = tmp;
    }

    return result;
  }

  /**
   * Fills the supplied byte array with bytes read from the specified InputStream. This method will
   * only stop reading when the byte array has been filled completely, or when the end of the stream
   * has been reached.
   *
   * @param in        The InputStream to read the bytes from.
   * @param byteArray The byte array to fill with bytes.
   *
   * @return The number of bytes written to the byte array.
   */
  public static int fillByteArray(final InputStream in, final byte[] byteArray) throws IOException {
    int result = 0;

    int bytesRead = in.read(byteArray);

    while (bytesRead >= 0) {
      result += bytesRead;

      if (result == byteArray.length) {
        break;
      }

      bytesRead = in.read(byteArray, result, byteArray.length - result);
    }

    return result;
  }

  public static String rollingHash(final InputStream stream) throws IOException {
    final Adler32 adler = new Adler32();
    adler.reset();
    final byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = stream.read(buffer)) != -1) {
      adler.update(buffer, 0, bytesRead);
    }
    return String.valueOf(adler.getValue());
  }

  /**
   * Writes all data that can be read from the supplied InputStream to the specified file.
   */
  public static void writeStream(final InputStream in, final File file) throws IOException {
    final FileOutputStream out = new FileOutputStream(file);

    try {
      writeStream(in, out);
    } finally {
      try {
        out.flush();
      } finally {
        out.close();
      }
    }
  }

  /**
   * Write the contents of the specified contents String to a file with the specified file name.
   */
  public static void writeString(final String contents, final String filename) throws IOException {
    writeString(contents, new File(filename));
  }

  /**
   * Writes the contents of the specified String to the specified File.
   */
  public static void writeString(final String contents, final File file) throws IOException {
    try (FileWriter out = new FileWriter(file)) {
      out.write(contents);
    }

  }

  /**
   * Reads rdf from the resource at the given resource path to the given model
   *
   * @param model  the model where the RDF data should be stored
   * @param path   the resource path
   * @param syntax the syntax
   *
   * @throws FileNotFoundException if the resource has not been foud
   * @throws IOException           if an I/O error occurs in the process
   * @throws ModelException        ...
   */
    /*
    public static void readFileFromResource(Model model, String path, Syntax syntax)
            throws FileNotFoundException, IOException, ModelException {
        InputStream stream = ResourceUtil.getInputStream(path, IOUtil.class);
        if (stream == null) {
            throw new FileNotFoundException("couldn't find resource " + path);
        }
        model.readFrom(stream, syntax);
    }
    */
}