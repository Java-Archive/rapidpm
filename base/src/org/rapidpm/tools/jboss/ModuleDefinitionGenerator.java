/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.tools.jboss;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 09.11.11
 * Time: 09:04
 */
public class ModuleDefinitionGenerator {
    private static final Logger logger = Logger.getLogger(ModuleDefinitionGenerator.class);

    private static final String LIB_DIR_PREFIX = "lib-";
    private static final String LIB_NAME_PREFIX = "org.rapidpm.";
    private static final String MODULE_SUBDIR = "main";
    private static final String MODULE_FILENAME = "module.xml";

    private String baseLibDir = "/opt/neoscio/NeoScio_Libs";
    private String baseModuleDir = "/opt/jboss/modules";

    private boolean copyLibs = true;

    public String getBaseLibDir() {
        return baseLibDir;
    }

    public void setBaseLibDir(final String baseLibDir) {
        this.baseLibDir = baseLibDir;
    }

    public String getBaseModuleDir() {
        return baseModuleDir;
    }

    public void setBaseModuleDir(final String baseModuleDir) {
        this.baseModuleDir = baseModuleDir;
    }

    public boolean isCopyLibs() {
        return copyLibs;
    }

    public void setCopyLibs(final boolean copyLibs) {
        this.copyLibs = copyLibs;
    }

    public void generateModuleDefinitions() throws IOException {
        final File baseLibDirFile = new File(baseLibDir);
        for (final File libDir : baseLibDirFile.listFiles()) {
            final String libDirName = libDir.getName();
            if (libDir.isDirectory() && libDirName.startsWith(LIB_DIR_PREFIX)) {
                final String libName = libDirName.substring(LIB_DIR_PREFIX.length());
                final File[] libFiles = libDir.listFiles();
                if (libFiles.length > 0) {
                    final String packageName = LIB_NAME_PREFIX + libName;
                    final File moduleDir = new File(baseModuleDir, packageNameToModuleDir(packageName));
                    if (moduleDir.exists() && moduleDir.isDirectory() || moduleDir.mkdirs()) {
                        final File moduleDefinitionFile = new File(moduleDir, MODULE_FILENAME);
                        // TODO XML builder
                        final Writer outputStream = new BufferedWriter(new FileWriter(moduleDefinitionFile));
                        outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                        outputStream.write("<module xmlns=\"urn:jboss:module:1.0\" name=\"" + packageName + "\">\n");
                        outputStream.write("  <resources>\n");
                        for (final File libFile : libFiles) {
                            if (libFile.getName().endsWith(".jar")) {
                                outputStream.write("    <resource-root path=\"" + libFile.getName() + "\"/>\n");
                                if (copyLibs) {
                                    FileUtils.copyFile(libFile, new File(moduleDir, libFile.getName()));
                                }
                            } else {
                                //
                            }
                        }
                        outputStream.write("  </resources>\n");
                        outputStream.write("  <dependencies>\n");
                        outputStream.write("    <!-- <module name=\"" + LIB_NAME_PREFIX + "moduleName\"/> -->\n");
                        outputStream.write("  </dependencies>\n");
                        outputStream.write("</module>\n");
                        outputStream.close();
                    } else {
                        logger.error("Verzeichnis '" + moduleDir.getAbsolutePath() + "' konnte nicht erstellt werden.");
                    }
                } else {
                    // keine Libs gefunden
                }
            } else {
                //
            }
        }
    }

    private static String packageNameToModuleDir(final String packageName) {
        final Iterable<String> packageParts = Splitter.on('.').split(packageName);
        return Joiner.on(File.separatorChar).join(packageParts) + File.separatorChar + MODULE_SUBDIR;
    }

    public static void main(final String[] args) throws IOException {
        final ModuleDefinitionGenerator moduleDefinitionGenerator = new ModuleDefinitionGenerator();
        moduleDefinitionGenerator.setBaseLibDir(args[0]);
        moduleDefinitionGenerator.setBaseModuleDir(args[1]);
        moduleDefinitionGenerator.generateModuleDefinitions();
    }
}