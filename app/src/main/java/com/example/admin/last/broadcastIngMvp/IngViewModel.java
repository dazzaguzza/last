package com.example.admin.last.broadcastIngMvp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

public class IngViewModel extends ViewModel {

    LiveData<PagedList<ItemIng>> ingPagedList;
    LiveData<PageKeyedDataSource<Integer,ItemIng>> liveDataSource;
    IngDataSourceFactory ingDataSourceFactory;

    public IngViewModel(){

        ingDataSourceFactory = new IngDataSourceFactory();
        liveDataSource = ingDataSourceFactory.getIngLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .build();

        ingPagedList = (new LivePagedListBuilder(ingDataSourceFactory, config)).build();


    }

    public void refresh(){
        ingDataSourceFactory.getIngLiveDataSource().getValue().invalidate();
    }
}
