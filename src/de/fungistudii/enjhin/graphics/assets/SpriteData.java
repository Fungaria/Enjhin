/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author Samuel
 */
public class SpriteData{
        public float cropedWidth;
        public float cropedHeight;
        public float actualWidth;
        public float actualHeight;
        public float leftOffsetX;
        public float bottomOffsetY;
        public float rightOffsetX;
        public float topOffsetY;

        public SpriteData(float width, float height){
            this.cropedHeight = height;
            this.actualHeight = height;
            this.cropedWidth = width;
            this.actualWidth = width;
        }
        
        public SpriteData(TextureAtlas.AtlasRegion packInfo) {
            
            cropedWidth = packInfo.packedWidth;
            cropedHeight = packInfo.packedHeight;
            
            actualWidth = packInfo.originalWidth;
            actualHeight = packInfo.originalHeight;
            
            leftOffsetX = packInfo.offsetX;
            bottomOffsetY = packInfo.offsetY;
            
            rightOffsetX = actualWidth-cropedWidth-leftOffsetX;
            topOffsetY = actualHeight-cropedHeight-bottomOffsetY;
        }
    }
