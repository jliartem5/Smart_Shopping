/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SmartShopping.OV;

/**
 *
 * @author Shinthu
 */
public class OVTypePromotion extends OVObject{

    private String libelleTypePromotion; 

    public OVTypePromotion(String libelleTypePromotion) {
        this.libelleTypePromotion = libelleTypePromotion;
    }
    

    public String getLibelleTypePromotion() {
        return libelleTypePromotion;
    }

    public void setLibelleTypePromotion(String libelleTypePromotion) {
        this.libelleTypePromotion = libelleTypePromotion;
    }
}
