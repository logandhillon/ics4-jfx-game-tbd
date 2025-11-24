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

    private static final float FOV_Y = 67;

    private static final float MOVE_SPEED = 5f; // units per second
    private static final float MOUSE_SENS = 12.5f; // degrees per second

    /**
     * a Player contains all code for the first-person camera, movement, etc.
     */
    public Player() {
        super();
        cam = new PerspectiveCamera(FOV_Y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 5f, 0f); // relative Z-plane inverse position (NOT POSITION!)
        cam.lookAt(0f, 0f, 0f); // initial facing vector
        cam.near = 1f;
        cam.far = 300f;
        cam.position.z -= 2f; // move player up (so they aren't short)
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

        // DEBUG: lets player fly up, remember to add gravity later
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            cam.position.z -= MOVE_SPEED * dt;

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
