package com.javaquasar.vk.api;

import com.javaquasar.vk.api.oauth.user.RequestHandler;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.audio.responses.GetResponse;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Quasar
 */
public class Main {
    
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Properties properties = loadConfiguration();
        //initServer(properties);

        Integer port = Integer.valueOf(properties.getProperty("server.port"));
        String host = properties.getProperty("server.host");

        Integer clientId = Integer.valueOf(properties.getProperty("client.id"));
        String clientSecret = properties.getProperty("client.secret");

        HandlerCollection handlers = new HandlerCollection();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(Main.class.getResource("/static").getPath());

        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        handlers.setHandlers(new Handler[]{resourceHandler, new RequestHandler(vk, clientId, clientSecret, host)});
        
        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(clientId, clientSecret, "", "4")
                .execute();
        
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        // 6.4. Client Credentials Flow
//        ServiceClientCredentialsFlowResponse authResponse = vk.oauth()
//                .serviceClientCredentialsFlow(APP_ID, CLIENT_SECRET)
//                .execute();
//
//        ServiceActor actor = new ServiceActor(APP_ID, authResponse.getAccessToken());

//        GetResponse getResponse = vk.wall().get(actor)
//                .ownerId(1)
//                .count(100)
//                .offset(5)
//               // .filter("owner")
//                .execute();
    }
    
    private static void initServer(Properties properties) throws Exception {
        Integer port = Integer.valueOf(properties.getProperty("server.port"));
        String host = properties.getProperty("server.host");

        Integer clientId = Integer.valueOf(properties.getProperty("client.id"));
        String clientSecret = properties.getProperty("client.secret");

        HandlerCollection handlers = new HandlerCollection();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(Main.class.getResource("/static").getPath());

        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        handlers.setHandlers(new Handler[]{resourceHandler, new RequestHandler(vk, clientId, clientSecret, host)});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

    private static Properties loadConfiguration() {
        Properties properties = new Properties();
        try (InputStream is = Main.class.getResourceAsStream("/config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            LOG.error("Can't load properties file", e);
            throw new IllegalStateException(e);
        }

        return properties;
    }
}
