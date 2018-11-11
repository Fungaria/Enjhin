/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;
import java.util.Comparator;
import de.fungistudii.enjhin.GameScreen;

/**
 * holds an Array of Visuals (to which those self add them into when created)
 * Visuals are sorted based on z-Index (Reason why no
 * {@link SortedIteratingSystem} ) then rendered with update -> draw only one
 * instance of this class should exist should be created by {@link GameScreen}
 *
 * @author Samuel
 */
class RenderSystem extends IteratingSystem implements EntityListener {

    private static final ComponentMapper<VisualComponent> visualMapper = ComponentMapper.getFor(VisualComponent.class);

    /**
     * used to get the {@link Entity} a {@link Visual} is added to
     */
    private final ObjectMap<Visual, Entity> link;
    /**
     * all {@link Visual}s in the engine
     */
    private final Array<Visual> visuals;

    private boolean shouldSort;

    /**
     * pixels per meter ex: UNIT_SCALE 5: 5 pixels per meter
     */
    public float UNIT_SCALE = 20;

    public RenderSystem() {
        super(Family.all(VisualComponent.class).get(), 100);
        this.visuals = new Array<Visual>(true, 30);
        this.link = new ObjectMap<Visual, Entity>();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        visuals.clear();
        link.clear();
        engine.addEntityListener(Family.all(VisualComponent.class).get(), this);
    }

    void draw(SpriteBatch batch, float delta) {
        sort();

        for (int i = 0; i < visuals.size; ++i) {
            Visual visual = visuals.get(i);
            Entity entity = link.get(visual);
            VisualComponent visualComponent = visualMapper.get(entity);
            if (visualComponent != null && visualComponent.isVisible() && visualComponent.contains(visual, true)) //check for bullshit
            {
                visual.draw(batch, entity, UNIT_SCALE, delta);
            }
        }
    }

    /**
     * Call this if the sorting criteria have changed (zIndex changed). The
     * actual sorting will be delayed until the entities are processed.
     */
    public void forceSort() {
        shouldSort = true;
    }

    private void sort() {
        if (shouldSort) {
            visuals.sort(zComparator);
            shouldSort = false;
        }
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        visuals.clear();
        link.clear();
        for (Visual visual : visualMapper.get(entity)) {
            visuals.add(visual);
            link.put(visual, entity);
        }
        visuals.sort(zComparator);
    }
    
    /**
     * Comparator used for sorting visuals in ascending order (biggset z to
     * smallest z or the other way round??? who cares) .
     */
    private static Comparator<Visual> zComparator = new Comparator<Visual>() {
        @Override
        public int compare(Visual visual1, Visual visual2) {
            if (visual1 == null) {
                return 1;
            } else if (visual2 == null) {
                return -1;
            } else if (visual1.getZIndex() > visual2.getZIndex()) {
                return 1;
            } else if (visual1.getZIndex() < visual2.getZIndex()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    @Override
    public void entityAdded(Entity entity) {
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
