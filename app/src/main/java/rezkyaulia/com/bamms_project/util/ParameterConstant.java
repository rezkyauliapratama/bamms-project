package rezkyaulia.com.bamms_project.util;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Rezky Aulia Pratama on 3/7/18.
 */
@Singleton
public class ParameterConstant {

    @Inject
    public ParameterConstant() {
    }

    public final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final String MASTERCARD = "MASTERCARD";
    public final String VISA= "VISA";

    public final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public final String CREDIT = "CREDIT";
    public final String DEBUT = "DEBIT";
}
