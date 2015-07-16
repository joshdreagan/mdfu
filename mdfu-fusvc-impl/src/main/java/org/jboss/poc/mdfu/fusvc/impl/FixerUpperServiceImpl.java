package org.jboss.poc.mdfu.fusvc.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebService;

import org.jboss.poc.mdfu.fusvc.FixerUpperServicePortType;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;

@WebService(endpointInterface = "org.jboss.poc.mdfu.fusvc.FixerUpperServicePortType",
            serviceName = "FixerUpperService")
public class FixerUpperServiceImpl implements FixerUpperServicePortType {

  private static final String BPMS_USERNAME = "bpmsAdmin";
  private static final String BPMS_PASSWORD = "admin1!!";
  private static final String BPMS_DEPLOYMENT_ID = "org.jboss.poc:camel-ht-jbpm-impl:1.0.0";
  private static final String BPMS_BASE_URI = "http://localhost:8080/business-central";
  private static final String BPMS_PROCESS_ID = "org.jboss.poc.impl.InvalidDataFixerUpper";
  
  private RuntimeEngine engine = null;

  public FixerUpperServiceImpl() {
    try {
      engine = RemoteRuntimeEngineFactory.newRestBuilder()
              .addUserName(BPMS_USERNAME)
              .addPassword(BPMS_PASSWORD)
              .addDeploymentId(BPMS_DEPLOYMENT_ID)
              .addUrl(URI.create(BPMS_BASE_URI).toURL())
              .build();
    } catch (Exception e) {
      throw new RuntimeException(String.format("Unable to create %s.", RuntimeEngine.class.getName()), e);
    }
  }

  @Override
  public void submit(String correlationId, String callbackUri, String data) {
    KieSession kSession = engine.getKieSession();
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("correlationId", correlationId);
    parameters.put("callbackUri", callbackUri);
    parameters.put("data", data);
    kSession.startProcess(BPMS_PROCESS_ID, parameters);
  }
}
