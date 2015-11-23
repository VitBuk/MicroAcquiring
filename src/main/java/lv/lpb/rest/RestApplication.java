package lv.lpb.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Ainars Vigulis <ainars.vigulis@lpb.lv>
 * @version 1.0.0
 */
@ApplicationPath("")
public class RestApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.disableMoxyJson", true);
        properties.put("jersey.config.disableMoxyJson", true);

        return properties;
    }
}
