package com.example.admin.last.broadcastIngMvp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

public class IngDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer,ItemIng>> IngLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
      IngDataSource ingDataSource = new IngDataSource();
      IngLiveDataSource.postValue(ingDataSource);
      return ingDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, ItemIng>> getIngLiveDataSource() {
        return IngLiveDataSource;
    }
}
