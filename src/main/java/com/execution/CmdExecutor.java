package com.execution;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.util.concurrent.atomic.AtomicBoolean;public class CmdExecutor {  private Process process;  private ShutdownProcessListener shutdownProcessListener;  private boolean isExecutionStarted = false;  private enum ThreadType {    ERRORS,    INFORMATION  }  public boolean execute(String command) {    final AtomicBoolean successful = new AtomicBoolean(false);    try {      Runtime runtimeEnvironment = Runtime.getRuntime();      process = runtimeEnvironment.exec(command);    } catch (IOException exception) {      exception.printStackTrace();    }    if (process == null) throw new NullPointerException();    Thread informationListenerThread = new Thread(() -> successful.set(listenThread(ThreadType.INFORMATION)));    Thread errorsListenerThread = new Thread(() -> successful.set(listenThread(ThreadType.ERRORS)));    informationListenerThread.start();    errorsListenerThread.start();    shutdownProcessListener = new ShutdownProcessListener(process, informationListenerThread, errorsListenerThread);    shutdownProcessListener.start();    isExecutionStarted = true;    try {      informationListenerThread.join();      errorsListenerThread.join();      shutdownProcessListener.join();    } catch (InterruptedException exception) {      exception.printStackTrace();    }    return successful.get();  }  private boolean listenThread(ThreadType threadType) {    InputStream stream;    switch (threadType) {      case ERRORS: {        stream = process.getErrorStream();        break;      }      case INFORMATION: {        stream = process.getInputStream();        break;      }      default:        throw new IllegalStateException("Unexpected value: " + threadType);    }    InputStreamReader streamReader = new InputStreamReader(stream);    BufferedReader bufferedReader = new BufferedReader(streamReader);    try {      String consoleLine = bufferedReader.readLine();      while (consoleLine != null) {        System.out.println(consoleLine);        consoleLine = bufferedReader.readLine();      }    } catch (IOException exception) {      exception.printStackTrace();    }    return true;  }  public boolean shutdown() {    if (isExecutionStarted) {      return shutdownProcessListener.shutdown();    } else {      throw new RuntimeException("Execution is not started yet");    }  }}