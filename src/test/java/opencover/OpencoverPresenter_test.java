package opencover;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hudson.util.ArgumentListBuilder;
import wargs.plugins.opencover.NUnitInstallation;
import wargs.plugins.opencover.OpencoverInstallation;
import wargs.plugins.opencover.OpencoverPresenter;
import wargs.plugins.opencover.abstracts.*;


public class OpencoverPresenter_test {

	IEnvironment envi;
	IOpencoverModuleView view;
	OpencoverPresenter sut;
	@Before
	public void setUp() throws Exception {
		view = mock(IOpencoverModuleView.class);
		envi = mock(IEnvironment.class);
		sut = new OpencoverPresenter(view);
	}

	@Test
	public void requiresModuleView()
	{
		assertNotNull(sut.View());
	}

	@Test
	public void GivenArgumentsThenBuild() {
		//sut = mock(OpencoverPresenter.class);
		OpencoverPresenter _spy = spy(sut);
		ArgumentListBuilder args = new ArgumentListBuilder();
		stub(_spy.getArgs()).toReturn(args);

		_spy.Build(envi);
		
		org.mockito.Mockito.verify(envi).Build(args);
	}
	@Test
	public void GettingArgumentsReturnCmdArgAsIndex0()
	{
		doReturn(new NUnitInstallation("test2","nunit.exe",null)).when(view).getNUnitInstallation();
		doReturn(new OpencoverInstallation("test","opencover.exe")).when(view).getOpencoverInstallation();
		doReturn("solutionpath").when(view).getSolutionPath();
		doReturn("outputfile").when(view).getOutputFile();
		doReturn("xml").when(view).getXml();
		doReturn("filter").when(view).getFilter();
		
		OpencoverPresenter _spy = spy(sut);
		stub(_spy.View()).toReturn(view);
		
		ArgumentListBuilder result = _spy.getArgs();
		
		assertEquals("cmd.exe",result.toList().get(0));
	}
	@Test
	public void GettingArgumentsReturnCSlashAsIndex0()
	{
		doReturn(new NUnitInstallation("test2","nunit.exe",null)).when(view).getNUnitInstallation();
		doReturn(new OpencoverInstallation("test","opencover.exe")).when(view).getOpencoverInstallation();
		doReturn("solutionpath").when(view).getSolutionPath();
		doReturn("outputfile").when(view).getOutputFile();
		doReturn("xml").when(view).getXml();
		doReturn("filter").when(view).getFilter();
		
		OpencoverPresenter _spy = spy(sut);
		stub(_spy.View()).toReturn(view);
		
		ArgumentListBuilder result = _spy.getArgs();
		
		assertEquals("/C",result.toList().get(1));
	}
	@Test
	public void GettingArgumentsReturnOpencoverArgumentAsIndex2()
	{
		doReturn(new NUnitInstallation("test2","nunit.exe",null)).when(view).getNUnitInstallation();
		doReturn(new OpencoverInstallation("test","opencover.exe")).when(view).getOpencoverInstallation();
		doReturn("solutionpath").when(view).getSolutionPath();
		doReturn("outputfile").when(view).getOutputFile();
		doReturn("xml").when(view).getXml();
		doReturn("filter").when(view).getFilter();
		
		OpencoverPresenter _spy = spy(sut);
		stub(_spy.View()).toReturn(view);
		
		ArgumentListBuilder result = _spy.getArgs();
		
		assertEquals("\"\"opencover.exe\"" + 
    			" -target:nunit.exe"+ 
    			" -targetargs:\"/nologo solutionpath"+ 
    			" /xml xml" + 
    			" /noshadow\" " + 
    			" -register:user" + 
    			" -filter: \"filter"+"\"" +
    			" -output:outputfile" +  " -hideskipped:All" +
    			"\"",result.toList().get(2));
	}

}
