package rezkyaulia.com.bamms_project.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class LoginRequest {
    String username;
    String password;


    public LoginRequest() {

    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
