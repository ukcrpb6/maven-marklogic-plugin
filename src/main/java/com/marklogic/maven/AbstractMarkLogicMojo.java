package com.marklogic.maven;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.maven.plugin.AbstractMojo;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;

/**
 * 
 */
public abstract class AbstractMarkLogicMojo extends AbstractMojo {

    /**
     * The host MarkLogic Server is running on.
     * 
     * @parameter default-value="localhost" expression="${marklogic.host}"
     */
    protected String host;
    
    /**
     * The host MarkLogic Server is running on.
     * 
     * @parameter default-value="admin" expression="${marklogic.username}"
     */
    protected String username;
    
    /**
     * The host MarkLogic Server is running on.
     * 
     * @parameter default-value="admin" expression="${marklogic.password}"
     */
    protected String password;
    
    /**
     * The XDBC port used for install purposes
     * 
     * @parameter default-value="8998" expression="${marklogic.xdbc.port}"
     */
    protected int xdbcPort = 8998;
    
    /**
     * The database to be used for XDBC connections
     * 
     * @parameter expression="${marklogic.xdbc.database}"
     */
    protected String database;    
    
    /**
     * The environment name, which specifies with configuration profile
     * is being applied.
     * 
     * @parameter default-value="development" expression="${marklogic.environment}"
     */
    protected String environment;
    
    protected Session getXccSession() {
    	ContentSource cs = null;
    	if (database == null) {
    		cs = ContentSourceFactory.newContentSource(host, xdbcPort, username, password);
    	} else {
    		cs = ContentSourceFactory.newContentSource(host, xdbcPort, username, password, database);
    	}
        Session session = cs.newSession();
        return session;
    }
    
    protected String getXdbcConnectionString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("xcc://");
    	if (username != null) {
    		sb.append(username);
    		sb.append(":");
    		sb.append(password);
    		sb.append("@");
    	}
    	sb.append(host);
    	sb.append(":");
    	sb.append(xdbcPort);
    	if (database != null) {
    		sb.append("/");
    		sb.append(database);
    	}
    	return sb.toString();
    }
    
	protected HttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(
                AuthScope.ANY, 
                new UsernamePasswordCredentials(username, password));

        //httpClient.getHostConfiguration().setProxy("my.proxyhost.com", 80);
        //Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
        //httpClient.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), defaultcreds);
        return httpClient;
	}
	
}