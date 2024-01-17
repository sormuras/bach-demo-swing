import run.bach.*;

class Project {
  interface Action {
    static void build() {
      System.out.println("Building with " + Bach.class);
    }
  }
}
