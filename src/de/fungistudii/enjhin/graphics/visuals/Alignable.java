/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.graphics.visuals;

import com.badlogic.gdx.graphics.Color;
import de.fungistudii.enjhin.utils.Align;

/**
 *
 * @author Samuel
 */
public interface Alignable {
    
    public void setAlignX(Align alignX);
    public void setAlignY(Align alignY);
    public void setAlign(Align alignX, Align alignY);
    public Align getAlignX();
    public Align getAlignY();
}
