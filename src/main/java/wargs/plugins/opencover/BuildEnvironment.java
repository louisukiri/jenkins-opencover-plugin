package wargs.plugins.opencover;
import java.io.IOException;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.util.ArgumentListBuilder;
import wargs.plugins.opencover.abstracts.IEnvironment;

public class BuildEnvironment implements IEnvironment {
	AbstractBuild<?,?> build;
	Launcher launcher;
	BuildListener listener;
	public BuildEnvironment(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener){
		this.build = build;
		this.launcher = launcher;
		this.listener = listener;
	}
	@Override
	public void Build(ArgumentListBuilder args) throws IOException, InterruptedException {
		EnvVars env = build.getEnvironment(listener);
		launcher.launch().cmds(args).envs(env).stdout(listener.getLogger()).pwd(build.getModuleRoot()).join();
	}

	@Override
	public void PrintToLog(String message) {
		listener.getLogger().println(message);
	}

	@Override
	public void Throw(String message) {
		build.setResult(Result.FAILURE);
	}

	@Override
	public FilePath GetFilePath(String relativePathToFile) {
		return build.getModuleRoot().child(relativePathToFile);
	}

}
