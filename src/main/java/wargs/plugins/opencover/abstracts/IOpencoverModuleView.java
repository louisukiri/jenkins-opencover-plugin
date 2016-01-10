package wargs.plugins.opencover.abstracts;

import wargs.plugins.opencover.NUnitInstallation;
import wargs.plugins.opencover.OpencoverInstallation;

public interface IOpencoverModuleView {
    public String getOpencoverInstallations();
    public String getNunitInstallations();
    public String getSolutionPath();
    public String getXml();
    public String getFilter();
    public String getOutputFile();
    public OpencoverInstallation getOpencoverInstallation();
    public NUnitInstallation getNUnitInstallation();
}
