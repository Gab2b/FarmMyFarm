package Game;

import java.util.ArrayList;

public class Player {
    static int playerNumber = 1;
    protected int id;
    protected int coin;
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
        if (coin > price) {
            return true;
        }
        return false;
    }

    public void buyThisField(Field newField, int price) {
        if (canPayThis(price)) {
            coin -= price;
            ownedFields.add(newField);
        }
        else {
            System.out.println("You don't have enough money!");
        }
    }

    public void buyThisResource(Resource newResource, int price) {
        if (canPayThis(price)) {
            coin -= price;
            ownedResources.add(newResource);
        }
        else {
            System.out.println("You don't have enough money!");
        }
    }

    public void showInfos() {
        System.out.println("Player " + id + " has " + coin + " coins and " + ownedResources.size() + " resources");
        for (Resource resource : ownedResources) {
            System.out.println(resource);
        }
    }

    public boolean gotThatResource(String resource) {
        for (int i = 0; i < ownedResources.size(); i++) {
            if (ownedResources.get(i).getName().equals(resource)) {
                return true;
            }
        }
        return false;
    }

    public void addResource(Resource newResource) {
        ownedResources.add(newResource);
    }
}
