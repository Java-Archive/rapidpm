/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.services;

import org.apache.log4j.Logger;
import org.rapidpm.lang.PackageClassLoader;
import org.rapidpm.logging.LoggerQualifier;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 02:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateless(name = "WebServiceInfoEJB")
@WebService(name = "WebServiceInfoWS")
public class WebServiceInfoBean {
    public WebServiceInfoBean() {
    }

    @Inject @LoggerQualifier
    private transient Logger logger;

    @WebResult(name = "servicelist")

    @WebMethod(operationName = "getWebServiceList")
    public
    List<String> getWebServiceList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                   @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {

        final PackageClassLoader packageClassLoader = new PackageClassLoader();
        final List<Class> classesWithAnnotation = packageClassLoader.getClassesWithAnnotation("de.rapidpm",
                WebService.class);
        final List<String> result = new ArrayList<>();
        for (final Class aClass : classesWithAnnotation) {
            final WebService annotation = (WebService) aClass.getAnnotation(WebService.class);
            result.add(annotation.name());
        }
        Collections.sort(result);
        return result;

    }


//    @WebMethod(operationName = "getTailFromLogfile")
//
//    public
//    @WebResult(name = "logmessages")
//    String getTailFromLogfile(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
//                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
//        final File nohup = new File("/opt/jboss/7.0.2/nohup.out");
//        return tailNLines(nohup, 1000);
//
//    }

//    public String tailNLines(File file, int lines) {
//        try {
//            java.io.RandomAccessFile fileHandler = new java.io.RandomAccessFile(file, "r");
//            long fileLength = file.length() - 1;
//            StringBuilder sb = new StringBuilder();
//            int line = 0;
//
//            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
//                fileHandler.seek(filePointer);
//                int readByte = fileHandler.readByte();
//
////                if (readByte == 0xA) {
////                    if (line == lines) {
////                        if (filePointer == fileLength) {
////                            continue;
////                        } else {
////                            break;
////                        }
////                    }
//////                } else if (readByte == 0xD) {
////                } else
//
//                if (readByte == Constants.LINE_BREAK) {
//                    line = line + 1;
//                    if (line == lines) {
//                        if (filePointer == fileLength - 1) {
//                            continue;
//                        } else {
//                            break;
//                        }
//                    }
//                }
//                sb.append((char) readByte);
//            }
//
//            sb.deleteCharAt(sb.length() - 1);
//            final String lastLine = sb.reverse().toString();
//            return lastLine;
//        } catch (java.io.FileNotFoundException e) {
//            //logger.error(e);
//            return null;
//        } catch (java.io.IOException e) {
//            //logger.error(e);
//            return null;
//        }
//    }
}