package org.skramer.greeter;

import java.io.PrintStream;

/**
 * Created by skramer on 9/18/16.
 */
public class Greeter {
  public void greet(PrintStream out, String name) {
    out.println(createGreeting(name));
  }

  public String createGreeting(String name) {
    return "Hello, " + name;
  }
}
