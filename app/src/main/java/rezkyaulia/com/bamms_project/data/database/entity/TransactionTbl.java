package rezkyaulia.com.bamms_project.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

@Entity(nameInDb = "TransactionTbl", indexes = {
        @Index(value = "_id", unique = true)
})
public class TransactionTbl implements Parcelable {

    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    private Long _id;


    @Property(nameInDb = "TransactionId")
    @SerializedName("id")
    private Long transactionId;

    @Property(nameInDb = "AccoundId")
    @SerializedName("account_id")
    private Long accountId;

    @Property(nameInDb = "TransactionType")
    @SerializedName("type")
    private Long type;


    @Property(nameInDb = "TransactionDate")
    @SerializedName("date")
    private String date;

    @Property(nameInDb = "TransactionName")
    @SerializedName("name")
    private String name;

    @Property(nameInDb = "TransactionAmount")
    @SerializedName("amount")
    private int amount;

    @ToOne(joinProperty = "accountId")
    private BankAccountTbl bankAccountTbl;

    @Transient
    @SerializedName("account")
    private BankAccountTbl account;

    @Transient
    @SerializedName("type_code")
    private String type_code;

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public BankAccountTbl getAccount() {
        return account;
    }

    /** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;


/** Used for active entity operations. */
@Generated(hash = 1916896218)
private transient TransactionTblDao myDao;

@Generated(hash = 1918855040)
public TransactionTbl(Long _id, Long transactionId, Long accountId, Long type,
        String date, String name, int amount) {
    this._id = _id;
    this.transactionId = transactionId;
    this.accountId = accountId;
    this.type = type;
    this.date = date;
    this.name = name;
    this.amount = amount;
}

@Generated(hash = 289108111)
public TransactionTbl() {
}

public Long get_id() {
    return this._id;
}

public void set_id(Long _id) {
    this._id = _id;
}

public Long getTransactionId() {
    return this.transactionId;
}

public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
}

public Long getAccountId() {
    return this.accountId;
}

public void setAccountId(Long accountId) {
    this.accountId = accountId;
}

public Long getType() {
    return this.type;
}

public void setType(Long type) {
    this.type = type;
}

public String getDate() {
    return this.date;
}

public void setDate(String date) {
    this.date = date;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}

public int getAmount() {
    return this.amount;
}

public void setAmount(int amount) {
    this.amount = amount;
}

@Generated(hash = 1656072366)
private transient Long bankAccountTbl__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1803676389)
public BankAccountTbl getBankAccountTbl() {
    Long __key = this.accountId;
    if (bankAccountTbl__resolvedKey == null
            || !bankAccountTbl__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BankAccountTblDao targetDao = daoSession.getBankAccountTblDao();
        BankAccountTbl bankAccountTblNew = targetDao.load(__key);
        synchronized (this) {
            bankAccountTbl = bankAccountTblNew;
            bankAccountTbl__resolvedKey = __key;
        }
    }
    return bankAccountTbl;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1445758965)
public void setBankAccountTbl(BankAccountTbl bankAccountTbl) {
    synchronized (this) {
        this.bankAccountTbl = bankAccountTbl;
        accountId = bankAccountTbl == null ? null
                : bankAccountTbl.getAccountId();
        bankAccountTbl__resolvedKey = accountId;
    }
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

@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeValue(this.transactionId);
        dest.writeValue(this.accountId);
        dest.writeValue(this.type);
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeInt(this.amount);
        dest.writeParcelable(this.bankAccountTbl, flags);
        dest.writeParcelable(this.account, flags);
        dest.writeString(this.type_code);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 231064454)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTransactionTblDao() : null;
    }

    protected TransactionTbl(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.transactionId = (Long) in.readValue(Long.class.getClassLoader());
        this.accountId = (Long) in.readValue(Long.class.getClassLoader());
        this.type = (Long) in.readValue(Long.class.getClassLoader());
        this.date = in.readString();
        this.name = in.readString();
        this.amount = in.readInt();
        this.bankAccountTbl = in.readParcelable(BankAccountTbl.class.getClassLoader());
        this.account = in.readParcelable(BankAccountTbl.class.getClassLoader());
        this.type_code = in.readString();
    }

    public static final Creator<TransactionTbl> CREATOR = new Creator<TransactionTbl>() {
        @Override
        public TransactionTbl createFromParcel(Parcel source) {
            return new TransactionTbl(source);
        }

        @Override
        public TransactionTbl[] newArray(int size) {
            return new TransactionTbl[size];
        }
    };
}
