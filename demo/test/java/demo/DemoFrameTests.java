package demo;

class DemoFrameTests {
  public static void main(String... args) {
    var frame = new DemoFrame();
    assert "bach-demo-swing".equals(frame.getTitle());
    assert !frame.isVisible();
  }
}
