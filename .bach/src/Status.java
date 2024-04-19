import static run.bach.workflow.Printer.PrinterTopic.STATUS;

class Status {
  public static void main(String... args) {
    Project.ofCurrentWorkingDirectory().print(STATUS);
  }
}
