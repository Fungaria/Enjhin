/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.fungistudii.enjhin.graphics.VisualComponent;

/**
 * Dunno whether its working
 *
 * @author Samuel
 */
public class TiledMapVisual extends OrthogonalTiledMapRenderer implements VisualComponent.Visual {

    private TiledMap map;
    private float zIndex;

    private float size;
    
    private OrthographicCamera cam;

    public TiledMapVisual(TiledMap map, OrthographicCamera cam, float zIndex, float unitscale) {
        super(map, unitscale);
        this.cam = cam;
        this.map = map;
        this.zIndex = zIndex;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        super.setMap(map);
        this.map = map;
    }

    @Override
    public float getZIndex() {
        return zIndex;
    }

    public void setZIndex(float zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public void draw(SpriteBatch batch, Entity entity, float unitscale, float delta) {
        setView(cam);
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                } else if (layer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) layer);
                } else{
                    renderObjects(layer);
                }
            }
        }
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            
            batch.draw(
                      textureObject.getTextureRegion(),
                      textureObject.getX()*unitScale,
                      textureObject.getY()*unitScale,
                      textureObject.getOriginX(),
                      textureObject.getOriginY(),
                      textureObject.getTextureRegion().getRegionWidth()*unitScale,
                      textureObject.getTextureRegion().getRegionHeight()*unitScale,
                      textureObject.getScaleX(),
                      textureObject.getScaleY(),
                      textureObject.getRotation());
            }
    }
}
