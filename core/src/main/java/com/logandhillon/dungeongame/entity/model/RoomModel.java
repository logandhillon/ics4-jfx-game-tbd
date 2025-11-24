package com.logandhillon.dungeongame.entity.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * Builds a hollow room using six rectangles (floor, ceiling, walls).
 * Call getModelInstance() to place it in the world.
 */
public class RoomModel extends Model3D {
    private static final String[] PART_NAMES = { "floor", "ceiling", "back", "front", "left", "right"};

    private final Model model;
    private final ModelInstance instance;

    /**
     * a hollow room with 6 rectangles (walls, floor, ceiling)
     * @param pos position of room
     * @param s size of room
     * @param color color of all walls
     */
    public RoomModel(Vector3 pos, Vector3 s, Color color) {
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        Material mat = new Material(ColorAttribute.createDiffuse(color));
        long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

        // 3d array of all ordered points for room. indices: ( part, plane, coordinate )
        float[][][] faces = {
            { {0,0,0},      {s.x,0,0},      {s.x,0,-s.z},   {0,0,-s.z},     {0f,1f,0f},     {0f,-1f,0f} },  // floor
            { {0,s.y,-s.z}, {s.x,s.y,-s.z}, {s.x,s.y,0},    {0,s.y,0},      {0f,-1f,0f},    {0f,1f,0f}  },  // ceiling
            { {0,0,0},      {0,s.y,0},      {s.x,s.y,0},    {s.x,0,0},      {0f,0f,1f},     {0f,0f,-1f} },  // back
            { {s.x,0,-s.z}, {s.x,s.y,-s.z}, {0,s.y,-s.z},   {0,0,-s.z},     {0f,0f,-1f},    {0f,0f,1f}  },  // front
            { {0,0,-s.z},   {0,s.y,-s.z},   {0,s.y,0},      {0,0,0},        {1f,0f,0f},     {-1f,0f,0f} },  // left
            { {s.x,0,0},    {s.x,s.y,0},    {s.x,s.y,-s.z}, {s.x,0,-s.z},   {-1f,0f,0f},    {1f,0f,0f}  },  // right
        };

        for (int i = 0; i < faces.length; i++) {
            // front-facing rect
            builder.part(PART_NAMES[i], GL20.GL_TRIANGLES, attrs, mat)
                   .rect(
                       faces[i][0][0], faces[i][0][1], faces[i][0][2],
                       faces[i][1][0], faces[i][1][1], faces[i][1][2],
                       faces[i][2][0], faces[i][2][1], faces[i][2][2],
                       faces[i][3][0], faces[i][3][1], faces[i][3][2],
                       faces[i][4][0], faces[i][4][1], faces[i][4][2]
                   );

            // back-facing rect
            builder.part(PART_NAMES[i] + "_back", GL20.GL_TRIANGLES, attrs, mat)
                   .rect(
                       faces[i][3][0], faces[i][3][1], faces[i][3][2],
                       faces[i][2][0], faces[i][2][1], faces[i][2][2],
                       faces[i][1][0], faces[i][1][1], faces[i][1][2],
                       faces[i][0][0], faces[i][0][1], faces[i][0][2],
                       faces[i][5][0], faces[i][5][1], faces[i][5][2]
                   );
        }

        model = builder.end();
        instance = new ModelInstance(model);
        this.moveTo(pos);
    }

    public Model getModel() {
        return model;
    }

    public ModelInstance getModelInstance() {
        return instance;
    }
}
