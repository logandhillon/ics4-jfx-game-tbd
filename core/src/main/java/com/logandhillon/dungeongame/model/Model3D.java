package com.logandhillon.dungeongame.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.logandhillon.dungeongame.entity.Entity;

/**
 * abstract class for all 3D models. extends {@link Entity}
 * @author logan
 */
public abstract class Model3D extends Entity {
    /**
     * @return the static {@link Model} of this model. this should not be used to render to the scene.
     * @see Model3D#getModelInstance()
     */
    public abstract Model getModel();

    /**
     * @return a dynamic {@link ModelInstance} of this model, that can have transformations and be rendered to the scene
     */
    public abstract ModelInstance getModelInstance();

    /**
     * Disposes of the model then destroys the entity.
     */
    @Override
    public void destroy() {
        this.getModel().dispose();
        super.destroy();
    }
}
