package rezkyaulia.com.bamms_project.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import rezkyaulia.com.bamms_project.data.database.entity.ParameterTbl;
import rezkyaulia.com.bamms_project.data.database.entity.UserTbl;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */

public class LoginResponse {

    @SerializedName("user_tbl")
    UserTbl userTbl;

    @SerializedName("parameter_tbl")
    List<ParameterTbl> parameterTbl;

    public UserTbl getUserTbl() {
        return userTbl;
    }

    public void setUserTbl(UserTbl userTbl) {
        this.userTbl = userTbl;
    }

    public List<ParameterTbl> getParameterTbl() {
        return parameterTbl;
    }

    public void setParameterTbl(List<ParameterTbl> parameterTbl) {
        this.parameterTbl = parameterTbl;
    }

}
