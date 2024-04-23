import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import run.bach.Bach;
import run.bach.ToolCall;
import run.bach.ToolRunner;
import run.bach.external.GoogleJavaFormat;
import run.bach.workflow.Builder;
import run.bach.workflow.Launcher;
import run.bach.workflow.Structure;
import run.bach.workflow.Workflow;

public record Project(Workflow workflow) implements Builder, Launcher {
  public static Project ofCurrentWorkingDirectory() {
    var folders = Bach.Folders.ofCurrentWorkingDirectory();

    var structure =
        new Structure(
            new Structure.Basics("bach-demo-swing", "99"),
            new Structure.Spaces(
                new Structure.Space(
                        "main",
                        0,
                        "demo=demo/demo.Main",
                        new Structure.DeclaredModule(
                            Path.of("demo"), Path.of("demo/main/module-info.java")))
                    .with(Structure.Space.Flag.IMAGE),
                new Structure.Space(
                    "test",
                    List.of("main"),
                    0,
                    List.of(Structure.Launcher.of("all-tests=demo/demo.AllTests")),
                    new Structure.DeclaredModules(
                        new Structure.DeclaredModule(
                            Path.of("demo"),
                            Path.of("demo/test/java-module/module-info.java"),
                            ModuleDescriptor.newModule("demo").build(),
                            new Structure.DeclaredFolders(
                                List.of(
                                    Path.of("demo/test/java"), Path.of("demo/test/java-module")),
                                List.of()),
                            Map.of())),
                    Set.of())));

    var runner = ToolRunner.ofSystem();
    var workflow = new Workflow(folders, structure, runner);
    return new Project(workflow);
  }

  @Override
  public boolean builderShouldInvokeCleanBeforeCompile() {
    return true;
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
