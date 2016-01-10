package wargs.plugins.opencover;

import java.io.IOException;

import hudson.util.ArgumentListBuilder;
import wargs.plugins.opencover.abstracts.IEnvironment;
import wargs.plugins.opencover.abstracts.IOpencoverModuleView;

public class OpencoverPresenter {

	private IOpencoverModuleView view;
	public IOpencoverModuleView View() {
		// TODO Auto-generated method stub
		return view;
	}
	
	public OpencoverPresenter(IOpencoverModuleView view)
	{
		this.view = view;
	}
	public void Build(IEnvironment env) throws IOException, InterruptedException {
		
		env.Build(getArgs());
	}
	public ArgumentListBuilder getArgs()
	{
	    ArgumentListBuilder builder = new ArgumentListBuilder();
	    builder.add("cmd.exe");
	    builder.add("/C");
	    builder.add("\"\"" + View().getOpencoverInstallation().getHome() + "\"" + 
    			" -target:"+ View().getNUnitInstallation().getHome() + 
    			" -targetargs:\"/nologo "+ View().getSolutionPath() + 
    			" /xml " + View().getXml() + "" + 
    			" /noshadow\" " + 
    			" -register:user" + 
    			" -filter: \""+ View().getFilter() +"\"" +
    			" -output:"+ View().getOutputFile() +  " -hideskipped:All" +
    			"\"");
		return builder;
	}

}
