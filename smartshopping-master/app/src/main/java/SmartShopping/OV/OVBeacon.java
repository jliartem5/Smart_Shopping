/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

/**
 *
 * @author TheNabo1
 */
public class OVBeacon extends OVObject {
    private String uuid;
    private String major;
    private String minor;

    public OVBeacon(){
        this.id = 1;
        this.uuid = "test";
        this.major = "2";
        this.minor = "1";
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }
    
    
    
}
