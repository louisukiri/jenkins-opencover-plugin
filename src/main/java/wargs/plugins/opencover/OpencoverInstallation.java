package wargs.plugins.opencover;

import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.model.Descriptor.FormException;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public final class OpencoverInstallation extends ToolInstallation 
implements NodeSpecific<OpencoverInstallation>,
EnvironmentSpecific<OpencoverInstallation>
{	
	@DataBoundConstructor
	public OpencoverInstallation(final String name, 
			final String home)
	{
		super(name, home, null);
		
	}
    @Extension
    public static class DescriptorImpl extends ToolDescriptor<OpencoverInstallation> {

    	
    	OpencoverInstallation[] Installations;
    	public DescriptorImpl(){super(); load();}
        public String getDisplayName() {
            return "Opencover";
        }

        @Override
        public void setInstallations(OpencoverInstallation... installations) {
        	//Jenkins.getInstance().getDescriptorByType(OpencoverInstallation.DescriptorImpl.class).setInstallations(installations);
        	Installations = installations;
        	save();
        }

		@Override
		public OpencoverInstallation[] getInstallations(){
			return Installations;
		}
    }
    public String getOpencoverPath(){
    	return this.getHome();
    }
	@Override
	public OpencoverInstallation forEnvironment(EnvVars environment) {
		return new OpencoverInstallation(getName(), environment.expand(getHome()));
	}


	@Override
	public OpencoverInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
		return new OpencoverInstallation(getName(), translateFor(node, log));
	}
}
