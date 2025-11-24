package com.logandhillon.dungeongame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * contains all code for the player entity, first-person camera, movement, etc.
 * @author logan
 */
public class Player extends Entity {
    private final PerspectiveCamera cam;

    private static final float MOVE_SPEED = 5f; // units per second
    private static final float MOUSE_SENS = 12.5f; // degrees per second

    /**
     * a Player contains all code for the first-person camera, movement, etc.
     * @param spawnAt the player's spawn point
     */
    public Player(Vector3 spawnAt) {
        super();

        // camera setup
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(spawnAt);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    @Override
    public void update(float dt) {
        // fwd/bck movement
        Vector3 fwd = new Vector3(cam.direction.x, cam.direction.y, 0).nor();
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            cam.position.add(fwd.scl(MOVE_SPEED * dt));
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            cam.position.sub(fwd.scl(MOVE_SPEED * dt));

        // get vector perpendicular to fwd (R) and add/subtract depending on direction
        Vector3 right = fwd.crs(Vector3.Z).nor();
        // L/R movement
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            cam.position.add(right.scl(MOVE_SPEED * dt));
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            cam.position.sub(right.scl(MOVE_SPEED * dt));

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            cam.position.y += MOVE_SPEED * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            cam.position.y -= MOVE_SPEED * dt;

        // mouse look
        Gdx.input.setCursorCatched(true);
        float dx = Gdx.input.getDeltaX() * MOUSE_SENS * dt;
        float dy = Gdx.input.getDeltaY() * MOUSE_SENS * dt;
        cam.direction.rotate(Vector3.Z, dx);    // yaw
        cam.direction.rotate(cam.direction.cpy().crs(Vector3.Z).nor(), dy); // pitch

        cam.update();
    }

    public PerspectiveCamera getCam() {
        return cam;
    }

    /**
     * resizes and updates the viewport
     * @param width new width of viewport
     * @param height new height of viewport
     */
    public void resizeViewport(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }
}
