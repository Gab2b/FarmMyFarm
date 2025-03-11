package Game;

import Main.Main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Loop extends Application{
    @FXML
    private GridPane gamegrid;

    @FXML
    private Label wikilabel;

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
    private Label warningLabel;

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

        controller.wikilabel.setOnMouseClicked(event -> {
            System.out.println("Clicked on wikilabel");
        });

        for (int i = 0; i < controller.gamegrid.getChildren().size(); i++) {
            Pane pane = (Pane) controller.gamegrid.getChildren().get(i);
            if (Main.activeFields.size()-1 >= i ) {
                pane.getStyleClass().remove("emptyPane");
                pane.getStyleClass().add(Main.activeFields.get(i).getProperties());
            }
            int paneIndex = i;
            pane.setOnMouseClicked(event -> {
                if (Main.activeFields.size()-1 >= paneIndex) {
                    System.out.println(Main.activeFields.get(paneIndex));
                    System.out.println(pane.getStyleClass());
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
                                                pane.getStyleClass().add("filledField");
                                                Main.activePlayer.ownedResources.remove(Main.activePlayer.ownedResources.get(j));
                                                Main.activeFields.get(paneIndex).setFilled(true, Main.activePlayer.ownedResources.get(j));
                                                fieldStage.close();
                                            }
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
                                                Main.activeFields.get(paneIndex).setFilled(true, Main.activePlayer.ownedResources.get(j));
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
                                Class<?> clazz = null;
                                try {
                                    clazz = Class.forName("Game." + Main.activeFields.get(paneIndex).getResourceFilled());
                                    if (Animal.class.isAssignableFrom(clazz)) {
                                        Animal animal = (Animal) clazz.getDeclaredConstructor().newInstance();
                                        if (Main.activeFields.get(paneIndex).getResource().isReady()) {
                                            System.out.println("Ready");
                                        }
                                        else {
                                            System.out.println("Not ready");
                                            System.out.println(animal.getRemainingTime());
                                        }
                                    } else {
                                        System.out.println(Main.activeFields.get(paneIndex).getResourceFilled() + " n'est PAS une sous-classe d'Animal.");
                                    }
                                } catch (ClassNotFoundException  | InvocationTargetException  | InstantiationException  | IllegalAccessException  | NoSuchMethodException ex ) {
                                    System.out.println("Error is not an Animal ");
                                    throw new RuntimeException(ex);
                                }
                            }
                            else {
                                fieldController.warningLabel.setText("/!\\ Field is empty");
                            }
                        });

                        fieldStage.setScene(fieldScene);
                        fieldStage.setTitle("Field " + (paneIndex+1));

                        fieldStage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FarmMyFarm");

        primaryStage.show();

    }

    public static void main(String[] args) {
        boolean loop = true;
        launch(args);

//        while (loop) {
//
//        }
    }
}
