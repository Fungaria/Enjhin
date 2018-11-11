/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.enjhin.utils.update;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author Samuel
 */
public class UpdateComponent implements Component{
    
    public Updatable updatable;
    
    public UpdateComponent(){
        
    }
    public UpdateComponent(Updatable updatable){
        this.updatable = updatable;
    }
    
}
