package com.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class ShutdownProcessListener extends Thread {
  private final ArrayList<Thread> listeners = new ArrayList<>();
  private final Process process;
  private boolean isShutdownRequired = false;
  private final ReentrantLock mutex = new ReentrantLock();

  public ShutdownProcessListener(Process process, Thread... listeners) {
    this.process = process;
    this.listeners.addAll(Arrays.asList(listeners));
  }

  @Override
  public void run() {
    while (process.isAlive()) {
      synchronized (mutex) {
        mutex.lock();
        if (isShutdownRequired) {
          for (Thread listener : listeners) {
            listener.interrupt();
            System.out.println("Listener: " + listener.getName() + " was interrupted...");
          }
          process.destroyForcibly();
          System.out.println("Subprocess: " + process.toString() + " was killed!");
          isShutdownRequired = false;
        }
        mutex.unlock();
      }
    }
  }

  public boolean shutdown() {
    synchronized (mutex) {
      mutex.lock();
      isShutdownRequired = true;
      mutex.unlock();
    }

    try {
      this.join();
    } catch (InterruptedException exception) {
      exception.printStackTrace();
      return false;
    }
    return true;
  }
}