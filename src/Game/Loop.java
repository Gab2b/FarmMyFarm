package Game;

import Main.Main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

public class Loop extends Application{
    @FXML
    private GridPane gamegrid;

    @FXML
    private GridPane invgrid;

    @FXML
    private Label balance;

    @FXML
    private ComboBox<String> selectSeeds;

    @FXML
    private ComboBox<String> selectAnimals;

    @FXML
    private Button confirmPlant;

    @FXML
    private Button confirmRetrieve;

    @FXML
    private Button confirmRaise;

    @FXML
    private Button confirmFeed;

    @FXML
    private Label warningLabel;

    @FXML
    private Label inventoryLabel;

    @FXML
    private Label market;

    @FXML
    private Label storePrompt;

    @FXML
    private ComboBox<String> sellSelect;

    @FXML
    private ComboBox<String> sellAmount;

    @FXML
    private ComboBox<String> buySelect;

    @FXML
    private ComboBox<String> buyAmount;

    @FXML
    private Label sellConfirm;

    @FXML
    private Label buyConfirm;

    @FXML
    private Label buyPrice;

    @FXML
    private Label sellPrice;

    @FXML
    private Button newButton;

    @FXML
    private Label newLabel;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../data/fxml/map.fxml"));
        Parent root = loader.load();
        Loop controller = loader.getController();

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                Pane pane = new Pane();
                pane.setPrefHeight(200.0);
                pane.setPrefWidth(200.0);
                pane.getStyleClass().add("emptyPane");
                controller.gamegrid.setRowIndex(pane, row);
                controller.gamegrid.setColumnIndex(pane, col);
                controller.gamegrid.add(pane, col, row);
            }
        }

        checkStatusPeriodically(controller);

        for (int i = 0; i < controller.gamegrid.getChildren().size(); i++) {
            Pane pane = (Pane) controller.gamegrid.getChildren().get(i);
            if (Main.activeFields.size()-1 >= i ) {
                pane.getStyleClass().remove("emptyPane");
                pane.getStyleClass().add(Main.activeFields.get(i).getProperties());
            }
            int paneIndex = i;

            pane.setOnMouseClicked(event -> {
                if (Main.activeFields.size()-1 >= paneIndex) {
                    try {
                        FXMLLoader fieldLoader = new FXMLLoader(getClass().getResource("../data/fxml/field.fxml"));
                        Parent fieldRoot = fieldLoader.load();
                        Scene fieldScene = new Scene(fieldRoot);
                        Loop fieldController = fieldLoader.getController();
                        Stage fieldStage = new Stage();

                        fieldController.confirmPlant.setOnAction(e -> {
                            if (!Main.activeFields.get(paneIndex).isFilled()) {
                                if (fieldController.selectSeeds.getValue() != null) {
                                    if (Main.activePlayer.gotThatResource(fieldController.selectSeeds.getValue())) {
                                        for (int j = 0; j < Main.activePlayer.ownedResources.size(); j++) {
                                            if (Main.activePlayer.ownedResources.get(j).getName().equals(fieldController.selectSeeds.getValue())) {
                                                pane.getStyleClass().remove("emptyPane");
                                                pane.getStyleClass().add(Main.activePlayer.ownedResources.get(j).getProperties());
                                                Main.activeFields.get(paneIndex).setFilled(true, Main.activePlayer.ownedResources.get(j));
                                                Main.activePlayer.ownedResources.remove(Main.activePlayer.ownedResources.get(j));
                                                break;
                                            }
                                            fieldStage.close();
                                        }
                                    } else {
                                        fieldController.warningLabel.setText("You don't have that seed");
                                    }
                                } else {
                                    fieldController.warningLabel.setText("/!\\ No seeds selected");
                                }
                            } else {
                                fieldController.warningLabel.setText("/!\\ Field is already filled");
                            }
                        });

                        fieldController.confirmRaise.setOnAction(e -> {
                            if (!Main.activeFields.get(paneIndex).isFilled()) {
                                if (fieldController.selectAnimals.getValue() != null) {
                                    if (Main.activePlayer.gotThatResource(fieldController.selectAnimals.getValue())) {
                                        for (int j = 0; j < Main.activePlayer.ownedResources.size(); j++) {
                                            if (Main.activePlayer.ownedResources.get(j).getName().equals(fieldController.selectAnimals.getValue())) {
                                                pane.getStyleClass().remove("emptyPane");
                                                pane.getStyleClass().add(Main.activePlayer.ownedResources.get(j).getProperties());
                                                Animal animal = (Animal) Main.activePlayer.ownedResources.get(j);
                                                Main.activeFields.get(paneIndex).setFilled(true, animal.retrieveAnimal());
                                                Main.activePlayer.ownedResources.remove(Main.activePlayer.ownedResources.get(j));
                                                fieldStage.close();
                                            }
                                        }
                                    } else {
                                        fieldController.warningLabel.setText("You don't have these animals");
                                    }
                                } else {
                                    fieldController.warningLabel.setText("/!\\ No animals selected");
                                }
                            } else {
                                fieldController.warningLabel.setText("/!\\ Field is already filled");
                            }
                        });

                        fieldController.confirmRetrieve.setOnAction(e -> {
                            if (Main.activeFields.get(paneIndex).isFilled()) {
                                Class<?> resourceClass = null;
                                try {
                                    resourceClass = Class.forName("Game." + Main.activeFields.get(paneIndex).getResourceFilled());
                                    if (Animal.class.isAssignableFrom(resourceClass)) {
                                        Animal animal = (Animal) Main.activeFields.get(paneIndex).getResource();
                                        if (animal.isAlive()) {
                                            fieldController.warningLabel.setText("Animal is dead..");
                                        }
                                        else {
                                            if (Main.activeFields.get(paneIndex).getResource().isReady()) {
                                                Main.activePlayer.addResource(animal.retrieveResource());
                                                if (Main.activeFields.get(paneIndex).getResource().reduceNumberOfResources()) {
                                                    Main.activeFields.get(paneIndex).setFilled(false, null);
                                                    pane.getStyleClass().clear();
                                                    pane.getStyleClass().add("ownedField");
                                                    fieldStage.close();
                                                }
                                                else {
                                                    animal.setCompletionTime(animal.getTimeToRecoltInMinutes());
                                                    fieldStage.close();
                                                }
                                            } else {
                                                fieldController.warningLabel.setText(animal.getRemainingTime());
                                            }
                                        }
                                    } else {
                                        if (Main.activeFields.get(paneIndex).getResource().isReady()) {
                                            Main.activePlayer.addResources(Main.activeFields.get(paneIndex).getResource().returnResources());
                                            Main.activeFields.get(paneIndex).setFilled(false, null);
                                            pane.getStyleClass().clear();
                                            pane.getStyleClass().add("ownedField");
                                            fieldStage.close();
                                        }
                                        else {
                                            fieldController.warningLabel.setText(Main.activeFields.get(paneIndex).getResource().getRemainingTime());
                                        }
                                    }
                                } catch (ClassNotFoundException ex ) {
                                    System.out.println("Error is not an Animal ");
                                    throw new RuntimeException(ex);
                                }
                            }
                            else {
                                fieldController.warningLabel.setText("/!\\ Field is empty");
                            }
                        });

                        fieldController.confirmFeed.setOnAction(e -> {
                            if (Main.activeFields.get(paneIndex).isFilled() && Main.activeFields.get(paneIndex).getResource() != null) {
                                if (Main.activeFields.get(paneIndex).getResource() instanceof Animal) {
                                    Animal animal = (Animal) Main.activeFields.get(paneIndex).getResource();
                                    if (animal.feed(Main.activePlayer.ownedResources)) {
                                        controller.gamegrid.getChildren().get(paneIndex).getStyleClass().clear();
                                        controller.gamegrid.getChildren().get(paneIndex).getStyleClass().add("ownedField");
                                        controller.gamegrid.getChildren().get(paneIndex).getStyleClass().add(animal.getProperties());
                                        fieldStage.close();
                                    }
                                    else {
                                        if (animal.isAlive()) {
                                            fieldController.warningLabel.setText("Animal is dead, your field is gone..");
                                        }
                                        else if (animal.isFed()){
                                            fieldController.warningLabel.setText("Animal is already fed");
                                        }
                                        else {
                                            fieldController.warningLabel.setText("You don't have the right food");
                                        }
                                    }
                                }
                            }
                        });

                        fieldStage.setScene(fieldScene);
                        fieldStage.setTitle("Field " + (paneIndex+1));

                        fieldStage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        FXMLLoader fieldLoader = new FXMLLoader(getClass().getResource("../data/fxml/buyfield.fxml"));
                        Parent fieldRoot = fieldLoader.load();
                        Scene fieldScene = new Scene(fieldRoot);
                        Loop fieldController = fieldLoader.getController();
                        Stage fieldStage = new Stage();

                        fieldController.newButton.setOnAction(e -> {
                            Field newField = new Field(GridPane.getRowIndex(controller.gamegrid.getChildren().get(paneIndex)),GridPane.getColumnIndex(controller.gamegrid.getChildren().get(paneIndex)), 100, true, "ownedField");
                            if (Main.activePlayer.buyThisField(newField)) {
                                pane.getStyleClass().remove("emptyPane");
                                Main.activeFields.add(newField);
                                pane.getStyleClass().add(Main.activeFields.get(paneIndex).getProperties());
                                fieldStage.close();
                            } else {
                                fieldController.newLabel.setVisible(true);
                            }
                        });

                        fieldStage.setScene(fieldScene);
                        fieldStage.setTitle("Unknown Field " + (paneIndex+1));
                        fieldStage.show();
                    }
                    catch ( IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
        }

        controller.market.setOnMouseClicked(e -> {
            try {
                FXMLLoader marketLoader = new FXMLLoader(getClass().getResource("../data/fxml/market.fxml"));
                Parent marketRoot = marketLoader.load();
                Scene marketScene = new Scene(marketRoot);
                Loop marketController = marketLoader.getController();
                Stage marketStage = new Stage();

                marketController.buySelect.setOnAction(event -> {
                    marketController.buyAmount.setDisable(false);
                    marketController.sellSelect.setDisable(true);
                });

                marketController.sellSelect.setOnAction(event -> {
                    marketController.sellAmount.setDisable(false);
                    marketController.buySelect.setDisable(true);
                });

                marketController.buyAmount.setOnAction(event -> {
                    try {
                        int buyAmount = Integer.parseInt(marketController.buyAmount.getValue());
                        Class<?> selectedClass = Class.forName("Game." + marketController.buySelect.getValue());
                        Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();
                        marketController.buyPrice.setText((newResource.price * buyAmount) + " \uD83D\uDCB0");

                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }

                });

                marketController.sellAmount.setOnAction(event -> {
                    try {
                        int sellAmount = Integer.parseInt(marketController.sellAmount.getValue());
                        Class<?> selectedClass = Class.forName("Game." + marketController.sellSelect.getValue());
                        Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();
                        marketController.sellPrice.setText((newResource.price * sellAmount) + " \uD83D\uDCB0");

                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }

                });

                marketController.buyConfirm.setOnMouseClicked( f -> {
                    try {
                        int buyAmount = Integer.parseInt(marketController.buyAmount.getValue());
                        Class<?> selectedClass = Class.forName("Game." + marketController.buySelect.getValue());

                        ArrayList<Resource> allResources= new ArrayList<>();
                        for (int i = 0; i < buyAmount; i++) {
                            Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();
                            allResources.add(newResource);
                        }

                        Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();

                        if (Main.activePlayer.buyThisResource(allResources, newResource.price * buyAmount)) {
                            marketStage.close();
                        }
                        else {
                            marketController.storePrompt.getStyleClass().clear();
                            marketController.storePrompt.getStyleClass().add("label");
                            marketController.storePrompt.getStyleClass().add("choiceLabel");
                            marketController.storePrompt.getStyleClass().add("warning");
                            marketController.storePrompt.setText("Can't afford this item.");
                        }

                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }


                });

                marketController.sellConfirm.setOnMouseClicked( f -> {
                    try {
                        int sellAmount = Integer.parseInt(marketController.sellAmount.getValue());
                        Class<?> selectedClass = Class.forName("Game." + marketController.sellSelect.getValue());

                        ArrayList<Resource> allResources= new ArrayList<>();
                        for (int i = 0; i < sellAmount; i++) {
                            Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();
                            allResources.add(newResource);
                        }

                        Resource newResource = (Resource) selectedClass.getDeclaredConstructor().newInstance();

                        if (Main.activePlayer.gotTheseResources(allResources)) {
                            Main.activePlayer.coin += (newResource.price * sellAmount);
                            marketStage.close();
                        }
                        else {
                            marketController.storePrompt.getStyleClass().clear();
                            marketController.storePrompt.getStyleClass().add("label");
                            marketController.storePrompt.getStyleClass().add("choiceLabel");
                            marketController.storePrompt.getStyleClass().add("warning");
                            marketController.storePrompt.setText("Missing required item.");
                        }

                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                marketStage.setScene(marketScene);
                marketStage.setTitle("Market");
                marketStage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controller.inventoryLabel.setOnMouseClicked(e -> {
            try {
                FXMLLoader invLoader = new FXMLLoader(getClass().getResource("../data/fxml/inventory.fxml"));
                Parent invRoot = invLoader.load();
                Scene invScene = new Scene(invRoot);
                Loop invController = invLoader.getController();
                Stage invStage = new Stage();

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 5; col++) {
                        StackPane pane = new StackPane();
                        pane.setPrefHeight(200.0);
                        pane.setPrefWidth(200.0);
                        pane.getStyleClass().add("inventory");
                        invController.invgrid.setRowIndex(pane, row);
                        invController.invgrid.setColumnIndex(pane, col);
                        invController.invgrid.add(pane, col, row);
                    }
                }

                Map<String, Integer> resourceCounts = new HashMap<>();
                Map<String, String> resourcePathToImg = new HashMap<>();

                for (int j = 0; j<Main.activePlayer.ownedResources.size(); j++) {
                    String resourceName = Main.activePlayer.ownedResources.get(j).getClass().getSimpleName();
                    String pathToImg = Main.activePlayer.ownedResources.get(j).getProperties();
                    resourceCounts.put(resourceName, resourceCounts.getOrDefault(resourceName, 0) + 1);
                    resourcePathToImg.put(resourceName, pathToImg);
                }

                int iteration = 0;
                for (Map.Entry<String, Integer> entry : resourceCounts.entrySet()) {

                    StackPane stackpane = (StackPane) invController.invgrid.getChildren().get(iteration);
                    Pane pane = new Pane();
                    pane.setMaxSize(100,100);

                    pane.getStyleClass().add("item-" + resourcePathToImg.get(entry.getKey()));

                    stackpane.getChildren().add(pane);


                    Label number = new Label();
                    number.setText(entry.getValue().toString());

                    Label name = new Label();
                    name.setText(entry.getKey() + " :");

                    stackpane.getChildren().add(number);
                    stackpane.getChildren().add(name);
                    StackPane.setMargin(number, new Insets(95, 0, 0, 95));
                    StackPane.setMargin(name, new Insets(95, 20, 0, 0));

                    iteration++;
                }

                invStage.setScene(invScene);
                invStage.setTitle("Inventory");
                invStage.show();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FarmMyFarm");

        primaryStage.show();
    }

    private void checkStatusPeriodically(Loop controller) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> checkFields(controller))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void checkFields(Loop controller) {
        for (int i = 0; i < Main.activeFields.size(); i++) {
            Field field = Main.activeFields.get(i);
            if (field.isFilled() && field.getResource() != null) {
                if (!(field.getResource() instanceof Animal)) {
                    if (field.getResource().checkIfReady()) {
                        controller.gamegrid.getChildren().get(i).getStyleClass().clear();
                        controller.gamegrid.getChildren().get(i).getStyleClass().add("ownedField");
                        controller.gamegrid.getChildren().get(i).getStyleClass().add(field.getResource().getGrownOne());
                    }
                }
                else {
                    Animal animal = (Animal) field.getResource();
                    if (animal.checkIfHungry()) {
                        controller.gamegrid.getChildren().get(i).getStyleClass().clear();
                        controller.gamegrid.getChildren().get(i).getStyleClass().add("ownedField");
                        if (animal.isAlive())
                        {
                            controller.gamegrid.getChildren().get(i).getStyleClass().add(field.getResource().getProperties().replace("healthy", "no"));
                        }
                        else {
                            controller.gamegrid.getChildren().get(i).getStyleClass().add(field.getResource().getProperties().replace("healthy", "hungry"));
                        }
                    }
                }
            }
        }

        controller.balance.setText("Balance : " + Main.activePlayer.coin + " \uD83D\uDCB0");
    }


    public static void main(String[] args) {
        boolean loop = true;
        launch(args);

//        while (loop) {
//
//        }
    }
}
