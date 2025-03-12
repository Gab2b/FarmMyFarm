package Game;

import java.util.ArrayList;

public class Player {
    static int playerNumber = 1;
    protected int id;
    protected int coin = 100;
    protected ArrayList<Resource> ownedResources = new ArrayList<Resource>();
    protected ArrayList<Field> ownedFields = new ArrayList<Field>();

    public Player() {
        this.id = playerNumber++;
        playerNumber += 1;
    }

    public void playerFromFile(int coin, ArrayList<Resource> ownedResources, ArrayList<Field> ownedFields) {
        this.coin = coin;
        this.ownedResources = ownedResources;
        this.ownedFields = ownedFields;
    }

    public boolean canPayThis(int price) {
        if (coin >= price) {
            return true;
        }
        return false;
    }

    public boolean buyThisField(Field newField) {
        if (canPayThis(newField.getPrice())) {
            coin -= newField.getPrice();
            ownedFields.add(newField);
            return true;
        }
        return false;
    }

    public boolean buyThisResource(ArrayList<Resource> newResources, int price) {
        if (canPayThis(price)) {
            coin -= price;
            ownedResources.addAll(newResources);
            return true;
        }
        return false;
    }

    public boolean gotThatResource(String resource) {
        for (int i = 0; i < ownedResources.size(); i++) {
            if (ownedResources.get(i).getName().equals(resource)) {
                return true;
            }
        }
        return false;
    }

    public boolean gotTheseResources(ArrayList<Resource> resources) {
        ArrayList<Resource> availableResources = new ArrayList<>(ownedResources);

        for (int i = 0; i < resources.size(); i++) {
            boolean resourcesFound = false;
            for (int j = 0; j < availableResources.size(); j++) {
                if (availableResources.get(j).getName().equals(resources.get(i).getName())) {
                    availableResources.remove(j);
                    resourcesFound = true;
                    break;
                }
            }
            if (!resourcesFound) {
                return false;
            }
        }

        ownedResources = new ArrayList<>(availableResources);
        return true;
    }

    public void addResource(Resource newResource) {
        ownedResources.add(newResource);
    }

    public void addResources(ArrayList<Resource> newResources) {
        ownedResources.addAll(newResources);
    }
}
