package com.execution;

import java.util.ArrayList;
import java.util.Arrays;

public class ShutdownProcessListener extends Thread {
  private final ArrayList<Thread> listeners = new ArrayList<>();
  private final Process process;
  private boolean isShutdownRequired = false;

  public ShutdownProcessListener(Process process, Thread... listeners) {
    this.process = process;
    this.listeners.addAll(Arrays.asList(listeners));
  }

  @Override
  public void run() {
    while (process.isAlive()) {
      if (isShutdownRequired) {
        for (Thread listener : listeners) {
          listener.interrupt();
          System.out.println("Listener: " + listener.getName() + " was interrupted...");
        }
        process.destroyForcibly();
        System.out.println("Subprocess: " + process.toString() + " was killed!");
        isShutdownRequired = false;
      }
    }
  }

  public boolean shutdown() {
    isShutdownRequired = true;

    if (process.isAlive()) {
      try {
        this.join();
      } catch (InterruptedException exception) {
        exception.printStackTrace();
        return false;
      }
    }

    boolean listenersDisableCheck = false;
    for (Thread listener : listeners) {
      if (!listener.isInterrupted()) {
        listenersDisableCheck = true;
        break;
      }
    }

    return !process.isAlive() && !listenersDisableCheck;
  }
}