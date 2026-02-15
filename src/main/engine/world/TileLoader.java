package main.engine.world;

import main.engine.graphics.TileSpriteSheet;

import javax.imageio.ImageIO;
import java.io.IOException;

public class TileLoader {

    private TileLoader(){
        throw new IllegalStateException("Utility Class");
    }

    public static void loadFromSpritesheet(TileManager tileManager, String spriteSheetPath, int tileSize, TileDefinition[] tileDefinitionList){
        // load the spritesheet image
        TileSpriteSheet spriteSheet = new TileSpriteSheet(spriteSheetPath,tileSize, tileSize);
        // loop through tiledefintion
        for(TileDefinition tileDefinition: tileDefinitionList){
        // extract the sub image at col*tileSize, row*tileSize, tilesize, tilesize
            Tile tile = new Tile(spriteSheet.getTile(tileDefinition.getColumn(), tileDefinition.getRow()), tileDefinition.isSolid());
        // call the tilemanager register method
            tileManager.registerTile(tileDefinition.getId(),tile);
        }
    }

    public static void loadSingleTile(TileManager tileManager, String imagePath, int id, boolean solid){
        try{
            Tile tile = new Tile( ImageIO.read(TileLoader.class.getResourceAsStream(imagePath)), solid);
            tileManager.registerTile(id, tile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
