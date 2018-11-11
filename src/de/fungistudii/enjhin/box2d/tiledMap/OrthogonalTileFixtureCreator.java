/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fungistudii.enjhin.box2d.tiledMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

/**
 * creates Fixtures around every tile on a TileLayer
 * der algorithmus is btw einfach GEGE
 * @author samuel
 */
public class OrthogonalTileFixtureCreator {

    private final World world;
    private final float unitScale;
    private final TiledMap map;
    private ArrayList<Vector2> starters = new ArrayList<Vector2>();
    
    public OrthogonalTileFixtureCreator(World world, float unitScale, TiledMap map){
        this.world = world;
        this.unitScale = unitScale;
        this.map = map;
    }
    
    public Fixture[] createFixtures(String layerName, FixtureDef fixtureDef, BodyDef bodyDef){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(layerName);
        if(layer == null){throw new RuntimeException("layerName "+layerName+" was not found in map");}
        
        createFixtures(layerName, fixtureDef, world.createBody(bodyDef));
        
        return null;
    }
    public Fixture[] createFixtures(String layerName, FixtureDef fixtureDef, Body body){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(layerName);
        if(layer == null){throw new RuntimeException("layerName "+layerName+" was not found in map");}
        
        ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
        
        for(int row=0; row<layer.getHeight(); row++){
            for(int col=0; col<layer.getWidth(); col++){
                if((layer.getCell(col, row) == null) && (layer.getCell(col, row-1) != null)){
                    starters.add(new Vector2(col, row));
                }
            }
        }
        
        while(starters.size() != 0) {
            fixtures.add(createShape(world, body, fixtureDef, layer, unitScale, (int)starters.get(0).x, (int)starters.get(0).y));
        }
        
        
        return fixtures.toArray(new Fixture[fixtures.size()]);
    }
    
    private Fixture createShape(World world, Body body, FixtureDef fixDef, TiledMapTileLayer layer, float unitScale, int x, int y){
        
        
        Direction dire = Direction.top;
        Vector2 tiled = new Vector2(x+1, y);
        ArrayList<Vector2> tiles = new ArrayList<Vector2>();
        tiles.add(new Vector2(x, y)); tiles.add(new Vector2(tiled));
        
        
        while(!(tiled.equals(new Vector2(x, y)))) {
            int numTiles = getNumTiles(layer, tiled);
            
            switch(dire){
                    case top:
                        if(numTiles == 1) {dire = Direction.left; tiled.add(0, -1);}
                        else if(numTiles == 3) {dire = Direction.right; tiled.add(0, 1);}
                        else tiled.add(1, 0);
                        break;
                    case down:
                        if(numTiles == 1) {dire = Direction.right; tiled.add(0, 1);}
                        else if(numTiles == 3) {dire = Direction.left; tiled.add(0, -1);}
                        else tiled.add(-1, 0);
                        break;
                    case left:
                        if(numTiles == 1) {dire = Direction.down; tiled.add(-1, 0);}
                        else if(numTiles == 3) {dire = Direction.top; tiled.add(1, 0);}
                        else tiled.add(0, -1);
                        break;
                    case right:
                        if(numTiles == 1) {dire = Direction.top; tiled.add(1, 0);}
                        else if(numTiles == 3) {dire = Direction.down; tiled.add(-1, 0);}
                        else tiled.add(0, 1);
                        break;
                }
            
            tiles.add(new Vector2(tiled));
        }
        
        starters.removeAll(tiles);
        
        Vector2[] coors = new Vector2[tiles.size()];
        for (int i = 0; i < tiles.size(); i++) {
            coors[i] = new Vector2(layer.getTileWidth()*unitScale*tiles.get(i).x, layer.getTileHeight()*unitScale*tiles.get(i).y);
        }
        
        ChainShape shape = new ChainShape();
        shape.createChain(coors);
        fixDef.shape = shape;
        Fixture fix = body.createFixture(fixDef);
        shape.dispose();
        return fix;
    }
    
    private int getNumTiles(TiledMapTileLayer layer, Vector2 tiled){
        int i = 0;
        
        boolean[][] tiles = new boolean[2][2];
        
        tiles[1][1] = (layer.getCell((int)tiled.x, (int)tiled.y) != null);
        tiles[0][1] = (layer.getCell((int)tiled.x-1, (int)tiled.y) != null);
        tiles[1][0] = (layer.getCell((int)tiled.x, (int)tiled.y-1) != null);
        tiles[0][0] = ((layer.getCell((int)tiled.x-1, (int)tiled.y-1) != null ));
        
        for (boolean[] tile : tiles) {
            for (boolean u : tile) {
                if(u) i++;
            }
        }
        
        //check for diagonal
        if(i==2 && ((tiles[0][1]&&tiles[1][0]) || (tiles[1][1]&&tiles[0][0]))){
            i=1;
        }
        
        return i;
    }
    
    
    private enum Direction{
        top,
        down,
        left,
        right
    }
}
