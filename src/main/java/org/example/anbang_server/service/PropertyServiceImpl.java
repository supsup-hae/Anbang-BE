package org.example.anbang_server.service;


import java.util.Map;
import org.example.anbang_server.model.Property;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

  private final static ShellRunner SHELL_RUNNER = new ShellRunner();
  private final static String[] CALL_CMD = {"/bin/bash", "-c",
      "sh src/main/resources/script/SC.sh"};

  @Override
  public ResponseEntity<String> propertyRegistration(Property property) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public void buildChannelCA() {
    Map<Integer, String> map = SHELL_RUNNER.execCommand(CALL_CMD);
    System.out.println(map);
  }
}
