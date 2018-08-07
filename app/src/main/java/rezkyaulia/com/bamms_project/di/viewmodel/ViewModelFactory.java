package rezkyaulia.com.bamms_project.di.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by Rezky Aulia Pratama on 5/6/18.
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels;

    @Inject
    ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels){

        this.viewModels = viewModels;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try{
            //noinspection unchecked
            return (T)viewModels.get(modelClass).get();
        }catch (Exception e){
            throw new RuntimeException("Error creating view model for class : " + modelClass.getSimpleName(),e);
        }
    }
}
