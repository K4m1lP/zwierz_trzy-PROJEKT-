package adds;
public enum MapDirection {
    NORTH(new Vector2d(0, 1)),
    NORTH_EAST(new Vector2d(1,1)),
    EAST(new Vector2d(1, 0)),
    SOUTH_EAST(new Vector2d(1,-1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTH_WEST(new Vector2d(-1,-1)),
    WEST(new Vector2d(-1, 0)),
    NORTH_WEST(new Vector2d(-1,1));
    final Vector2d dirVector;
    private static final MapDirection[] myValues = values();
    MapDirection(Vector2d dirVector) { this.dirVector = dirVector; }
    public MapDirection setTo(int x) {return myValues[x];}
    public Vector2d toUnitVector() {return dirVector;}
}
