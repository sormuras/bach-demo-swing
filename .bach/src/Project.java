import java.lang.module.ModuleDescriptor;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import run.bach.ModuleLocator;
import run.bach.ToolRunner;
import run.bach.workflow.Builder;
import run.bach.workflow.Folders;
import run.bach.workflow.Starter;
import run.bach.workflow.Structure;
import run.bach.workflow.Workflow;

public record Project(Workflow workflow) implements Builder, Starter {
  public static Project ofCurrentWorkingDirectory() {
    var folders = Folders.ofCurrentWorkingDirectory();

    var main =
        new Structure.Space("main")
            .withTargetingJavaRelease(11)
            .withLauncher("demo=demo/demo.Main")
            .withModule("demo", "demo/main/module-info.java")
            .withCompileRuntimeImage();

    var test =
        new Structure.Space("test", main)
            .withLauncher("all-tests=demo/demo.AllTests")
            .with(
                new Structure.DeclaredModule(
                    Path.of("demo"),
                    Path.of("demo/test/java-module/module-info.java"),
                    ModuleDescriptor.newModule("demo").build(),
                    new Structure.DeclaredFolders(
                        List.of(Path.of("demo/test/java"), Path.of("demo/test/java-module")),
                        List.of()),
                    Map.of()));
    var structure =
        new Structure(
            new Structure.Basics("bach-demo-swing", "99"),
            new Structure.Spaces(main, test),
            ModuleLocator.compose());

    var runner = ToolRunner.ofSystem();
    var workflow = new Workflow(folders, structure, runner);
    return new Project(workflow);
  }

  @Override
  public boolean builderDoesCleanAtTheBeginning() {
    return true;
  }
}
