package rezkyaulia.com.bamms_project.data.database;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Rezky Aulia Pratama on 8/6/18.
 */

public interface TransactionInterface<T> {
    long add(T object);
    void add(List<T> list);
    Flowable<List<T>> getAll();
    Observable<T> get(long id);
    void removeAll();
    void remove(T object);
}
