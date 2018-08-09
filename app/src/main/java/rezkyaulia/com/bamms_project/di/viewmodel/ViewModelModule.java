package rezkyaulia.com.bamms_project.di.viewmodel;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import rezkyaulia.com.bamms_project.ui.MainViewModel;
import rezkyaulia.com.bamms_project.ui.detail.DetailViewModel;

/**
 * Created by Rezky Aulia Pratama on 5/6/18.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);


}