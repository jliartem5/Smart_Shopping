/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartShopping.OV;

import com.example.sshopping.http.OnDataReturnListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import SmartShopping.Remote.WebServer;

/**
 *
 * @author TheNabo1
 */
public class ReqNotification extends OVReq {
 
    
    private OVNotification ovNotification;
    private int major;
    private int distance;

    public OVNotification getOvNotification() {

        return ovNotification;
    }

    public void setOvNotification(OVNotification ovNotification) {
        this.ovNotification = ovNotification;
    }

    public boolean requestNotifications(OnDataReturnListener dataListener){

        WebServer ws = WebServer.getInstance();
        try {
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();

            //OVNotification notification = new OVNotification(1, this.distance, 0, "-");
            //notification.getOvBeacon().setMajor(this.major);

            //Test beacon fictif
            OVNotification notification = new OVNotification(1, 1, 0, "-");
            notification.getOvBeacon().setMajor(2);

            nvp.add(new BasicNameValuePair("Notification", notification.toJSON().toString()));
            ws.sendRequest(WebServer.COMMANDE.Notification, nvp, dataListener);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateNotificationResponse(OVNotification notification, OnDataReturnListener dataReturnListener){

        WebServer ws = WebServer.getInstance();
        try {
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();


            nvp.add(new BasicNameValuePair("Notification", notification.toJSON().toString()));
            ws.sendRequest(WebServer.COMMANDE.UpdateReponse, nvp, dataReturnListener);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
