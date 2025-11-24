package com.logandhillon.dungeongame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.logandhillon.dungeongame.entity.Entity;
import com.logandhillon.dungeongame.entity.Player;
import com.logandhillon.dungeongame.entity.model.Model3D;
import com.logandhillon.dungeongame.entity.model.RoomModel;

import java.util.ArrayList;

/**
 * Main class for dungeon game.
 * Handles bootstrapping, updating (rendering & ticking) and disposing.
 * @author logan
 */
public class Main extends ApplicationAdapter {
    private Player player;
    private Environment env;

    private final ArrayList<Model3D> models = new ArrayList<>();
    private ModelBatch modelBatch;

    @Override
    public void create() {
        player = new Player();

        // Lighting
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        env.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        // Model creation
        models.add(new RoomModel(new Vector3(0,0,0),
                                 new Vector3(4f,4f,5f),
                                 Color.RED));

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
        PerspectiveCamera cam = player.getCam();
        Gdx.gl.glViewport(0, 0, (int)cam.viewportWidth*2, (int)cam.viewportHeight*2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(player.getCam());
        for (Model3D model: models)
            modelBatch.render(model.getModelInstance(), env);
        modelBatch.end();
    }

    /**
     * non-graphics related updates, called every tick. (e.g. handling inputs)
     */
    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        // update all entities
        for (Entity entity: Entity.getEntities())
            entity.update(dt);
    }

    @Override
    public void resize(int width, int height) {
        player.resizeViewport(width, height);
    }

    @Override
    public void dispose() {
        modelBatch.dispose();

        // destroy all (active) entities
        for (Entity entity: Entity.getEntities())
            entity.destroy();
    }
}
