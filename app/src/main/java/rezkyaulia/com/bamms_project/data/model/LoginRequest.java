package rezkyaulia.com.bamms_project.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class LoginRequest {
    String name;
    String email;
    String token;
    @SerializedName("sign_id")
    String signId;

    public LoginRequest() {

    }

    public LoginRequest(String name, String email, String token, String signId) {
        this.name = name;
        this.email = email;
        this.token = token;
        this.signId = signId;
    }

    public LoginRequest(String name, String email, String signId) {
        this.name = name;
        this.email = email;
        this.signId = signId;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
}
