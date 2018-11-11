/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.update;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

/**
 *
 * @author sreis
 */
public abstract class FamilySystem extends EntitySystem{
    
    private Family family;
    
    public FamilySystem(Family family){
        this.family = family;   
    }
}
