package com.rezkyaulia.android.light_optimization_data.parser.RequestListener;

import java.io.IOException;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public interface ParsedCallback<T> {
    public void onCompleted(T result);
    public void onFailure(IOException e);


}
