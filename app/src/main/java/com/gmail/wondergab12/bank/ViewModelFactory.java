package com.gmail.wondergab12.bank;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.wondergab12.bank.repository.RepoImpl;
import com.gmail.wondergab12.bank.repository.database.BankOpenHelper;
import com.gmail.wondergab12.bank.vm.HomeViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(new RepoImpl(BankOpenHelper.getInstance(context).databaseDao()));
    }

}
