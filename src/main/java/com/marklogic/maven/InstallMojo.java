package com.marklogic.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.exceptions.RequestException;


/**
 * Install and configure the database(s), application server(s), etc. in the specified 
 * configuration.
 * 
 * @author <a href="mailto:mark.helmstetter@marklogic.com">Mark Helmstetter</a>
 * @goal install
 * @execute goal="bootstrap"
 */
public class InstallMojo extends AbstractInstallMojo {
	
	// TODO should we just manually check if bootstrap is required and provide an error,
	// rather than just executing bootstrap every time?

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("install execute");
		try {
			ResultSequence rs = executeInstallAction("install-all");
			System.out.println(rs.asString());
		} catch (RequestException e) {
			throw new MojoExecutionException("xcc request error", e);
		}
	}
	
}