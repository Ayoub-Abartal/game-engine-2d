package main.engine.core;


// the Game Loop shouldn't know about the player
// but it should know about the scene

public class GameLoop implements Runnable{

    private double nanoPerFrame;
    private volatile boolean paused = false;

    private volatile boolean running = false;
    // referencing a scene
    //private Scene scene; // change it to final later so the user cant modify the scene while the game loop is running
    
    private final SceneManager sceneManager; 

    private Thread gameThread;

    public GameLoop(GameSettings gameSettings,SceneManager sceneManager){
        this.sceneManager = sceneManager;
        this.nanoPerFrame = 1_000_000_000.0 / gameSettings.getFps();
    }

    public void startGame(){
        running = true;
        gameThread= new Thread(this,"GameLoop");
        gameThread.start();
    }

    public void pauseGame(){
        this.paused = true;
    }

    public void resumeGame(){
        this.paused = false;
    }

    @Override
    public void run() {
        double lastTime = System.nanoTime();

        double nextDrawTime = System.nanoTime() + nanoPerFrame;

        while(running){

            if(paused){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }

                continue;
            }

            double currentTime = System.nanoTime();
            double delta = (currentTime - lastTime) / nanoPerFrame;
            lastTime = currentTime;

            sceneManager.update(delta);
            sceneManager.render();

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
        running = false;
    }
    
}
