package com.riguz.eventhub.browser.consumer;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.eventprocessorhost.EventProcessorOptions;
import com.microsoft.azure.eventprocessorhost.InMemoryCheckpointManager;
import com.microsoft.azure.eventprocessorhost.InMemoryLeaseManager;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.util.Properties;

public class EventProcessorHostFactory {
    private static final Properties config;

    static {
        config = new Properties();
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static EventProcessorHost createHost() {
        final String profile = config.getProperty("profile");
        final String consumerGroupName = config.getProperty(profile + ".consumerGroupName");
        final String hostNamePrefix = config.getProperty(profile + ".hostNamePrefix");
        final String connectionString = config.getProperty(profile + ".connectionString");


        InMemoryCheckpointManager checkpointManager = new InMemoryCheckpointManager();
        InMemoryLeaseManager leaseManager = new InMemoryLeaseManager();
        EventProcessorHost host = EventProcessorHost.EventProcessorHostBuilder
                .newBuilder(EventProcessorHost.createHostName(hostNamePrefix),
                        consumerGroupName)
                .useUserCheckpointAndLeaseManagers(
                        checkpointManager,
                        leaseManager)
                .useEventHubConnectionString(connectionString)
                .build();
        final Object hostContext;
        try {
            hostContext = FieldUtils.readField(host, "hostContext", true);
            FieldUtils.writeField(leaseManager, "hostContext", hostContext, true);
            FieldUtils.writeField(checkpointManager, "hostContext", hostContext, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Registering host named " + host.getHostName());
        EventProcessorOptions options = new EventProcessorOptions();
        options.setExceptionNotification(new ErrorNotificationHandler());

        return host;
    }
}
