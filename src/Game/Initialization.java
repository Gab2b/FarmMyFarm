package Game;
import Main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Initialization {
    public static int start(ArrayList<Field> activeFields, Player activePlayer) {
            try {
                File saveDirectory = new File("../data/saves/");
                File[] saveFiles = saveDirectory.listFiles();

                if (saveFiles != null) {
                    for (int e = 0; e < saveFiles.length; e++) {
                        Scanner scanPlayerFile = new Scanner(new File(saveDirectory.getPath() + (e + 1) + "/player.txt"));

                        if (scanPlayerFile.hasNextLine()) {
                            int coin = Integer.parseInt(scanPlayerFile.nextLine());
                            while (scanPlayerFile.hasNextLine()) {
                                String line = scanPlayerFile.nextLine();

                                if (line.equals("endOfResources")) {
                                    break;
                                }

                                String[] parts = line.split(",");
                                String resourceName = parts[0];
                                int quantity = Integer.parseInt(parts[1]);

                                Resource.resourceFromFile(resourceName, quantity, activePlayer.ownedResources);
                            }
                            ArrayList<Field> ownedFields = new ArrayList<>();
                            activePlayer.playerFromFile(coin, activePlayer.ownedResources, activePlayer.ownedFields);
                        }

                        String fieldsDirectoryPath = saveDirectory.getPath() + (e + 1) + "/";
                        File fieldsDirectory = new File(fieldsDirectoryPath);

                        // INFOS FIELDS
                        if (fieldsDirectory.exists() && fieldsDirectory.isDirectory()) {
                            File[] fieldsFiles = fieldsDirectory.listFiles();
                            if (fieldsFiles != null) {
                                for (int i = 0; i < fieldsFiles.length; i++) {
                                    Scanner scanFieldInfos = new Scanner(new File(fieldsDirectoryPath + (i + 1) + ".txt"));
                                    Field newField = new Field(Integer.parseInt(scanFieldInfos.nextLine()), Integer.parseInt(scanFieldInfos.nextLine()), Integer.parseInt(scanFieldInfos.nextLine()), Boolean.parseBoolean(scanFieldInfos.nextLine()), scanFieldInfos.nextLine());
                                    activePlayer.ownedFields.add(newField);
                                    activeFields.add(newField);
                                    scanFieldInfos.close();
                                }
                            }
                        }
                    }
                }
                else {
                    activeFields.add(new Field(0,0,0,true,"ownedField"));
                    Main.activePlayer.addResource(new Wheat());
                    Main.activePlayer.addResource(new RedBeet());
                    Main.activePlayer.addResource(new Carrot());
                    Main.activePlayer.addResource(new Chicken());
                    Main.activePlayer.addResource(new Cow());
                    Main.activePlayer.addResource(new Pig());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return 0;
        }
}
