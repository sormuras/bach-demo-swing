import java.nio.file.Path;
import run.bach.*;
import run.bach.external.*;
import run.bach.workflow.*;

public record Project(Workflow workflow) implements Builder, Launcher, Printer, Tester {
  public static Project ofCurrentWorkingDirectory() {
    var folders = Bach.Folders.ofCurrentWorkingDirectory();
    var structure =
        new Structure(
            new Structure.Basics("bach-demo-swing", "99"),
            new Structure.Spaces(
                new Structure.Space(
                    "main",
                    0,
                    "demo/demo.Main",
                    new Structure.DeclaredModule(
                        Path.of("demo"), Path.of("demo/main/module-info.java")))));
    var runner = ToolRunner.ofSystem();
    var workflow = new Workflow(folders, structure, runner);
    return new Project(workflow);
  }

  public void format(String... args) {
    var folders = workflow().folders();
    var format = new GoogleJavaFormat("1.22.0").install();
    ToolCall.of(format)
        .when(args.length == 0, call -> call.add("--replace").addFiles(folders.root(), "**.java"))
        .when(args.length != 0, call -> call.addAll(args))
        .run();
  }
}
