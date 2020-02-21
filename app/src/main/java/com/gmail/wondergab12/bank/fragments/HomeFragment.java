package com.gmail.wondergab12.bank.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.wondergab12.bank.R;
import com.gmail.wondergab12.bank.ViewModelFactory;
import com.gmail.wondergab12.bank.model.Atm;
import com.gmail.wondergab12.bank.vm.HomeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = HomeFragment.class.getName();

    private static final int REQUEST_CODE_FINE_LOCATION = 10018;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private GoogleMap map;
    private HomeFragmentListener listener;
    private RecyclerView recyclerView;

    public interface HomeFragmentListener {
        void atmClickPerform(Atm atm);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        checkLocationPermission();

        map.setOnMarkerClickListener(marker -> {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            RecyclerAdapter recyclerAdapter = (RecyclerAdapter) recyclerView.getAdapter();
            if (layoutManager != null && recyclerAdapter != null) {
                //noinspection SuspiciousMethodCalls
                layoutManager.scrollToPosition(recyclerAdapter.getList().indexOf(marker.getTag()));
            }

            return false;
        });

        LatLng reykjavik = new LatLng(64.128288, -21.827774);
        map.addMarker(new MarkerOptions().position(reykjavik).title("Reykjavík, Iceland"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(reykjavik));
    }

    private void checkLocationPermission() {
        if (getContext() != null) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
                updateAtms();
            } else {
                askLocationPermission();
            }
        }
    }

    private void enableUserLocation() {
        map.setMyLocationEnabled(true);
        LatLng userLatLng = getActualLatLng();
        if (userLatLng != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 10.82F));
        }
    }

    private String getCurrentCity() {
        LatLng userLatLng = getActualLatLng();
        Locale locale = new Locale("ru");

        Geocoder geocoder = new Geocoder(getContext(), locale);
        try {
            if (userLatLng != null) {
                List<Address> addressList = geocoder.getFromLocation(userLatLng.latitude, userLatLng.longitude, 1);
                return addressList.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("MissingPermission")
    private LatLng getActualLatLng() {
        if (getContext() != null) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                List<String> providersList = locationManager.getProviders(true);
                Location mostAccurate = null;
                for (String provider : providersList) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        if (mostAccurate == null || mostAccurate.getAccuracy() < location.getAccuracy()) {
                            mostAccurate = location;
                        }
                    }
                }
                if (mostAccurate != null) {
                    return new LatLng(mostAccurate.getLatitude(), mostAccurate.getLongitude());
                }
            }
        }

        return null;
    }

    private void askLocationPermission() {
        String[] requiredPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        requestPermissions(requiredPermissions, REQUEST_CODE_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            updateAtms();
        }
    }

    private void updateAtms() {
        HomeViewModel viewModel = new ViewModelProvider(requireActivity(), new ViewModelFactory(getContext())).get(HomeViewModel.class);
        viewModel.getErrs().observe(getViewLifecycleOwner(), Throwable::printStackTrace);
        viewModel.getAtms().observe(getViewLifecycleOwner(), atmsList -> {
            recyclerView.setAdapter(new RecyclerAdapter(atmsList));
            for (Atm atm : atmsList) {
                LatLng latLng = new LatLng(Double.valueOf(atm.getGpsX()), Double.valueOf(atm.getGpsY()));
                Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(atm.getId()));
                marker.setTag(atm);
            }
        });

        viewModel.getAtmList(getCurrentCity(), "USD");
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

        private List<Atm> dataList;

        RecyclerAdapter(List<Atm> dataList) {
            this.dataList = dataList;
        }

        List<Atm> getList() {
            return dataList;
        }

        @NonNull
        @Override
        public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atm, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
            holder.bindData(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList != null ? dataList.size() : 0;
        }

        private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView atmId;
            private Atm atm;

            RecyclerHolder(@NonNull View itemView) {
                super(itemView);
                atmId = itemView.findViewById(R.id.atm_id);
                itemView.setOnClickListener(this);
            }

            void bindData(Atm atm) {
                this.atm = atm;
                atmId.setText(atm.getId());
            }

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.atmClickPerform(atm);
                }
            }
        }
    }

}
