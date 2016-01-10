package wargs.plugins.opencover;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.kohsuke.stapler.DataBoundConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import hudson.CopyOnWrite;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.XmlFile;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.util.ArgumentListBuilder;
import hudson.util.ListBoxModel;


public class OpencoverBuilder extends Builder{

	String opencoverInstallations;
	String nunitInstallations;
	String solutionPath;
	String xml;
	String filter;
	String outputFile;
    @DataBoundConstructor
	public OpencoverBuilder(final String opencoverInstallations, final String nunitInstallations
			, final String solutionPath, final String xml, final String filter, final String outputFile){
		this.opencoverInstallations = opencoverInstallations;
		this.nunitInstallations = nunitInstallations;
		this.solutionPath = solutionPath;
		this.xml = xml;
		this.filter = filter;
		this.outputFile = outputFile;
	}
    public String getOpencoverInstallations(){ return opencoverInstallations;}
    public String getNunitInstallations(){return nunitInstallations;}
    public String getSolutionPath(){return solutionPath;}
    public String getXml(){return xml;}
    public String getFilter(){return filter;}
    public String getOutputFile(){return outputFile;}
    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		ArgumentListBuilder args = new ArgumentListBuilder();
		OpencoverInstallation opencover = getOpenCover(getOpencoverInstallations());
		NUnitInstallation nunit = getNUnit(getNunitInstallations());
		
    	listener.getLogger().println("ok jim");
    	listener.getLogger().println("Opencover Installation : " + getOpencoverInstallations());
    	listener.getLogger().println("NUnit Installation : " + getNunitInstallations());
    	listener.getLogger().println("Path to Opencover: " + opencover.getHome());
    	listener.getLogger().println("Path to NUnit: " + nunit.getHome());
    	
    	
    	args.prepend("cmd.exe", "/C"
    			,"\"\"" + opencover.getHome() + "\"" + 
    			" -target:"+ nunit.getHome() + 
    			" -targetargs:\"/nologo "+getSolutionPath() + 
    			" /xml " + getXml() + "" + 
    			" /noshadow\" " + 
    			" -register:user" + 
    			" -filter: \""+getFilter() +"\"" +
    			" -output:"+getOutputFile() +  " -hideskipped:All" +
    			"\"");
    	
    	/*args.add("echo");
    	args.add("ok jim");
    	args.prepend("cmd.exe", "/C");*/
    	EnvVars env = build.getEnvironment(listener);
    	launcher.launch().cmds(args).envs(env).stdout(listener.getLogger()).pwd(build.getModuleRoot()).join();
    	FilePath path = build.getModuleRoot();
    	FilePath xmlPath = path.child(getOutputFile());

    	String xmlPathStr = xmlPath.readToString();
    	Pattern p = Pattern.compile("branchCoverage=\"([^\"]+)");
		Matcher m = p.matcher(xmlPathStr);
		String Val = "";
		m.find();
		Val = m.group(1);
		try {
	        DocumentBuilderFactory dbf =
	            DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xmlPathStr));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("Summary");
	        Element element = (Element)nodes.item(0);
	        listener.getLogger().println("From el: " + element.getAttribute("branchCoverage"));
	        
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		listener.getLogger().println("node count: " + Val);

    	listener.getLogger().println("finished");
    	//listener.getLogger().println(xmlPathStr);
    	return true;
		
	}
    public OpencoverInstallation getOpenCover(String name)
    {
    	if(name == null)
    		return null;
    	for(OpencoverInstallation installation : ToolInstallation.all().get(OpencoverInstallation.DescriptorImpl.class).getInstallations())
    	{
    		if(installation.getHome().equals(name))
    			return installation;
    	}
    	return null;
    }
    public NUnitInstallation getNUnit(String name)
    {
    	if(name == null)
    		return null;
    	for(NUnitInstallation installation : ToolInstallation.all().get(NUnitInstallation.DescriptorImpl.class).getInstallations())
    	{
    		if(installation.getHome().equals(name))
    			return installation;
    	}
    	return null;
    }
    public static String[] getString(){
    	return new String[]{"test", "b", "c", "D"};
    }
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder>{

    	@CopyOnWrite
    	private volatile OpencoverInstallation[] installations = new OpencoverInstallation[0];
    	
    	public String getDisplayName(){
    		return "Opencover Test";
    	}
    	public OpencoverInstallation[] getInstallations(){
    		return installations;
    	}
		@Override
		public boolean isApplicable(@SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType) {
			return true;
		}

        public void setInstallations(OpencoverInstallation... antInstallations) {
            this.installations = antInstallations;
            save();
        }
		public OpencoverInstallation.DescriptorImpl getToolDescriptor(){
			return ToolInstallation.all().get(OpencoverInstallation.DescriptorImpl.class);
		}
		public NUnitInstallation.DescriptorImpl getNUnitDescriptor(){
			return ToolInstallation.all().get(NUnitInstallation.DescriptorImpl.class);
		}
		public ListBoxModel doFillOpencoverInstallationsItems(){
			return FillInstallations(getToolDescriptor().getInstallations());
		}
		public ListBoxModel doFillNunitInstallationsItems(){
			return FillInstallations(getNUnitDescriptor().getInstallations());
		}
		public <T extends ToolInstallation> ListBoxModel FillInstallations(T[] installations){

			ListBoxModel items = new ListBoxModel();
			for(T installation : installations)
			{
				items.add(installation.getName(), installation.getHome());
			}
			return items;
		}
    }
}
