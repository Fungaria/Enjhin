/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.assets.AssetManager;

/**
 *
 * @author sreis
 */
public class Assets {
    public final AssetManager manager;
    
    public ImgLoader img;
    public SoundLoader sound;
    public FontLoader font;
    
    public static boolean dist;
    
    public Assets(){
        this.manager = new AssetManager();
        img = new ImgLoader(manager);
        sound = new SoundLoader(manager);
        font = new FontLoader(manager);
    }
}
