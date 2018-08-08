package rezkyaulia.com.bamms_project.data.database.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "TransactionTbl", indexes = {
        @Index(value = "_id", unique = true)
})
public class TransactionTbl {

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

    @Property(nameInDb = "TransactionAmount")
    @SerializedName("amount")
    private int amount;

@Generated(hash = 2081418470)
public TransactionTbl(Long _id, Long transactionId, Long accountId, Long type,
        int amount) {
    this._id = _id;
    this.transactionId = transactionId;
    this.accountId = accountId;
    this.type = type;
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

public int getAmount() {
    return this.amount;
}

public void setAmount(int amount) {
    this.amount = amount;
}



}
