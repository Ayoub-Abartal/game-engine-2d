package main.engine.core;

/**
 * Manages scene lifecycle and transitions.
 * Currently wraps a single scene; future versions will support scene switching.
 */
public class SceneManager {

    private final Scene currentScene;

    public SceneManager(Scene scene){
        if(scene == null){
            throw new IllegalArgumentException("Scene cannot be null");
        }
        this.currentScene = scene;
    }

    public void update(double delta){
        currentScene.update(delta);
    } 
    
    public void render(){
        currentScene.repaint();
    } 

    public Scene getCurrentScene(){
        return currentScene;
    }
    
}
