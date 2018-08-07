package rezkyaulia.com.bamms_project.data.database.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Entity(nameInDb = "UserTbl", indexes = {
        @Index(value = "userId", unique = true)
})
public class UserTbl {
    @Id
    @Property(nameInDb = "UserId")
    @SerializedName("user_id")
    private Long userId;

    @Property(nameInDb = "Name")
    private String name;

    @Property(nameInDb = "Email")
    private String email;

    @Property(nameInDb = "Token")
    private String token;

    @Property(nameInDb = "UserKey")
    @SerializedName("user_key")
    private String userKey;

    @Property(nameInDb = "SignId")
    @SerializedName("sign_id")
    private String signId;

@Generated(hash = 1898921289)
public UserTbl(Long userId, String name, String email, String token,
        String userKey, String signId) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.token = token;
    this.userKey = userKey;
    this.signId = signId;
}

@Generated(hash = 585658511)
public UserTbl() {
}

public Long getUserId() {
    return this.userId;
}

public void setUserId(Long userId) {
    this.userId = userId;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}

public String getEmail() {
    return this.email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getToken() {
    return this.token;
}

public void setToken(String token) {
    this.token = token;
}

public String getUserKey() {
    return this.userKey;
}

public void setUserKey(String userKey) {
    this.userKey = userKey;
}

public String getSignId() {
    return this.signId;
}

public void setSignId(String signId) {
    this.signId = signId;
}



}
