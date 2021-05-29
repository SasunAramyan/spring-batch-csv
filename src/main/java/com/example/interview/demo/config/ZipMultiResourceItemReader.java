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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;


public class ZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {


  /**
   * Extract only files from the zip archive.
   *
   * @param currentZipFile
   * @param extractedResources
   * @throws IOException
   */
  static void extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException {
    Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();
    while (zipEntryEnum.hasMoreElements()) {
      ZipEntry zipEntry = zipEntryEnum.nextElement();
      System.out.println("extracting:" + zipEntry.getName());
      // traverse directories
      if (!zipEntry.isDirectory()) {
        File fie = new File("src/main/resources/input/part.csv");
        InputStream inputStream = currentZipFile.getInputStream(zipEntry);
        try (OutputStream outputStream = new FileOutputStream(fie)) {
          IOUtils.copy(inputStream, outputStream);
          extractedResources.add(new FileSystemResource(fie));

//          // add inputStream
//          extractedResources.add(
//              new InputStreamResource(
//                  currentZipFile.getInputStream(zipEntry),
//                  zipEntry.getName()));
//          System.out.println("using extracted file:" + zipEntry.getName());
        }
      }
    }

  }
}

