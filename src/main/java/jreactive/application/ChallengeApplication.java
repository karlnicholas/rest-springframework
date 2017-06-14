package jreactive.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main javax.ws.rs.Application context.
 * 
 * @author Karl Nicholas.
 * @version 2017.04.02
 *
 */

@ApplicationPath("rest")
public class ChallengeApplication extends ResourceConfig {
    
    public ChallengeApplication() {
        packages("javachallenge");
    }
}