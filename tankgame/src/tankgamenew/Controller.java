package tankgamenew;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

public class Controller extends Component {

    Game game;
    GlobalTexture tex;

    private static ArrayList<Bullet> bulletList = new ArrayList<>();
    private static ArrayList<Wall> wallList = new ArrayList<>();
    private static ArrayList<Ammo> ammoList = new ArrayList<>();
    private static ArrayList<Explosion> explosions = new ArrayList<>();

    Bullet tempBullet;
    Wall tempWall;
    Ammo ammoCrate;

    private Image image, playBackground;
    private MediaTracker tracker;
    private URL url;

    Controller() {
    }

    public Controller(Game game, GlobalTexture tex) {
        this.game = game;
        this.tex = tex;

        addAmmo(new Ammo(750, 100, tex));
        addAmmo(new Ammo(590, 650, tex));

        for (int i = 0; i < 7; i++) {
            addWall(new Wall(565 + 32 * i, 500, tex));
            addWall(new Wall(100 + 32 * i, 500, tex));
            addWall(new Wall(990 + 32 * i, 500, tex));
            addWall(new Wall(532 + 32 * i, 500, tex));
            addWall(new Wall(660, 30 + 32 * i, tex));
            addWall(new Wall(660, 700 + 32 * i, tex));
            addWall(new Wall(390, 405 + 32 * i, tex));
            addWall(new Wall(900, 405 + 32 * i, tex));

        }

        for (int i = 0; i < 42; i++) {
            addWall(new Wall(1 + 32 * i, 1, tex));
            addWall(new Wall(1 + 32 * i, 992, tex));
        }

        for (int i = 0; i < 7; i++) {
            addWall(new Wall(200 + 32 * i, 702, tex));
            addWall(new Wall(100 + 32 * i, 286, tex));
            addWall(new Wall(1000 + 32 * i, 702, tex));
            addWall(new Wall(1000 + 32 * i, 286, tex));
            addWall(new Wall(520, 100 + 32 * i, tex));
            addWall(new Wall(520, 600 + 32 * i, tex));
            addWall(new Wall(800, 100 + 32 * i, tex));
            addWall(new Wall(800, 600 + 32 * i, tex));
        }

        for (int i = 0; i < 30; i++) {
            addWall(new Wall(1312, 30 + 32 * i, tex));
            addWall(new Wall(0, 30 + 32 * i, tex));
        }
    }

    public void tick() {

        for (int i = 0; i < bulletList.size(); i++) {
            tempBullet = bulletList.get(i);

            if (game.p.getBound().intersects(bulletList.get(i).getBounds())) {
                removeBullet(tempBullet);
                soundHit();
                game.hp1 -= 20;
                explosions.add(new Explosion(game.p.getX() + 30, game.p.getY() + 25, 4, 4 + 50, Color.red));

            } else if (game.p2.getBound().intersects(bulletList.get(i).getBounds())) {
                removeBullet(tempBullet);
                soundHit();
                explosions.add(new Explosion(game.p2.getX() + 30, game.p2.getY() + 25, 4, 4 + 50, Color.blue));
                game.hp2 -= 20;
            }

            tempBullet.tick();

        }

        if (game.hp1 == 0 || game.hp2 == 0 ) {
            ammoList.clear();
            addAmmo(new Ammo(750, 100, tex));
            addAmmo(new Ammo(590, 650, tex));
        }
        if (game.ammo1 == 0 && game.ammo2 == 0) {
            game.hp1 = 0;
            game.hp2 = 0;
        }

        ListIterator<Ammo> ammoListIterator = ammoList.listIterator();
        while (ammoListIterator.hasNext()) {
            Rectangle r2 = ammoListIterator.next().getBounds();
            if (r2.intersects(game.p.getBound())) {
                soundAmmo();
                ammoListIterator.remove();
                game.ammo1 += 10;

            }
            if (r2.intersects(game.p2.getBound())) {
                soundAmmo();
                ammoListIterator.remove();
                game.ammo2 += 10;
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            boolean remove = explosions.get(i).update();
            if (remove) {
                explosions.remove(i);
                i--;
            }
        }
    }

    public void renderMap(int w, int h, Graphics2D g2) {

        playBackground = getGameBG("res/background2.png");
        g2.drawImage(playBackground, 0, 0, 1920, 1000, this);

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).render(g2);
        }

        for (int i = 0; i < bulletList.size(); i++) {
            tempBullet = bulletList.get(i);
            tempBullet.render(g2);
        }

        for (int i = 0; i < wallList.size(); i++) {
            tempWall = wallList.get(i);
            tempWall.render(g2);
        }

        for (int i = 0; i < ammoList.size(); i++) {
            ammoCrate = ammoList.get(i);
            ammoCrate.render(g2);

        }

        game.p.render(g2);
        game.p2.render(g2);

        game.p.update();
        game.p2.update();
    }

    private void soundHit() {
        game.sound.playSound("res/bullet.wav");
    }

    private void soundAmmo() {
        game.sound.playSound("res/ammoup.wav");
    }

    public Image getGameBG(String name) {
        url = Game.class.getResource(name);
        image = getToolkit().getImage(url);

        try {
            tracker = new MediaTracker(this);
            tracker.addImage(image, 0);
            tracker.waitForID(0);
        } catch (InterruptedException e) { }
        return image;
    }

    public void addBullet(Bullet instance) {
        bulletList.add(instance);
    }

    public void removeBullet(Bullet instance) {
        bulletList.remove(instance);
    }

    private void addWall(Wall instance) {
        wallList.add(instance);
    }

    public static ArrayList<Wall> getWalls() {
        return wallList;
    }

    private void addAmmo(Ammo instance) {
        ammoList.add(instance);
    }

}
