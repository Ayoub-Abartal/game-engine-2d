package main.engine.core;

import main.engine.api.Collidable;
import main.engine.api.Drawable;
import main.engine.api.Interactable;
import main.engine.api.Updatable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// the scene is the container of game objects inside a gameloop
// this allows the levels to have multiple scenes
// also made the update method of each object called explicitly

// interface segragation
// observer pattern

public class Scene extends JPanel {

    protected List<Updatable> updatables;

    protected List<Drawable> drawables;

    protected List<Interactable> interactables;

    protected List<Collidable> collidables;

    protected GameSettings gameSettings;

    public Scene(GameSettings gameSettings){
        this.drawables = new ArrayList<>();
        this.updatables = new ArrayList<>();
        this.interactables = new ArrayList<>();
        this.collidables = new ArrayList<>();
        this.gameSettings = gameSettings;
    }

    public void addGameObject(Object object){
        if(object instanceof Updatable updatable)
            updatables.add(updatable);
        if(object instanceof Drawable drawable)
            drawables.add(drawable);
        if(object instanceof Interactable interactable)
            interactables.add(interactable);
        if(object instanceof Collidable collidable)
            collidables.add(collidable);
    }

    public void update(double delta){
        for(Updatable updatable: updatables){
            updatable.update(delta);
        }
    }

    public void draw(Graphics2D graphics2D){
        for(Drawable drawable: drawables){
            drawable.draw(graphics2D);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
         Graphics2D graphics2D = (Graphics2D) g;
        this.draw(graphics2D);
        graphics2D.dispose();

    }

    public List<Collidable> getCollidables(){ return this.collidables; }
}
