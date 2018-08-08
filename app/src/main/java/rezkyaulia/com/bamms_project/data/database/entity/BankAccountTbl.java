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

@Entity(nameInDb = "BankAccountTbl", indexes = {
        @Index(value = "accountId", unique = true)
})

public class BankAccountTbl {
    @Id
    @Property(nameInDb = "AccountId")
    @SerializedName("id")
    private Long accountId;

    @Property(nameInDb = "UserId")
    @SerializedName("user_id")
    private Long userId;

    @Property(nameInDb = "AccountType")
    @SerializedName("type")
    private Long type;

    @Property(nameInDb = "AccountNumber")
    @SerializedName("account_number")
    private String acountNumber;

    @Property(nameInDb = "AccountBalance")
    @SerializedName("account_balance")
    private int accountBalance;


    @Property(nameInDb = "Description")
    private String description;

    @ToMany(joinProperties = {
            @JoinProperty(name = "accountId", referencedName = "accountId")
    })
    @SerializedName("transactions")
    private List<TransactionTbl> transactionTbls;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 261602099)
private transient BankAccountTblDao myDao;

@Generated(hash = 316185379)
public BankAccountTbl(Long accountId, Long userId, Long type,
        String acountNumber, int accountBalance, String description) {
    this.accountId = accountId;
    this.userId = userId;
    this.type = type;
    this.acountNumber = acountNumber;
    this.accountBalance = accountBalance;
    this.description = description;
}

@Generated(hash = 631984847)
public BankAccountTbl() {
}

public Long getAccountId() {
    return this.accountId;
}

public void setAccountId(Long accountId) {
    this.accountId = accountId;
}

public Long getUserId() {
    return this.userId;
}

public void setUserId(Long userId) {
    this.userId = userId;
}

public Long getType() {
    return this.type;
}

public void setType(Long type) {
    this.type = type;
}

public String getAcountNumber() {
    return this.acountNumber;
}

public void setAcountNumber(String acountNumber) {
    this.acountNumber = acountNumber;
}

public int getAccountBalance() {
    return this.accountBalance;
}

public void setAccountBalance(int accountBalance) {
    this.accountBalance = accountBalance;
}

public String getDescription() {
    return this.description;
}

public void setDescription(String description) {
    this.description = description;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 209698597)
public List<TransactionTbl> getTransactionTbls() {
    if (transactionTbls == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        TransactionTblDao targetDao = daoSession.getTransactionTblDao();
        List<TransactionTbl> transactionTblsNew = targetDao
                ._queryBankAccountTbl_TransactionTbls(accountId);
        synchronized (this) {
            if (transactionTbls == null) {
                transactionTbls = transactionTblsNew;
            }
        }
    }
    return transactionTbls;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1570495759)
public synchronized void resetTransactionTbls() {
    transactionTbls = null;
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
@Generated(hash = 1527797710)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getBankAccountTblDao() : null;
}

}
