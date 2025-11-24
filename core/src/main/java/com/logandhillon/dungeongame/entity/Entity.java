package com.logandhillon.dungeongame.entity;

import java.util.ArrayList;

/**
 * abstract class for all entities
 * @author logan
 */
public abstract class Entity {
    private static final ArrayList<Entity> ENTITIES = new ArrayList<>();

    public Entity() {
        ENTITIES.add(this);
    }

    /**
     * Called to update the entity, ran every game tick.
     * @param dt delta time (amount of time between last frame)
     */
    public void update(float dt) {}

    /**
     * Called when this entity is destroyed.
     */
    public void destroy() {
        ENTITIES.remove(this);
    }

    /**
     * gets all (created) entities, should be used for rendering, updating, etc.
     * @return ArrayList of Entities
     */
    public static ArrayList<Entity> getEntities() {
        return ENTITIES;
    }
}
