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
            OVNotification notification = new OVNotification(1, 1, false, "-");

            nvp.add(new BasicNameValuePair("NotificationTexte",notification.toJSON().toString()));
            ws.sendRequest(WebServer.COMMANDE.NotificationTexte, nvp, dataListener);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

}
