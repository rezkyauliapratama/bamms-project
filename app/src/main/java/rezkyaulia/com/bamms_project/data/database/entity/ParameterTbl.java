package rezkyaulia.com.bamms_project.data.database.entity;

/**
 * Created by Rezky Aulia Pratama on 11/6/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "ParameterTbl", indexes = {
        @Index(value = "parameterId", unique = true)
})
public class ParameterTbl implements Parcelable{
    @Id
    @Property(nameInDb = "ParameterId")
    @SerializedName("parameter_id")
    private Long parameterId;

    @Property(nameInDb = "CategoryId")
    @SerializedName("category_id")
    private long categoryId;

    @Property(nameInDb = "ParentId")
    @SerializedName("parent_id")
    private long parentId;

    @Property(nameInDb = "Code")
    private String code;

    @Property(nameInDb = "Name")
    private String name;

    @Property(nameInDb = "description")
    private String description;

@Generated(hash = 1979927415)
public ParameterTbl(Long parameterId, long categoryId, long parentId,
        String code, String name, String description) {
    this.parameterId = parameterId;
    this.categoryId = categoryId;
    this.parentId = parentId;
    this.code = code;
    this.name = name;
    this.description = description;
}

public ParameterTbl (String name){
    this.name = name;
}
@Generated(hash = 149003883)
public ParameterTbl() {
}

public Long getParameterId() {
    return this.parameterId;
}

public void setParameterId(Long parameterId) {
    this.parameterId = parameterId;
}

public long getCategoryId() {
    return this.categoryId;
}

public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
}

public long getParentId() {
    return this.parentId;
}

public void setParentId(long parentId) {
    this.parentId = parentId;
}

public String getCode() {
    return this.code;
}

public void setCode(String code) {
    this.code = code;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}

public String getDescription() {
    return this.description;
}

public void setDescription(String description) {
    this.description = description;
}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.parameterId);
        dest.writeLong(this.categoryId);
        dest.writeLong(this.parentId);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected ParameterTbl(Parcel in) {
        this.parameterId = (Long) in.readValue(Long.class.getClassLoader());
        this.categoryId = in.readLong();
        this.parentId = in.readLong();
        this.code = in.readString();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Creator<ParameterTbl> CREATOR = new Creator<ParameterTbl>() {
        @Override
        public ParameterTbl createFromParcel(Parcel source) {
            return new ParameterTbl(source);
        }

        @Override
        public ParameterTbl[] newArray(int size) {
            return new ParameterTbl[size];
        }
    };

    @Override
    public String toString() {
        return this.name;
    }
}
