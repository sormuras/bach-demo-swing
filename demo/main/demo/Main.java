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
    var frame = new JFrame("bach-demo-swing");
    frame.setSize(640, 480);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
    frame.setFocusable(true);
    frame.setAutoRequestFocus(true);
  }
}
