package Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Initialization {
    public int start(ArrayList<Player> activePlayer, ArrayList<Field> activeFields) {
            try {

                File saveDirectory = new File("../data/saves/");
                File[] saveFiles = saveDirectory.listFiles();

                if (saveFiles != null) {
                    for (int e = 0; e < saveFiles.length; e++) {
                        Scanner scanRestaurantFile = new Scanner(new File(saveDirectory.getPath() + (e + 1) + "/player.txt"));

                        String fieldsDirectoryPath = saveDirectory.getPath() + (e + 1) + "/";
                        File fieldsDirectory = new File(fieldsDirectoryPath);

                        // INFOS FIELDS
                        if (fieldsDirectory.exists() && fieldsDirectory.isDirectory()) {
                            File[] fieldsFiles = fieldsDirectory.listFiles();
                            if (fieldsFiles != null) {
                                for (int i = 0; i < fieldsFiles.length; i++) {
                                    Scanner scanDishInfos = new Scanner(new File(fieldsDirectoryPath + (i + 1) + ".txt"));
                                    Field newField = new Field(scanDishInfos.nextLine(), scanDishInfos.nextLine(), Double.parseDouble(scanDishInfos.nextLine()), Double.parseDouble(scanDishInfos.nextLine()), scanDishInfos.nextLine(), scanDishInfos.nextLine(), Boolean.parseBoolean(scanDishInfos.nextLine()), scanDishInfos.nextLine(), Double.parseDouble(scanDishInfos.nextLine()), Double.parseDouble(scanDishInfos.nextLine()));
                                    dishesList.add(dish);
                                    scanDishInfos.close();
                                }
                            }
                        }

                        scanRestaurantFile.close();
                        scanMenuFiles.close();
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return 0;
        }
}
