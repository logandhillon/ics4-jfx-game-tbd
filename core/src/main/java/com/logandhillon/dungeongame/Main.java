package com.logandhillon.dungeongame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.logandhillon.dungeongame.model.IModel3D;
import com.logandhillon.dungeongame.model.RoomModel;

import java.util.ArrayList;


public class Main extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private Environment env;

    private final ArrayList<IModel3D> models = new ArrayList<>();
    private ModelBatch modelBatch;

    @Override
    public void create() {
        // Camera setup
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 5f, 0f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        // Lighting
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        env.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        // Model creation
        RoomModel room = new RoomModel(2f, Color.BLUE);
        models.add(room);
        room.getModelInstance().transform.translate(2,1,0);

        modelBatch = new ModelBatch();
    }

    @Override
    public void render() {
        update();
        draw();
    }

    /**
     * handles graphical rendering/drawing to screen. runs after {@link Main#update()}
     */
    private void draw() {
        Gdx.gl.glViewport(0, 0, (int)cam.viewportWidth*2, (int)cam.viewportHeight*2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        for (IModel3D model: models)
            modelBatch.render(model.getModelInstance(), env);
        modelBatch.end();
    }

    /**
     * non-graphics related updates, called every tick. (e.g. handling inputs)
     */
    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        float moveSpeed = 5f * dt;  // movement speed (units per second)
        float rotateSpeed = 25f * dt;  // rotation speed (degrees per second)

        // fwd/bck movement
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            cam.position.add(cam.direction.cpy().scl(moveSpeed));
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            cam.position.sub(cam.direction.cpy().scl(moveSpeed));

        // get vector perpendicular to fwd (R) and add/subtract depending on direction
        Vector3 right = cam.direction.cpy().crs(Vector3.Z).nor().scl(moveSpeed);
        // L/R movement
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            cam.position.add(right);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            cam.position.sub(right);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            cam.position.y += moveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            cam.position.y -= moveSpeed;

        // mouse look
        Gdx.input.setCursorCatched(true);
        float dx = Gdx.input.getDeltaX() * 0.5f * rotateSpeed;
        float dy = Gdx.input.getDeltaY() * 0.5f * rotateSpeed;
        cam.direction.rotate(Vector3.Z, dx);    // yaw
        cam.direction.rotate(cam.direction.cpy().crs(Vector3.Z).nor(), dy); // pitch

        cam.update();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        for (IModel3D model: models)
            model.getModel().dispose();
    }
}
