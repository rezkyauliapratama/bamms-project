package rezkyaulia.com.bamms_project.data.database.manage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import rezkyaulia.com.bamms_project.data.database.TransactionInterface;
import rezkyaulia.com.bamms_project.data.database.entity.DaoSession;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTbl;
import rezkyaulia.com.bamms_project.data.database.entity.ParameterTblDao;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Singleton
public class ManageParameterTbl implements TransactionInterface<ParameterTbl> {

    private ParameterTblDao dao;

    @Inject
    public ManageParameterTbl(DaoSession daoSession){
        this.dao = daoSession.getParameterTblDao();
    }

    @Override
    public long add(ParameterTbl object) {
        return  dao.insertOrReplace(object);
    }

    @Override
    public void add(List<ParameterTbl> list) {
        dao.insertOrReplaceInTx(list);
    }

    @Override
    public Flowable<List<ParameterTbl>> getAll() {
        return Flowable.fromCallable(() -> dao.loadAll());
    }

    @Override
    public Observable<ParameterTbl> get(long id) {
        return Observable.fromCallable(() -> dao.load(id));
    }

    @Override
    public void removeAll() {
        dao.deleteAll();
    }

    @Override
    public void remove(ParameterTbl object) {
        dao.delete(object);
    }

    public Flowable<List<ParameterTbl>> getCategoryByCode(String code){
        return Flowable.fromCallable(() -> {
            ParameterTbl param = dao.queryBuilder().where(ParameterTblDao.Properties.Code.eq(code)).unique();

            if (param != null) {
                return dao.queryBuilder().where(ParameterTblDao.Properties.CategoryId.eq(param.getParameterId())).list();
            }else{
                return null;
            }
        });

    }

    public Flowable<ParameterTbl> getByCode(String code){
        return Flowable.fromCallable(() -> {
            ParameterTbl param = dao.queryBuilder().where(ParameterTblDao.Properties.Code.eq(code)).unique();

            if (param != null) {
                return param;
            }else{
                return null;
            }
        });

    }
}
