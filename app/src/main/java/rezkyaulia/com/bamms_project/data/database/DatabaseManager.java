package rezkyaulia.com.bamms_project.data.database;

import javax.inject.Inject;
import javax.inject.Singleton;

import rezkyaulia.com.bamms_project.data.database.manage.ManageBankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.manage.ManageParameterTbl;
import rezkyaulia.com.bamms_project.data.database.manage.ManageTransactionTbl;
import rezkyaulia.com.bamms_project.data.database.manage.ManageUserTbl;


/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Singleton
public class DatabaseManager {
    @Inject
    ManageUserTbl manageUserTbl;

    @Inject
    ManageParameterTbl manageParameterTbl;

    @Inject
    ManageTransactionTbl manageTransactionTbl;

    @Inject
    ManageBankAccountTbl manageBankAccountTbl;


    @Inject
    public DatabaseManager() {

    }

    public ManageUserTbl getUserRepo() {
        return manageUserTbl;
    }

    public ManageParameterTbl getParameterRepo() {
        return manageParameterTbl;
    }

    public ManageTransactionTbl getTransactionRepo() {
        return manageTransactionTbl;
    }

    public ManageBankAccountTbl getAccountRepo() {
        return manageBankAccountTbl;
    }
}
