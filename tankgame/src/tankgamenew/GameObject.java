package tankgamenew;

public class GameObject {

    protected double x,y ;
    protected GlobalTexture tex;

    public GameObject(double x, double y, GlobalTexture tex) {

        this.x = x;
        this.y = y;
        this.tex = tex;
    }
}
