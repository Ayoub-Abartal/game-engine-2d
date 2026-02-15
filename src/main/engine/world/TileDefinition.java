package main.engine.world;

/**
 * TileDefinition - Data Class
 */
public class TileDefinition {
    private final int id;
    private final int column;
    private final int row;
    private final boolean solid;

    public TileDefinition(int id, int column, int row, boolean solid){
        this.id = id;
        this.column = column;
        this.row = row;
        this.solid = solid;
    }

    public int getId() {
        return id;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isSolid() {
        return solid;
    }
}
