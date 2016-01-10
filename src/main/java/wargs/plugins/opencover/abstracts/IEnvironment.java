package wargs.plugins.opencover.abstracts;

import java.io.IOException;

import hudson.FilePath;
import hudson.util.ArgumentListBuilder;

public interface IEnvironment {
	void Build(ArgumentListBuilder args) throws IOException, InterruptedException;
	void PrintToLog(String message);
	void Throw(String message);
	FilePath GetFilePath(String relativePathToFile);
}
