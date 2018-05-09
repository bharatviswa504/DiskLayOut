package com.bh;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
  public static void main(String args[]) throws IOException {

    if(args.length ==0) {
      create();
      read();
    } else  {
      if(args[0].equals("create")) {
        create();
      } else {
        read();
      }
    }
  }

  public static void read() throws IOException {
    String storageDir = "/home/bviswanadham/yaml/";
    Yaml yaml = new Yaml();
    List<Container> containerMap = new ArrayList<Container>();
    long time = now();
    File containerDir = new File(storageDir);
    File[] files = containerDir.listFiles();
    try {
      if (files != null) {
        for (File subDirs : files) {
          File[] subDir = subDirs.listFiles();
          for (File containerDirs : subDir) {
            File[] yamlFiles = containerDirs.listFiles(new ContainerFilter());
            for (File yamlFile : yamlFiles) {
              FileReader yamlFileReader = new FileReader(yamlFile);
              KeyValueContainer kv = yaml.loadAs(yamlFileReader, KeyValueContainer.class);
              containerMap.add(kv);
           /*System.out.println(kv.getContainerType());*/
              yamlFileReader.close();
            }
          }
        }
      }
      long end = now();
    } catch(IOException e) {
      e.printStackTrace();
    } finally {
      long end = now();
      System.out.println("Total time taken to read and load Map " + (end - time));
      System.out.println("Container map Size is " + containerMap.size());
    }
  }

  public static void create() throws IOException{
    Yaml yaml = new Yaml();
    long time = now();
    String storageDir = "/home/bviswanadham/yaml/";

    for(long i=0L; i<50000L; i++) {
      KeyValueContainer kc = createKeyValueContainer();
      String containerDir = containerDir(i);
      File containerPath = new File(storageDir + containerDir + i);

      if(!containerPath.exists()) {
        containerPath.mkdirs();
      }
      String containerFile = storageDir + containerDir + i + "/" + i + ".yaml";
      FileWriter fw = new FileWriter(containerFile);
      yaml.dump(kc, fw);
      fw.close();
    }
    long end = now();
    System.out.println("Total time taken to write" + (end-time));

  }

  public static KeyValueContainer createKeyValueContainer() {
    KeyValueContainer keyValueContainer = new KeyValueContainer();
    keyValueContainer.setChunkLocations("a");
    keyValueContainer.setContainerMetaDataPath("b");
    keyValueContainer.setContainerType("rocksdb");
    keyValueContainer.setLayOutVersion(1);
    keyValueContainer.setState(Container.LifeCycleState.CLOSED);
    Map<String, String> custom = new HashMap<String, String>();
    custom.put("volume", "v1");
    custom.put("owner", "bh");
    keyValueContainer.setCustomMetaData(custom);
    return keyValueContainer;
  }

  /**
   * Current system time.  Do not use this to calculate a duration or interval
   * to sleep, because it will be broken by settimeofday.  Instead, use
   * monotonicNow.
   * @return current time in msec.
   */
  public static long now() {
    return System.currentTimeMillis();
  }

  public static String containerDir(long containerId) {
    String CONATINER_SUBDIR_PREFIX = "subdir";
    int d1 = (int) ((containerId >> 9) & 0xFF);
    String path = CONATINER_SUBDIR_PREFIX + d1 + "/";
    return path;
  }

  /**
   * Filter out only container files from the container metadata dir.
   */
  private static class ContainerFilter implements FilenameFilter {
    /**
     * Tests if a specified file should be included in a file list.
     *
     * @param dir the directory in which the file was found.
     * @param name the name of the file.
     * @return <code>true</code> if and only if the name should be included in
     * the file list; <code>false</code> otherwise.
     */
    public boolean accept(File dir, String name) {
      return name.endsWith(".yaml");
    }
  }
}
