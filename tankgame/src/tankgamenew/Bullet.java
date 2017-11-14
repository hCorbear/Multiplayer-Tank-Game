package tankgamenew;

import java.awt.*;

public class Bullet extends TickingObject {

    Game game;
    int facing;

    public Bullet(double x, double y, GlobalTexture tex, Game game) {
        super(x, y, tex);
        this.game = game;

        if(game.p.getDirection() == Direction.UP){
            facing = 1;
        } else if (game.p.getDirection() == Direction.RIGHT){
            facing = 2;
        } else if (game.p.getDirection() == Direction.LEFT){
            facing = 3;
        } else if (game.p.getDirection() == Direction.DOWN){
            facing = 4;
        }

    }


    public void tick() {
        if(facing == 1) {  // 1 is up
            y -= 10;
        } else if (facing == 2) { //2 is right
            x += 10;
        } else if (facing == 3) {  // 3 is left
            x -= 10;
        } else if (facing == 4) {  // 4 is down
            y += 10;
        }

    }


    public void render(Graphics g) {

        g.drawImage(game.bullet, (int) x, (int) y, null);

    }



}
