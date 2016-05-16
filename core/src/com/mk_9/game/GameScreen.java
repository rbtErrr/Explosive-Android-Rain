package com.mk_9.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by mk 9 on 15.05.2016.
 */
public class GameScreen implements Screen {
    Rain game;  // в гейме батч и камера, в рендере их через гейм и в конструкторе из-за того что нет магии типа Криате

    OrthographicCamera camera;

    Texture batsaberImg;
    Texture rbtImg;
    Texture rbt2Img;
    Texture rbt3Img;
    Texture explImg;


    Rectangle batsaber;
    Rectangle expl;

    Vector3 touchPosition;

    Music bomb;

    Array<Rectangle> robots;

    long lastRobotDrop;

    int n;

    Music music;


    public GameScreen(Rain game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        batsaberImg = new Texture(Gdx.files.internal("bat.png"));
        rbtImg = new Texture(Gdx.files.internal("rbtImg.png"));
        rbt2Img = new Texture(Gdx.files.internal("rbt2Img.png"));
        rbt3Img = new Texture(Gdx.files.internal("rbt3Img.png"));
        explImg = new Texture(Gdx.files.internal("explImg.png"));

        bomb = Gdx.audio.newMusic(Gdx.files.internal("batt.mp3"));
        bomb.setVolume(2f);


        batsaber = new Rectangle();
        batsaber.x = 480 / 2 - 64 / 2;
        batsaber.y = 20;
        batsaber.height = 86;
        batsaber.width = 64;


        expl = new Rectangle();
        expl.height = 70;
        expl.width = 90;

        touchPosition = new Vector3();

        robots = new Array<Rectangle>();
        createRobot();

        music = Gdx.audio.newMusic(Gdx.files.internal("mus.mp3"));


        n = MathUtils.random(0, 2);
    }


    private void createRobot() {
        Rectangle rbt = new Rectangle();
        rbt.x = MathUtils.random(0, Rain.WIDTH - 64);
        rbt.y = Rain.HEIGHT;
        rbt.width = 64;
        rbt.height = 96;
        robots.add(rbt);

        lastRobotDrop = TimeUtils.nanoTime();


    }

    @Override
    public void show() {
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(batsaberImg, batsaber.x, batsaber.y);

        if (n == 0) {
            for (Rectangle robot : robots) {
                game.batch.draw(rbtImg, robot.x, robot.y);
//            int n = MathUtils.random(3);
//            if (n == 0) {
//
//            } else if (n == 1) {
//                batch.draw(rbt2Img, robot.x, robot.y);
//            } else {
//                batch.draw(rbt3Img, robot.x, robot.y);
//            }

            }

        } else if (n == 1) {
            for (Rectangle robot : robots) {
                game.batch.draw(rbt2Img, robot.x, robot.y);
            }
        } else if (n == 2) {
            for (Rectangle robot : robots) {
                game.batch.draw(rbt3Img, robot.x, robot.y);
            }
        }


        if (Gdx.input.isTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            batsaber.x = touchPosition.x - 64 / 2;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            batsaber.x -= 300 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            batsaber.x += 300 * Gdx.graphics.getDeltaTime();
        if (batsaber.x < 0) batsaber.x = 0;
        if (batsaber.x > 480) batsaber.x = 480 - 64 / 2;

        if (TimeUtils.nanoTime() - lastRobotDrop > 1000000000) createRobot();

        Iterator<Rectangle> iterator = robots.iterator();
        while (iterator.hasNext()) {

            Rectangle rbt = iterator.next();
            rbt.y -= 250 * Gdx.graphics.getDeltaTime();
            if (rbt.y + 64 / 2 < 0) {
                game.batch.draw(explImg, rbt.x, rbt.y);
                iterator.remove();
            }

            if (rbt.overlaps(batsaber)) {
                game.batch.draw(explImg, rbt.x, rbt.y);
                bomb.play();
                iterator.remove();
            }
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batsaberImg.dispose();
        rbtImg.dispose();
        rbt2Img.dispose();
        rbt3Img.dispose();
        bomb.dispose();
        music.dispose();
        explImg.dispose();


    }
}
