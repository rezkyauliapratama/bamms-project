package rezkyaulia.com.bamms_project.data.database.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Entity(nameInDb = "UserTbl", indexes = {
        @Index(value = "userId", unique = true)
})
public class UserTbl {
    @Id
    @Property(nameInDb = "UserId")
    @SerializedName("id")
    private Long userId;

    @Property(nameInDb = "UserType")
    @SerializedName("role_id")
    private int userType;


    @Property(nameInDb = "UserKey")
    @SerializedName("user_key")
    private String userKey;


    @Property(nameInDb = "Name")
    private String name;

    @Property(nameInDb = "Email")
    private String email;

    @Property(nameInDb = "Phone")
    private String phone;

    @Property(nameInDb = "Address")
    private String address;

    @ToMany(joinProperties = {
            @JoinProperty(name = "userId", referencedName = "userId")
    })
    @SerializedName("bank_accounts")
    private List<BankAccountTbl> bankAccountTbls;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 2081283128)
private transient UserTblDao myDao;

@Generated(hash = 2138082167)
public UserTbl(Long userId, int userType, String userKey, String name,
        String email, String phone, String address) {
    this.userId = userId;
    this.userType = userType;
    this.userKey = userKey;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.address = address;
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

public int getUserType() {
    return this.userType;
}

public void setUserType(int userType) {
    this.userType = userType;
}

public String getUserKey() {
    return this.userKey;
}

public void setUserKey(String userKey) {
    this.userKey = userKey;
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

public String getPhone() {
    return this.phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getAddress() {
    return this.address;
}

public void setAddress(String address) {
    this.address = address;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 652737584)
public List<BankAccountTbl> getBankAccountTbls() {
    if (bankAccountTbls == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BankAccountTblDao targetDao = daoSession.getBankAccountTblDao();
        List<BankAccountTbl> bankAccountTblsNew = targetDao
                ._queryUserTbl_BankAccountTbls(userId);
        synchronized (this) {
            if (bankAccountTbls == null) {
                bankAccountTbls = bankAccountTblsNew;
            }
        }
    }
    return bankAccountTbls;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1875301405)
public synchronized void resetBankAccountTbls() {
    bankAccountTbls = null;
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1492006224)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getUserTblDao() : null;
}

    

}
