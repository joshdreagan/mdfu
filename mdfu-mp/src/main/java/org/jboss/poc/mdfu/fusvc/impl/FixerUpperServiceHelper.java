package org.jboss.poc.mdfu.fusvc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FixerUpperServiceHelper {
  
  public FixerUpperServiceHelper() {}
  
  public static final String createCorrelationId() {
    return UUID.randomUUID().toString();
  }
  
  public static final List<?> createSubmitMethodCall(String correlationId, String callbackUri, String data) {
    List<Object> params = new ArrayList<>();
    params.add(correlationId);
    params.add(callbackUri);
    params.add(data);
    return params;
  }
}
