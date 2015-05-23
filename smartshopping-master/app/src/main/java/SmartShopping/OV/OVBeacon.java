/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheNabo1
 */
public class OVBeacon extends OVObject {

    private Integer idBeacon;
    private String uuid;
    private int major;
    private int minor;

    public OVBeacon(String jsonStr) {

        try {
            JSONObject object;
            object = new JSONObject(jsonStr);

            this.uuid = object.getString("uuid");
            this.major = object.getInt("major");
            this.minor = object.getInt("minor");
            this.idBeacon = object.getInt("id");
        } catch (JSONException ex) {
        }
    }
    
    public OVBeacon() {
        this.id = 1;
        this.uuid = "test";
        this.major = 2;
        this.minor = 1;
    }

    public OVBeacon(Integer idBeacon, String uuid, int major, int minor) {
        this.idBeacon = idBeacon;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }
    
    @Override
    public Integer getId() {
        return idBeacon;
    }

    public void setId(int id) {
        this.idBeacon = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

}
