package rezkyaulia.com.bamms_project.data.model;

import com.google.gson.annotations.SerializedName;

public class AccountRequest {

    @SerializedName("account_number")
    private String accountNumber;

    public AccountRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
