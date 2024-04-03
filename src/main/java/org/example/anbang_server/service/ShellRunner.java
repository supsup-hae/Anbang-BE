package org.example.anbang_server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellRunner {

  public static void execCommand(String command) {
    try {
      // Execute the command
      Process process = Runtime.getRuntime().exec(command);

      // Get the input stream of the process
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      // Read the output of the process line by line
      String line;
      while ((line = reader.readLine()) != null) {
        // Display the output in real-time
        System.out.println(line);
      }

      // Wait for the process to finish
      int exitCode = process.waitFor();
      System.out.println("Script execution finished with exit code: " + exitCode);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}