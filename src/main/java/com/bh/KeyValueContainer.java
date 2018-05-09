package com.bh;


public class KeyValueContainer extends Container{
  private String ContainerMetaDataPath;
  private String chunkLocations;

  public String getContainerMetaDataPath() {
    return ContainerMetaDataPath;
  }

  public void setContainerMetaDataPath(String containerMetaDataPath) {
    ContainerMetaDataPath = containerMetaDataPath;
  }

  public String getChunkLocations() {
    return chunkLocations;
  }

  public void setChunkLocations(String chunkLocations) {
    this.chunkLocations = chunkLocations;
  }
}

