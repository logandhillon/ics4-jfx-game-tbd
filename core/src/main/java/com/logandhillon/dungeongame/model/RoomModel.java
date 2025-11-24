package com.logandhillon.dungeongame.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.Color;

/**
 * Builds a hollow room using six rectangles (floor, ceiling, walls).
 * Call getModelInstance() to place it in the world.
 */
public class RoomModel extends Model3D {
    private final Model model;
    private final ModelInstance instance;

    /**
     * a hollow room with 6 rectangles (walls, floor, ceiling)
     * @param s size of room
     * @param color color of walls
     */
    public RoomModel(float s, Color color) {
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        Material mat = new Material(ColorAttribute.createDiffuse(color));
        long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

        builder.part("floor", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   -s, 0f, -s,
                   s, 0f, -s,
                   s, 0f, s,
                   -s, 0f, s,
                   0f, 1f, 0f
               );

        builder.part("ceiling", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   -s, s, s,
                   s, s, s,
                   s, s, -s,
                   -s, s, -s,
                   0f, -1f, 0f
               );

        builder.part("back", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   -s, 0f, -s,
                   -s, s, -s,
                   s, s, -s,
                   s, 0f, -s,
                   0f, 0f, 1f
               );

        builder.part("front", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   s, 0f, s,
                   s, s, s,
                   -s, s, s,
                   -s, 0f, s,
                   0f, 0f, -1f
               );

        builder.part("left", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   -s, 0f, s,
                   -s, s, s,
                   -s, s, -s,
                   -s, 0f, -s,
                   1f, 0f, 0f
               );

        builder.part("right", GL20.GL_TRIANGLES, attrs, mat)
               .rect(
                   s, 0f, -s,
                   s, s, -s,
                   s, s, s,
                   s, 0f, s,
                   -1f, 0f, 0f
               );

        model = builder.end();
        instance = new ModelInstance(model);
    }

    public Model getModel() {
        return model;
    }

    public ModelInstance getModelInstance() {
        return instance;
    }
}
