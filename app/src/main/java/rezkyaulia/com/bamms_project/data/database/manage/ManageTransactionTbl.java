package rezkyaulia.com.bamms_project.data.database.manage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import rezkyaulia.com.bamms_project.data.database.TransactionInterface;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTblDao;
import rezkyaulia.com.bamms_project.data.database.entity.DaoSession;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTblDao;

public class ManageTransactionTbl implements TransactionInterface<TransactionTbl> {

    private TransactionTblDao dao;

    @Inject
    public ManageTransactionTbl(DaoSession daoSession){
        this.dao = daoSession.getTransactionTblDao();
    }

    @Override
    public long add(TransactionTbl object) {
        return  dao.insertOrReplace(object);
    }

    @Override
    public void add(List<TransactionTbl> list) {
        dao.insertOrReplaceInTx(list);
    }

    @Override
    public Flowable<List<TransactionTbl>> getAll() {
        return Flowable.fromCallable(() -> dao.loadAll());
    }

    @Override
    public Observable<TransactionTbl> get(long id) {
        return Observable.fromCallable(() -> dao.load(id));
    }

    @Override
    public void removeAll() {
        dao.deleteAll();
    }

    @Override
    public void remove(TransactionTbl object) {
        dao.delete(object);
    }
}
