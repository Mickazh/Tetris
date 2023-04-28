package Pieces;

public class Coordonnees {
    private int x, y;

    public Coordonnees(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void plus1X(){
        ++this.x;
    }

    public void moins1X(){
        --this.x;
    }

    public void plus1Y(){
        ++this.y;
    }

    public void moins1Y(){
        --this.y;
    }

    @Override
    public String toString() {
        return "Coordonnees [x=" + x + ", y=" + y + "]";
    }

}
