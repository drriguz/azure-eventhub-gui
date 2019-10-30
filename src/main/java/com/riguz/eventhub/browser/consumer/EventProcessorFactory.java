package com.riguz.eventhub.browser.consumer;

import com.microsoft.azure.eventprocessorhost.IEventProcessorFactory;
import com.microsoft.azure.eventprocessorhost.PartitionContext;
import com.riguz.eventhub.browser.model.Event;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

public class EventProcessorFactory implements IEventProcessorFactory<EventProcessor> {
    private final Consumer<Event> onReceived;

    public EventProcessorFactory(Consumer<Event> onReceived) {
        this.onReceived = onReceived;
    }

    @Override
    public EventProcessor createEventProcessor(PartitionContext partitionContext) throws Exception {
        System.out.println("Creating new event processor:" + partitionContext.getPartitionId());
        return new EventProcessor(onReceived);
    }
}
