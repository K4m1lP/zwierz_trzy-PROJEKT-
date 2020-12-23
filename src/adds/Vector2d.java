package adds;
public class Vector2d {
    private final int x;
    private final int y;
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX(){ return this.x;}
    public int getY(){ return this.y;}
    public boolean precedes(Vector2d other) { return other.x >= this.x && this.y <= other.y; }
    public boolean follows(Vector2d other) { return this.x >= other.x && this.y >= other.y; }
    public Vector2d add(Vector2d other) { return new Vector2d(this.x+other.x,this.y+other.y); }
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode() { return 13 + (this.x * 31) + (this.y * 17); }
}
