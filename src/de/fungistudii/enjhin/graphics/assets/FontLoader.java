package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author sreis
 */
public class FontLoader {
    private AssetManager manager;
    
    public FontLoader(AssetManager manager) {
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

    public void load(String fontFolder) {
        FileHandle dirHandle = Gdx.files.internal(fontFolder);
        Array<FileHandle> handles = new Array<FileHandle>();
        getHandles(dirHandle, handles);
        for (FileHandle handle : handles) {
            manager.load(handle.toString(), Sound.class);
        }
    }
    
    public BitmapFont getFont(String name){
        return manager.get(name, BitmapFont.class);
    }
}
