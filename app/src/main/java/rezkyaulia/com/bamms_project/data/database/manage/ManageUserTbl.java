package rezkyaulia.com.bamms_project.data.database.manage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import rezkyaulia.com.bamms_project.data.database.TransactionInterface;
import rezkyaulia.com.bamms_project.data.database.entity.DaoSession;
import rezkyaulia.com.bamms_project.data.database.entity.UserTbl;
import rezkyaulia.com.bamms_project.data.database.entity.UserTblDao;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */
@Singleton
public class ManageUserTbl implements TransactionInterface<UserTbl> {

    private UserTblDao dao;

    @Inject
    public ManageUserTbl(DaoSession daoSession){
        this.dao = daoSession.getUserTblDao();
    }

    @Override
    public long add(UserTbl object) {
        return  dao.insertOrReplace(object);
    }

    @Override
    public void add(List<UserTbl> list) {
        dao.insertOrReplaceInTx(list);
    }

    @Override
    public Flowable<List<UserTbl>> getAll() {
        return Flowable.fromCallable(() -> dao.loadAll());
    }

    @Override
    public Observable<UserTbl> get(long id) {
        return Observable.fromCallable(() -> dao.load(id));
    }

    @Override
    public void removeAll() {
        dao.deleteAll();
    }

    @Override
    public void remove(UserTbl object) {
        dao.delete(object);
    }

    public Observable<UserTbl> get() {
        return Observable.fromCallable(() -> {
            List<UserTbl> userTbls = dao.loadAll();
            if (userTbls.size() > 0){
                return userTbls.get(0);
            }

            return null;
        });
    }

    public long getId(){
        List<UserTbl> userTbls = dao.loadAll();
        if (userTbls.size() > 0){
            return userTbls.get(0).getUserId();
        }

        return 0;
    }

    public String getUserKey(){
        List<UserTbl> userTbls = dao.loadAll();
        if (userTbls.size() > 0){
            return userTbls.get(0).getUserKey();
        }

        return null;
    }


}
