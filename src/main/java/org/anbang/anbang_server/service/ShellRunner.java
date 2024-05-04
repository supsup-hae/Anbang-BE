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

      Thread outputThread = new Thread(() -> {
        String line;
        try {
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      outputThread.start();

      Thread errorThread = new Thread(() -> {
        String line;
        try {
          while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      errorThread.start();

      // 프로세스의 종료를 기다림
      int exitCode = process.waitFor();
      System.out.println("스크립트 실행이 종료되었습니다. 종료코드: " + exitCode);

      // 스레드들이 종료될 때까지 대기
      outputThread.join();
      errorThread.join();

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}