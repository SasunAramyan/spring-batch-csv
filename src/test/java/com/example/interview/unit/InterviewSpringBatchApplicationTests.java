package com.example.interview.unit;


import com.example.interview.demo.config.ZipMultiResourceItemReader;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

class InterviewSpringBatchApplicationTests {


  void zipExtractorTest() throws IOException {

    List<Resource> resources = new ArrayList<>();
		ZipMultiResourceItemReader.extractFiles(new ZipFile("src/test/resources/input/data.zip"), resources);


  }
}
