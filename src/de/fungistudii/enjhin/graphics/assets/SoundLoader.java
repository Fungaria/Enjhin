package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author sreis
 */
public class SoundLoader {
    private AssetManager manager;
    
    public SoundLoader(AssetManager manager) {
        this.manager = manager;
    }
    
    private void getHandles(FileHandle begin, Array<FileHandle> handles) {
        FileHandle[] newHandles = begin.list();
        for (FileHandle f : newHandles) {
            if (f.isDirectory()) {
                getHandles(f, handles); 
            } else {
                handles.add(f);
            }
        }
    }

    public void load(String soundFolder) {
        FileHandle dirHandle = Assets.dist?Gdx.files.local(soundFolder):Gdx.files.internal(soundFolder);
        Array<FileHandle> handles = new Array<FileHandle>();
        getHandles(dirHandle, handles);
        for (FileHandle handle : handles) {
            manager.load(handle.toString(), Sound.class);
        }
    }
    
    public Sound getSound(String name){
        if(manager.getAssetNames().contains(name+".mp3", false))
            return manager.get(name+".mp3", Sound.class);
        
        else if(manager.getAssetNames().contains(name+".ogg", false))
            return manager.get(name+".ogg", Sound.class);
        
        else if(manager.getAssetNames().contains(name+".wav", false))
            return manager.get(name+".wav", Sound.class);
        
        else 
            return null;
    }
    
    public Music getMusic(String name){
        return manager.get(name, Music.class);
    }
}
