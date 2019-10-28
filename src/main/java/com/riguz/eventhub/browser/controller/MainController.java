package com.riguz.eventhub.browser.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.riguz.eventhub.browser.consumer.EventProcessorFactory;
import com.riguz.eventhub.browser.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class MainController {
    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, Integer> partitionColumn;

    @FXML
    private TableColumn<Event, Integer> offsetColumn;

    @FXML
    private TableColumn<Event, Integer> sequenceColumn;

    @FXML
    private TableColumn<Event, String> createdTimeColumn;

    @FXML
    private TableColumn<Event, String> messageColumn;

    @FXML
    private TextArea textArea;

    @FXML
    private PieChart partitionMessageCountChart;

    public final ObservableList<Event> data = FXCollections.observableArrayList(
    );

    public void initialize() {
        partitionColumn.setCellValueFactory(new PropertyValueFactory<>("partition"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offset"));
        sequenceColumn.setCellValueFactory(new PropertyValueFactory<>("sequenceNumber"));
        createdTimeColumn.setCellValueFactory(new PropertyValueFactory<>("createdTime"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableView.setItems(data);

        tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                String json = tableView.getSelectionModel().getSelectedItem().getMessage();
                try {
                    JsonParser parser = new JsonParser();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    JsonElement el = parser.parse(json);
                    json = gson.toJson(el);
                }catch (Exception ex){
                    // done
                }
                textArea.setText(json);
                System.out.println(json);
            }
            event.consume();
        });

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 0", 100),
                        new PieChart.Data("Partition 1", 1));
        partitionMessageCountChart.setData(pieChartData);
    }
}
