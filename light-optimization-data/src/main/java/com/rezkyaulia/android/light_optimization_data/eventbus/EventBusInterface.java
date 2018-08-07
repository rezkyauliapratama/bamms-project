package com.rezkyaulia.android.light_optimization_data.eventbus;

import android.support.annotation.NonNull;

import io.reactivex.Observable;


/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public interface EventBusInterface {
    void post(@NonNull Object event);

    <T> Observable<T> observable(@NonNull Class<T> eventClass);
}
