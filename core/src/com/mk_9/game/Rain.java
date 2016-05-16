package com.mk_9.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rain extends Game {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 480;

    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;


    @Override
    public void create() {

        this.setScreen(new MyMenuScreen(this));

        batch = new SpriteBatch();
        font = new BitmapFont();


    }


    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {

        font.dispose();
        batch.dispose();

    }
}
