package unal.edu.co.bicicrash.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 6/11/2017.
 */

public class PositionLL {

    private String uid;
    private String ubication;

    public PositionLL() {
//        this.uid = uid;
//        this.ubication = ubication;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }


//    public Map<String, Object> toMap() {
//        HashMap<String,Object> result= new HashMap<>();
//
//        result.put("ubication",ubication);
//        result.put("uid",uid);
//
//        return result;
//    }
}
