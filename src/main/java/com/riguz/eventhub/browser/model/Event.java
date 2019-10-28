package com.riguz.eventhub.browser.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Event {
    private final SimpleStringProperty partition;
    private final SimpleStringProperty offset;
    private final SimpleStringProperty sequenceNumber;
    private final SimpleStringProperty createdTime;
    private final SimpleStringProperty message;

    public Event(String partition, String offset, String sequenceNumber, String createdTime, String message) {
        this.partition = new SimpleStringProperty(partition);
        this.offset = new SimpleStringProperty(offset);
        this.sequenceNumber = new SimpleStringProperty(sequenceNumber);
        this.createdTime = new SimpleStringProperty(createdTime);
        this.message = new SimpleStringProperty(message);
    }

    public String getPartition() {
        return partition.get();
    }

    public SimpleStringProperty partitionProperty() {
        return partition;
    }

    public String getOffset() {
        return offset.get();
    }

    public SimpleStringProperty offsetProperty() {
        return offset;
    }

    public String getSequenceNumber() {
        return sequenceNumber.get();
    }

    public SimpleStringProperty sequenceNumberProperty() {
        return sequenceNumber;
    }

    public String getCreatedTime() {
        return createdTime.get();
    }

    public SimpleStringProperty createdTimeProperty() {
        return createdTime;
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(partition, event.partition) &&
                Objects.equals(offset, event.offset) &&
                Objects.equals(sequenceNumber, event.sequenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partition, offset, sequenceNumber);
    }
}
