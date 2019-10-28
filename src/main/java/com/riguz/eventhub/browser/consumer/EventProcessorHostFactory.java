package com.riguz.eventhub.browser.consumer;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.eventprocessorhost.EventProcessorOptions;
import com.microsoft.azure.eventprocessorhost.InMemoryCheckpointManager;
import com.microsoft.azure.eventprocessorhost.InMemoryLeaseManager;
import org.apache.commons.lang3.reflect.FieldUtils;

public class EventProcessorHostFactory {
    public static EventProcessorHost createHost() {
        String consumerGroupName = "<your consumer group name>";
        String hostNamePrefix = "<your hostname prefix>";

        // dev env
        String connectionString = "<your conncection string>";

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
