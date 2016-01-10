package wargs.plugins.opencover;

import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import jenkins.model.Jenkins;

@SuppressWarnings("serial")
public class NUnitInstallation extends ToolInstallation
implements NodeSpecific<NUnitInstallation>, EnvironmentSpecific<NUnitInstallation>
{
	@DataBoundConstructor
	public NUnitInstallation(final String name, final String home, final String defaultArgs){
		super(name, home, null);
	}
	@Extension
    public static class DescriptorImpl extends ToolDescriptor<NUnitInstallation> {
		NUnitInstallation[] Installations;
		public DescriptorImpl(){load();}
        public String getDisplayName() {
            return "NUnit";
        }

        @Override
        public NUnitInstallation[] getInstallations(){
        	return Installations;
        }
        @Override
        public void setInstallations(NUnitInstallation... installations) {
        	Installations = installations;
        	save();
        }
    }

	@Override
	public NUnitInstallation forEnvironment(EnvVars environment) {
		return new NUnitInstallation(getName(), environment.expand(getHome()), null);
	}
	@Override
	public NUnitInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
		return new NUnitInstallation(getName(), translateFor(node, log), null);
	}
}
