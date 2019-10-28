package com.riguz.eventhub.browser;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.riguz.eventhub.browser.consumer.EventProcessorHostFactory;
import com.riguz.eventhub.browser.consumer.EventProcessorFactory;
import com.riguz.eventhub.browser.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(("main.fxml")));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Azure EventHub Browser");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();

        final EventProcessorHost host = EventProcessorHostFactory.createHost();
        final MainController controller = fxmlLoader.getController();

        primaryStage.setOnCloseRequest((event -> {
            System.out.println("Unregistering event processors...");
            host.unregisterEventProcessor();
        }));
        final EventProcessorFactory eventProcessorFactory = new EventProcessorFactory(controller.data);

        host.registerEventProcessorFactory(eventProcessorFactory);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
