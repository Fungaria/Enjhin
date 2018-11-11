/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import de.fungistudii.enjhin.graphics.VisualComponent.Visual;
import de.fungistudii.enjhin.graphics.visuals.AnimationVisual;
import de.fungistudii.enjhin.utils.Align;
import de.fungistudii.enjhin.graphics.visuals.B2dAnimation;
import de.fungistudii.enjhin.graphics.visuals.B2dParticleEffect;
import de.fungistudii.enjhin.graphics.visuals.B2dSprite;
import de.fungistudii.enjhin.graphics.visuals.ParticleEffectVisual;
import de.fungistudii.enjhin.graphics.visuals.SpriteVisual;
import de.fungistudii.enjhin.utils.GraphicsUtils;
import java.util.HashMap;

/**
 * utility class used by Assets 
 * @author Samuel
 */
class SpriteDeserializer {
    
    private static final String particleDir = "particleDir";
    private static final String packDir = "packDir";
    
    private String particleDirectory;
    private String packDirecory;
    private HashMap<String, XmlReader.Element> elements = new HashMap<String, XmlReader.Element>();

    private AssetManager manager;
    
    public SpriteDeserializer(XmlReader.Element root, AssetManager manager) {
        this.manager = manager;
        this.packDirecory = root.getAttribute(packDir);
        this.particleDirectory = root.getAttribute(particleDir);
        
        if(!manager.getAssetNames().contains(packDir, false)){
            TextureAtlasLoader.TextureAtlasParameter param = new TextureAtlasLoader.TextureAtlasParameter();
            param.loadedCallback = new AssetLoaderParameters.LoadedCallback() {
                @Override
                public void finishedLoading(AssetManager am, String string, Class type) {
                    if(type.equals(TextureAtlas.class)){
                        GraphicsUtils.setAtlasFilter(am.get(string, TextureAtlas.class), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                    }
                }
            };
            manager.load(packDirecory, TextureAtlas.class, param);
        }
        
        
        for (int i = 0; i < root.getChildCount(); i++) {
            XmlReader.Element child = root.getChild(i);
            elements.put(child.getName(), child);
        }
    }
    
    public Visual loadVisual(String name){
        return loadVisual(elements.get(name));
    }
    public boolean containsVisual(String name){
        return elements.containsKey(name);
    }
    private Visual loadVisual(XmlReader.Element element){
        if(element == null)
            throw new RuntimeException("no visual with given name in Hashmap");
        String type = element.get("type");
        
        if(type == null)
            throw new RuntimeException("XML element has no type ATTR");
        
        if(type.trim().toLowerCase().equals("sprite")){
            return loadSprite(element);
        }else if(type.trim().toLowerCase().equals("b2dsprite")){
            return loadB2dSprite(element);
        }else if(type.trim().toLowerCase().equals("animation")){
            return loadAnimation(element);
        }else if(type.trim().toLowerCase().equals("b2danimation")){
            return loadB2dAnimation(element);
        }else if(type.trim().toLowerCase().equals("particle")){
            return loadParticle(element);
        }else if(type.trim().toLowerCase().equals("b2dparticle")){
            return loadB2dParticle(element);
        }
        throw new RuntimeException("type: "+type+" is stupid");
    }
    
    //SIMPLE
    private SpriteVisual loadSprite(XmlReader.Element spriteElement){
        //imgLocation
        String imgLocation = spriteElement.get("src");
        if(imgLocation == null){
            throw new NullPointerException("src of sprite "+spriteElement.getName()+" MUST be set");
        }
        
        SpriteData data = new SpriteData(getAtlas().findRegion(imgLocation));
        
        //zIndex
        float zIndex = spriteElement.getFloat("zIndex", 0);
        
        //alignX
        Align alignX = deserializeAlign(spriteElement.get("alignX", "left"));
        
        //alignY
        Align alignY = deserializeAlign(spriteElement.get("alignY", "bottom"));
        
        //scale
        float scale = spriteElement.getFloat("scale", 1);
        
        Color tint = deserializeColor(spriteElement.get("color", "255, 255, 255, 255"));
        
        SpriteVisual sprite = new SpriteVisual();
        sprite.set(getAtlas().createSprite(imgLocation));
        sprite.setPosition(0, 0);
        sprite.setData(data);
        sprite.setAlign(alignX, alignY);
        sprite.setZIndex(zIndex);
        sprite.setScale(scale);
        sprite.setColor(tint);
        
        
        return sprite;
    }
    private AnimationVisual loadAnimation(XmlReader.Element animationElement){
        //imgLocation
        String imgLocation = animationElement.get("src");
        if(imgLocation == null){
            throw new NullPointerException("src of sprite "+animationElement.getName()+" MUST be set");
        }
        
        //zIndex
        float zIndex = animationElement.getFloat("zIndex", 0);
        
        //alignX
        Align alignX = deserializeAlign(animationElement.get("alignX", "left"));
        
        //alignY
        Align alignY = deserializeAlign(animationElement.get("alignY", "bottom"));
        
        //offsetX
        float offsetX = animationElement.getFloat("offsetX", 0);
        
        //offsetY
        float offsetY = animationElement.getFloat("offsetY", 0);
        
        //scale
        float scale = animationElement.getFloat("scale", 1);
        
        //tint
        Color tint = deserializeColor(animationElement.get("color", "255, 255, 255, 255"));
        
        //frameDuration
        float frameDuration = 1/animationElement.getFloat("fps", 1);
        
        //playMode
        Animation.PlayMode playMode = deserializePlayMode(animationElement.get("playMode", "normal"));
        
        AnimationVisual sprite = new AnimationVisual(frameDuration, getAtlas().createSprites(imgLocation), createSpriteDatas(imgLocation), playMode);
        
        sprite.setAlign(alignX, alignY);
        sprite.setZIndex(zIndex);
        sprite.setScale(scale);
        sprite.setColor(tint);
        
        return sprite;
    }
    private ParticleEffectVisual loadParticle(XmlReader.Element particleElemet) {
        String particleName = particleElemet.getName();
        
        //imgLocation
        String src = particleElemet.get("src");
        if(src == null || particleName==null || particleDirectory==null){
            throw new NullPointerException("src of sprite "+particleElemet+" MUST be set");
        }
        
        //zIndex
        float zIndex = particleElemet.getFloat("zIndex", 0);
        
        //scale
        float scale = particleElemet.getFloat("scale", 1);
        
        //offsetX
        float offsetX = particleElemet.getFloat("position", 0);
        
        //offsetY
        float offsetY = particleElemet.getFloat("position", 0);
        
        ParticleEffectVisual particleEffect = new ParticleEffectVisual();
        particleEffect.load(Gdx.files.internal(particleDirectory+"\\"+src), getAtlas(), "particles/");
        particleEffect.setPosition(offsetX, offsetY);
        particleEffect.setScale(scale);
        particleEffect.setZIndex(zIndex);
        
        return particleEffect;
    }
    
    //BOX2D
    private B2dParticleEffect loadB2dParticle(XmlReader.Element particleElemet) {
        String particleName = particleElemet.getName();
        
        //imgLocation
        String src = particleElemet.get("src");
        if(src == null || particleName==null || particleDirectory==null){
            throw new NullPointerException("src of sprite "+particleElemet+" MUST be set");
        }
        
        //zIndex
        float zIndex = particleElemet.getFloat("zIndex", 0);
        
        //scale
        float scale = particleElemet.getFloat("scale", 1);
        
        //alignX
        Align alignX = deserializeAlign(particleElemet.get("alignX", "left"));
        
        //alignY
        Align alignY = deserializeAlign(particleElemet.get("alignY", "bottom"));
        
        //offsetX
        float offsetX = particleElemet.getFloat("position", 0);
        
        //offsetY
        float offsetY = particleElemet.getFloat("position", 0);
        
        B2dParticleEffect particleEffect = new B2dParticleEffect();
        particleEffect.load(Gdx.files.internal(particleDirectory+"\\"+src), getAtlas(), "particles/");
        particleEffect.setAlign(alignX, alignY);
        particleEffect.setPosition(offsetX, offsetY);
        particleEffect.setZIndex(zIndex);
        
        return particleEffect;
    }
    private B2dSprite loadB2dSprite(XmlReader.Element spriteElement){
        //imgLocation
        String imgLocation = spriteElement.get("src");
        if(imgLocation == null){
            throw new NullPointerException("src of sprite "+spriteElement.getName()+" MUST be set");
        }
        
        //Data
        SpriteData data = new SpriteData(getAtlas().findRegion(imgLocation));
        
        //zIndex
        float zIndex = spriteElement.getFloat("zIndex", 0);
        
        //alignX
        Align alignX = deserializeAlign(spriteElement.get("alignX", "left"));
        
        //alignY
        Align alignY = deserializeAlign(spriteElement.get("alignY", "bottom"));
        
        //offsetX
        float offsetX = spriteElement.getFloat("offsetX", 0);
        
        //offsetY
        float offsetY = spriteElement.getFloat("offsetY", 0);
        
        //scale
        float scale = spriteElement.getFloat("scale", 1);
        
        //tint
        Color tint = deserializeColor(spriteElement.get("color", "255, 255, 255, 255"));
        
        B2dSprite sprite = new B2dSprite(getAtlas().createSprite(imgLocation));
        sprite.setData(data);
        sprite.setPosition(offsetX, offsetY);
        sprite.setAlign(alignX, alignY);
        sprite.setZIndex(zIndex);
        sprite.setScale(scale);
        sprite.setColor(tint);
        
        return sprite;
    }
    private B2dAnimation loadB2dAnimation(XmlReader.Element animationElement){
        
        //imgLocation
        String imgLocation = animationElement.get("src");
        if(imgLocation == null){
            throw new NullPointerException("src of sprite "+animationElement.getName()+" MUST be set");
        }
        
        //zIndex
        float zIndex = animationElement.getFloat("zIndex", 0);
        
        //alignX
        Align alignX = deserializeAlign(animationElement.get("alignX", "left"));
        
        //alignY
        Align alignY = deserializeAlign(animationElement.get("alignY", "bottom"));
        
        //offsetX
        float offsetX = animationElement.getFloat("offsetX", 0);
        
        //offsetY
        float offsetY = animationElement.getFloat("offsetY", 0);
        
        //scale
        float scale = animationElement.getFloat("scale", 1);
        
        //tint
        Color tint = deserializeColor(animationElement.get("color", "255, 255, 255, 255"));
        
        //frameDuration
        float frameDuration = 1/animationElement.getFloat("fps", 1);
        
        //playMode
        Animation.PlayMode playMode = deserializePlayMode(animationElement.get("playMode", "normal"));
        
        B2dAnimation sprite = new B2dAnimation(frameDuration, getAtlas().createSprites(imgLocation), createSpriteDatas(imgLocation), playMode);
        
        sprite.setAlign(alignX, alignY);
        sprite.setZIndex(zIndex);
        sprite.setScale(scale);
        sprite.setColor(tint);
        
        return sprite;
    }
    
    public TextureAtlas getAtlas(){
        return manager.get(packDirecory.replace("\\\\", "/"), TextureAtlas.class);
    }
    
    //UTILITY
    private Align deserializeAlign(String serializedAlign){
        if(serializedAlign.equals("left"))
            return Align.left;
        else if(serializedAlign.equals("right"))
            return Align.right;
        else if(serializedAlign.equals("center"))
            return Align.center;
        else if(serializedAlign.equals("top"))
            return Align.top;
        else if(serializedAlign.equals("bottom"))
            return Align.bottom;
        else
            throw new IllegalArgumentException(serializedAlign+" isn't an Align");
    }
    private Animation.PlayMode deserializePlayMode(String serializedAlign) {
        String trimmedSerializedAlign = serializedAlign.trim().toLowerCase();
        if(trimmedSerializedAlign.equals("normal"))
            return Animation.PlayMode.NORMAL;
        else if(trimmedSerializedAlign.equals("loop"))
            return Animation.PlayMode.LOOP;
        else if(trimmedSerializedAlign.equals("loop_pingpong"))
            return Animation.PlayMode.LOOP_PINGPONG;
        else if(trimmedSerializedAlign.equals("loop_random"))
            return Animation.PlayMode.LOOP_RANDOM;
        else if(trimmedSerializedAlign.equals("loop_reversed"))
            return Animation.PlayMode.LOOP_REVERSED;
        else if(trimmedSerializedAlign.equals("reversed"))
            return Animation.PlayMode.REVERSED;
        else
            throw new IllegalArgumentException(serializedAlign+" isn't an PlayMode");
    }
    private Color deserializeColor(String colorString){
        String[] colors = colorString.split(",");
        float[] values = new float[4];
        for (int i = 0; i < colors.length; i++) {
            values[i] = Float.parseFloat(colors[i].trim());
        }
        Color color = new Color(values[0]/255f, values[1]/255f, values[2]/255f, values[3]/255f);
        return color;
    }
    private Array<SpriteData> createSpriteDatas(String name){
        Array<SpriteData> datas = new Array<SpriteData>();
        for (TextureAtlas.AtlasRegion region : getAtlas().findRegions(name)) {
            datas.add(new SpriteData(region));
        }
        return datas;
    }
}
