package Main;

import Game.*;

import java.util.ArrayList;

public class Main {
    static public ArrayList<Field> activeFields = new ArrayList<>();
    static public Player activePlayer = new Player();

    public static void main(String[] args) {
        Initialization.start(activeFields, activePlayer);
        for (int i = 0; i < activeFields.size(); i++) {
            System.out.println("+1");
        }
        Loop.main(args);
    }
}