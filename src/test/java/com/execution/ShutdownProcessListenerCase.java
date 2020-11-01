package com.execution;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class ShutdownProcessListenerCase {

  @Test
  public void testShutdown() {
    AtomicBoolean isStopped = new AtomicBoolean(false);
    CmdExecutor cmdExecutor = new CmdExecutor();

    Thread commandThread = new Thread(() -> {
      Thread thread = new Thread(() -> cmdExecutor.execute("ping -t 127.0.0.1"));
      thread.start();
    });
    commandThread.start();

    Thread shutdownThread = new Thread(() -> {
      try {
        Thread.sleep(500);
        isStopped.set(cmdExecutor.shutdown());
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
    });
    shutdownThread.start();

    try {
      commandThread.join();
      shutdownThread.join();
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }

    assertTrue(isStopped.get());
  }
}
