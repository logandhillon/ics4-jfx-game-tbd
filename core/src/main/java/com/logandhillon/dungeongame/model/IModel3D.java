package com.logandhillon.dungeongame.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public interface IModel3D {
    /**
     * @return the static {@link Model} of this model. this should not be used to render to the scene.
     * @see IModel3D#getModelInstance()
     */
    Model getModel();

    /**
     * @return a dynamic {@link ModelInstance} of this model, that can have transformations and be rendered to the scene
     */
    ModelInstance getModelInstance();
}
