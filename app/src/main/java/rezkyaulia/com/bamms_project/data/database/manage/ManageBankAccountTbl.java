package rezkyaulia.com.bamms_project.data.database.manage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import rezkyaulia.com.bamms_project.data.database.TransactionInterface;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTblDao;
import rezkyaulia.com.bamms_project.data.database.entity.DaoSession;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTbl;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTblDao;

public class ManageBankAccountTbl  implements TransactionInterface<BankAccountTbl> {

    private BankAccountTblDao dao;
    private ParameterTblDao paramDao;

    @Inject
    public ManageBankAccountTbl(DaoSession daoSession){
        this.dao = daoSession.getBankAccountTblDao();
        this.paramDao = daoSession.getParameterTblDao();
    }

    @Override
    public long add(BankAccountTbl object) {
        return  dao.insertOrReplace(object);
    }

    @Override
    public void add(List<BankAccountTbl> list) {
        dao.insertOrReplaceInTx(list);
    }

    @Override
    public Flowable<List<BankAccountTbl>> getAll() {
        return Flowable.fromCallable(() -> {
            List<BankAccountTbl> bankAccountTbls = dao.loadAll();
            if (bankAccountTbls != null)
                for(BankAccountTbl bankAccountTbl : bankAccountTbls){
                    ParameterTbl parameterTbl = paramDao.queryBuilder().where(ParameterTblDao.Properties.ParameterId.eq(bankAccountTbl.getType())).unique();
                    if (parameterTbl != null){
                        bankAccountTbl.setType_code(parameterTbl.getCode());
                    }
                }
            return bankAccountTbls;
        });
    }

    @Override
    public Observable<BankAccountTbl> get(long id) {
        return Observable.fromCallable(() -> dao.load(id));
    }

    @Override
    public void removeAll() {
        dao.deleteAll();
    }

    @Override
    public void remove(BankAccountTbl object) {
        dao.delete(object);
    }
}
