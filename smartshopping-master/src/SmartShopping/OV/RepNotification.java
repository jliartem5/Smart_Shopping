/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import java.util.ArrayList;

/**
 *
 * @author TheNabo1
 */
public class RepNotification extends OVRep {
    
     private ArrayList<OVNotification> listeNotification; 

    public RepNotification(){
        this.listeNotification = new ArrayList<OVNotification>();
    }
    
    public ArrayList<OVNotification> getListeNotification() {
        return listeNotification;
    }

    public void setListeNotification(ArrayList<OVNotification> listeNotification) {
        this.listeNotification = listeNotification;
    }
    
}
