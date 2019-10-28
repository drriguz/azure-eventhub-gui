package com.riguz.eventhub.browser.consumer;

import com.microsoft.azure.eventprocessorhost.IEventProcessorFactory;
import com.microsoft.azure.eventprocessorhost.PartitionContext;
import com.riguz.eventhub.browser.model.Event;
import javafx.collections.ObservableList;

public class EventProcessorFactory implements IEventProcessorFactory<EventProcessor> {
    private final ObservableList<Event> tableItems;

    public EventProcessorFactory(ObservableList<Event> tableItems) {
        this.tableItems = tableItems;
    }

    @Override
    public EventProcessor createEventProcessor(PartitionContext partitionContext) throws Exception {
        System.out.println("Creating new event processor:" + partitionContext.getPartitionId());
        return new EventProcessor(tableItems);
    }
}
