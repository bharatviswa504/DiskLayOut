package com.bh;

import java.util.Map;

/**
 * Generic Class to hold Container information.
 */
public class Container {
  private String containerType;
  private LifeCycleState state;
  private int layOutVersion;
  private Map<String, String> customMetaData;

  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }

  public LifeCycleState getState() {
    return state;
  }

  public void setState(LifeCycleState state) {
    this.state = state;
  }

  public int getLayOutVersion() {
    return layOutVersion;
  }

  public void setLayOutVersion(int layOutVersion) {
    this.layOutVersion = layOutVersion;
  }

  public Map<String, String> getCustomMetaData() {
    return customMetaData;
  }

  public void setCustomMetaData(Map<String, String> customMetaData) {
    this.customMetaData = customMetaData;
  }

  public enum LifeCycleState {
    ALLOCATED(1), CLOSED(2);
    private int state;
    LifeCycleState(int state) {
      this.state = state;
    }
  }
}

