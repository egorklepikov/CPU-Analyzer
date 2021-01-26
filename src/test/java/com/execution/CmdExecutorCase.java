package com.execution;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CmdExecutorCase {

  private final CmdExecutor cmdExecutor = new CmdExecutor();

//  @Test(expected = NullPointerException.class)
//  public void testProcessNPE1() {
//    cmdExecutor.execute(null);
//  }

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

//  @Test(expected = RuntimeException.class)
//  public void testShutdown() {
//    cmdExecutor.shutdown();
//  }

  @Test
  @Tag("first")
  public void someTest1() {

  }

  @Test
  @Tag("second")
  public void someTest2() {

  }
}
