package demo;

import javax.swing.*;

class DemoFrame extends JFrame {
  DemoFrame() {
    super("bach-demo-swing");
    setSize(640, 480);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setFocusable(true);
    setAutoRequestFocus(true);
  }
}
