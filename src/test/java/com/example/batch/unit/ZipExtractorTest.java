package com.example.batch.unit;


import com.example.batch.config.ZipMultiResourceItemReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

class ZipExtractorTest {


  @Test
  void zipExtractorTest() throws IOException {

    List<Resource> resources = new ArrayList<>();
    ZipMultiResourceItemReader.extractFiles(new ZipFile("src/main/resources/input/data.zip"), resources);
    Assertions.assertThat(resources).hasSize(2);
    Assertions.assertThat(resources.get(0).getDescription()).isEqualTo("file [/home/sasun/IdeaProjects/spring-batch-csv/src/main/resources/input/part.csv]");
    Assertions.assertThat(resources.get(1).getDescription()).isEqualTo("file [/home/sasun/IdeaProjects/spring-batch-csv/src/main/resources/input/part.csv]");
  }
}
