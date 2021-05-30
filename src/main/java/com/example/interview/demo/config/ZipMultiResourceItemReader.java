package com.example.interview.demo.config;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


public class ZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

  private final static Logger logger = LoggerFactory.getLogger(ZipMultiResourceItemReader.class);


  /**
   * Extract only files from the zip archive.
   *
   * @param currentZipFile
   * @param extractedResources
   * @throws IOException
   */
  public static void extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException {
    Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();
    while (zipEntryEnum.hasMoreElements()) {
      ZipEntry zipEntry = zipEntryEnum.nextElement();
      logger.info("extracting: {}", zipEntry.getName());
      if (!zipEntry.isDirectory()) {
        File fie = new File("src/main/resources/input/part.csv");
        InputStream inputStream = currentZipFile.getInputStream(zipEntry);
        try (OutputStream outputStream = new FileOutputStream(fie)) {
          IOUtils.copy(inputStream, outputStream);
          extractedResources.add(new FileSystemResource(fie));
        }
      }
    }
  }
}

