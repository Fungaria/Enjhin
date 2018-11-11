package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import de.fungistudii.enjhin.graphics.VisualComponent;
import de.fungistudii.enjhin.utils.GraphicsUtils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sreis
 */
public class ImgLoader {
    private final ArrayList<SpriteDeserializer> deserializers = new ArrayList<SpriteDeserializer>();
    
    boolean loaded;
    
    private AssetManager manager;
    
    public ImgLoader(AssetManager manager) {
        this.manager = manager;
    }
    
    public void load(String xmlDirectory){
        loaded = true;
        loadDeserializer(xmlDirectory);
    }
    
    private void loadDeserializer(String folder) {
        FileHandle dirHandle = Assets.dist?Gdx.files.local(folder):Gdx.files.internal(folder);
        Array<FileHandle> handles = new Array<FileHandle>();
        getHandles(dirHandle, handles);
        for (FileHandle handle : handles) {
            XmlReader.Element element = (loadXmlFile(handle));
            deserializers.add(new SpriteDeserializer(element, manager));
        }
    }
    
    private void getHandles(FileHandle begin, Array<FileHandle> handles) {
        FileHandle[] newHandles = begin.list();
        for (FileHandle f : newHandles) {
            if (f.isDirectory()) {
                getHandles(f, handles);
            } else if(f.extension().equals("xml")){
                handles.add(f);
            }
        }
    }
    
    private XmlReader.Element loadXmlFile(FileHandle handle){
        try {
            Gdx.app.debug("Assets", "Reading file " + handle.path());
            InputStream inputStream = handle.read();
            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            XmlReader reader = new XmlReader();

            XmlReader.Element root = reader.parse(streamReader);
            
            return root;
        }
        catch (Exception e) {
            Gdx.app.error("Assets", "error loading file " + handle.path() + " " + e);
            throw new RuntimeException("Asstest");
        }
    }
    
    public TextureAtlas.AtlasRegion createPackInfo(String name){
        for (SpriteDeserializer deserializer : deserializers) {
            TextureAtlas.AtlasRegion info = deserializer.getAtlas().findRegion(name);
            if(info != null)
                return info;
        }
        return null;
    }
    
    public Sprite createSprite(String name){
        for (SpriteDeserializer deserializer : deserializers) {
            Sprite sprite = deserializer.getAtlas().createSprite(name);
            if(sprite != null)
                return sprite;
        }
        return null;
    }
    
    public VisualComponent.Visual createVisual(String visual){
        for (SpriteDeserializer deserializer : deserializers) {
            if(deserializer.containsVisual(visual))
                return deserializer.loadVisual(visual);
        }
        throw new RuntimeException("visual: "+visual+" does not exist");
    }
}
