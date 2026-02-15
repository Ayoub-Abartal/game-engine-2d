package main.engine.core;

import javax.swing.*;

public class GameEngine {
    private Scene scene;

    public static void start(Scene scene, GameSettings gameSettings){
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(gameSettings.isResizable());
        window.setTitle(gameSettings.getTitle());

        GameLoop gameLoop = new GameLoop(gameSettings);

        gameLoop.setScene(scene);

        window.add(scene);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gameLoop.startGame();
    }

    }




