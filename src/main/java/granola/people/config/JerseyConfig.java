package granola.people.config;

import granola.people.detection.api.ObjectDetector;
import granola.people.detection.impl.pHash;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyConfig extends ResourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(JerseyConfig.class);

    public JerseyConfig() {
        LOG.info("Initializing Jersey configuration");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                // APIs
                bind(pHash.class).to(ObjectDetector.class);
            }
        });
    }
}
