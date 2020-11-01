package com.execution;


import org.junit.Test;

public class CmdExecutorCase {

  private final CmdExecutor cmdExecutor = new CmdExecutor();

  @Test(expected = NullPointerException.class)
  public void testProcessNPE1() {
    cmdExecutor.execute(null);
  }

}
