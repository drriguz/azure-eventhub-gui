package com.riguz.eventhub.browser.consumer;

import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventprocessorhost.CloseReason;
import com.microsoft.azure.eventprocessorhost.IEventProcessor;
import com.microsoft.azure.eventprocessorhost.PartitionContext;
import com.riguz.eventhub.browser.model.Event;
import javafx.collections.ObservableList;

import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;

public class EventProcessor implements IEventProcessor {
    private final Consumer<Event> onReceived;
    private int checkpointBatchingCount = 0;

    public EventProcessor(Consumer<Event> onReceived) {
        this.onReceived = onReceived;
    }

    @Override
    public void onOpen(PartitionContext context) throws Exception {
        System.out.println("Partition " + context.getPartitionId() + " is opening");
    }

    @Override
    public void onClose(PartitionContext context, CloseReason reason) throws Exception {
        System.out.println("Partition " + context.getPartitionId() + " is closing for reason " + reason.toString());
    }

    @Override
    public void onError(PartitionContext context, Throwable error) {
        System.out.println("Partition " + context.getPartitionId() + " onError: " + error.toString());
    }

    private void handleEvent(PartitionContext context, EventData data) throws UnsupportedEncodingException {
        final Event event = new Event(
                context.getPartitionId(),
                data.getSystemProperties().getOffset(),
                String.valueOf(data.getSystemProperties().getSequenceNumber()),
                data.getSystemProperties().getEnqueuedTime().toString(),
                new String(data.getBytes(), "UTF8"));
        onReceived.accept(event);
        System.out.println("=> (" + context.getPartitionId() + "," + data.getSystemProperties().getOffset() + "," +
                data.getSystemProperties().getSequenceNumber() + "): ");
    }

    @Override
    public void onEvents(PartitionContext context, Iterable<EventData> events) throws Exception {
        System.out.println("Partition " + context.getPartitionId() + " got event batch");
        int eventCount = 0;
        for (EventData data : events) {
            try {
                handleEvent(context, data);
                eventCount++;

                this.checkpointBatchingCount++;
                if ((checkpointBatchingCount % 5) == 0) {
                    System.out.println("Partition " + context.getPartitionId() + " checkpointing at " +
                            data.getSystemProperties().getOffset() + "," + data.getSystemProperties().getSequenceNumber());
                    context.checkpoint(data).get();
                }
            } catch (Exception e) {
                System.out.println("Processing failed for an event: " + e.toString());
            }
        }
        System.out.println("Partition " + context.getPartitionId() + " batch size was " + eventCount + " for host " + context.getOwner());
    }
}