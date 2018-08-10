package rezkyaulia.com.bamms_project.data.model;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Property;

public class TransferRequest {
    @SerializedName("from_account")
    private String fromAccount;

    @SerializedName("to_account")
    private String toAccount;

    @SerializedName("date")
    private String date;

    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private int amount;

    public TransferRequest(String fromAccount, String toAccount, String date, String name, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.date = date;
        this.name = name;
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
