package main.engine.core;


// the Game Loop shouldn't know about the player
// but it should know about the scene


import main.engine.core.GameSettings;

public class GameLoop implements Runnable{

    private double nanoPerFrame;
    private volatile boolean paused = false;
    // referencing a scene
    private Scene scene; // change it to final later so the user cant modify the scene while the game loop is running
    private Thread gameThread;

    private GameSettings gameSettings;

    public GameLoop(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        this.nanoPerFrame = 1_000_000_000.0 / gameSettings.getFps() ;
    }

    public void startGame(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    public void pauseGame(){
        this.paused = !paused;
    }

    public void resumeGame(){
        this.paused = !paused;
    }
    @Override
    public void run() {
        double lastTime = System.nanoTime();

        double nextDrawTime = System.nanoTime() + nanoPerFrame;

        // checking for null safety
        while(gameThread != null && scene!=null){
            double currentTime = System.nanoTime();
            double delta = (currentTime - lastTime) / nanoPerFrame;
            lastTime = currentTime;

            scene.update(delta);
            scene.repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1_000_000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += nanoPerFrame;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopGame(){
        gameThread = null;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }
}
