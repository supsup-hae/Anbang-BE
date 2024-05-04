package org.anbang.anbang_server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellRunner {

  private final static int BUFFER_SIZE = 8192 * 4;

  public static void execCommand(String command) {
    try {
      Process process = Runtime.getRuntime().exec(command);

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()),
          BUFFER_SIZE);
      BufferedReader errorReader = new BufferedReader(
          new InputStreamReader(process.getErrorStream()), BUFFER_SIZE);
      String errorLine = null;
      String line = null;

      while ((line = reader.readLine()) != null || (errorLine = errorReader.readLine()) != null) {
        if (line != null) {
          System.out.println(line);
        } else {
          System.err.println(errorLine);
        }
      }

      int exitCode = process.waitFor();
      System.out.println("Script execution finished with exit code: " + exitCode);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}