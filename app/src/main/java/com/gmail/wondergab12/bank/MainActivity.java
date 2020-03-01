package com.gmail.wondergab12.bank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.wondergab12.bank.fragments.HomeFragment;
import com.gmail.wondergab12.bank.fragments.InfoFragment;
import com.gmail.wondergab12.bank.model.Atm;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener {

    public static final String KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // If first creating(not recreating after display rotation, for example)
        if (savedInstanceState == null) {
            showHomeFragment();
        }
    }

    private void showHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit();
    }

    private void showInfoFragment(Bundle args) {
        InfoFragment fragment = InfoFragment.newInstance();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, InfoFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void atmClickPerform(Atm atm) {
        Bundle args = new Bundle();
        args.putSerializable(KEY, atm);
        showInfoFragment(args);
    }

}
