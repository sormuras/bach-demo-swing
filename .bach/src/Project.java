import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import run.bach.*;
import run.bach.external.*;
import run.bach.workflow.*;
import run.bach.workflow.Structure.*;

public record Project(Workflow workflow) implements Builder, Launcher, Printer, Tester {
  public static Project ofCurrentWorkingDirectory() {
    var folders = Bach.Folders.ofCurrentWorkingDirectory();

    var structure =
        new Structure(
            new Basics("bach-demo-swing", "99"),
            new Spaces(
                new Space(
                    "main",
                    0,
                    "demo/demo.Main",
                    new DeclaredModule(Path.of("demo"), Path.of("demo/main/module-info.java"))),
                new Space(
                    "test",
                    List.of("main"),
                    0,
                    List.of("demo/demo.AllTests"),
                    new DeclaredModules(
                        new DeclaredModule(
                            Path.of("demo"),
                            Path.of("demo/test/java-module/module-info.java"),
                            ModuleDescriptor.newModule("demo").build(),
                            new DeclaredFolders(
                                List.of(
                                    Path.of("demo/test/java"), Path.of("demo/test/java-module")),
                                List.of()),
                            Map.of())))));

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
