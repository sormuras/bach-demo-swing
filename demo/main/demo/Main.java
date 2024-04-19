package demo;

import javax.swing.*;

class Main implements Runnable {
  public static void main(String... args) {
    if (!java.awt.Desktop.isDesktopSupported()) {
      System.out.println("Desktop not supported");
      return;
    }
    SwingUtilities.invokeLater(new Main());
  }

  @Override
  public void run() {
    var frame = new DemoFrame();
    frame.setVisible(true);
  }
}
