package com.execution;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CmdExecutorCase {

  private final CmdExecutor cmdExecutor = new CmdExecutor();

  @Test(expected = NullPointerException.class)
  public void testProcessNPE1() {
    cmdExecutor.execute(null);
  }

  @Test
  public void testExecution() {
    assertTrue(cmdExecutor.execute("java -version"));
  }

  @Test
  public void testExecution1() {
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
    assertTrue(cmdExecutor.execute("java -version"));
  }

  @Test(expected = RuntimeException.class)
  public void testShutdown() {
    cmdExecutor.shutdown();
  }
}
