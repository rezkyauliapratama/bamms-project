package rezkyaulia.com.bamms_project.data;

import java.util.ArrayList;
import java.util.List;

import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;

public class DummyData {

    List<BankAccountTbl> bankAccountTbls = new ArrayList<>();
    List<TransactionTbl> transactionTbls = new ArrayList<>();

    public DummyData() {
        bankAccountTbls.add(new BankAccountTbl(1L,1L,1001L,"9012129101",2000000,""));
        bankAccountTbls.add(new BankAccountTbl(2L,1L,1001L,"9012129102",3000000,""));
        bankAccountTbls.add(new BankAccountTbl(3L,1L,1001L,"9012129104",4000000,""));

        transactionTbls.add(new TransactionTbl(1L,1L,1L,1003L,"08-01-2018t02:00","tokopedia",2000));
    }

    public List<BankAccountTbl> getAccounts(){
       return bankAccountTbls;
    }

    public List<TransactionTbl> getTransactions() {
        return transactionTbls;
    }
}
