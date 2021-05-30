package com.example.batch.config;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {

  private final static Logger logger = LoggerFactory.getLogger(ZipMultiResourceItemReader.class);
  private final static String TEMP_FILE_PATH = "src/main/resources/input/temp";

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
        File fie = new File(TEMP_FILE_PATH + LocalDateTime.now() + ".csv");
        InputStream inputStream = currentZipFile.getInputStream(zipEntry);
        try (OutputStream outputStream = new FileOutputStream(fie)) {
          IOUtils.copy(inputStream, outputStream);
          extractedResources.add(new FileSystemResource(fie));
        }
      }
    }
  }
}

