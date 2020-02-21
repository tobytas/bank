package com.gmail.wondergab12.bank.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.wondergab12.bank.MainActivity;
import com.gmail.wondergab12.bank.R;
import com.gmail.wondergab12.bank.model.Atm;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    public static final String TAG = InfoFragment.class.getName();

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            Atm atm = (Atm) getArguments().getSerializable(MainActivity.KEY);
            if (atm != null) {
                recyclerView.setAdapter(new RecyclerAdapter(getAtmFields(atm)));
            }
        }
    }

    private class AtmField {

        private String fieldName;
        private String fieldValue;

        public AtmField(String fieldName, String fieldValue) {
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

        private List<AtmField> dataList;

        public RecyclerAdapter(List<AtmField> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fields, parent, false);
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

        private class RecyclerHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView value;

            public RecyclerHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.field_name);
                value = itemView.findViewById(R.id.field_value);
            }

            void bindData(AtmField field) {
                name.setText(field.getFieldName());
                value.setText(field.getFieldValue());
            }
        }
    }

    private List<AtmField> getAtmFields(Atm atm) {
        List<AtmField> list = new ArrayList<>();

        list.add(new AtmField(getString(R.string.id), atm.getId()));
        list.add(new AtmField(getString(R.string.area), atm.getArea()));
        list.add(new AtmField(getString(R.string.city_type), atm.getCityType()));
        list.add(new AtmField(getString(R.string.city), atm.getCity()));
        list.add(new AtmField(getString(R.string.address_type), atm.getAddressType()));
        list.add(new AtmField(getString(R.string.address), atm.getAddress()));
        list.add(new AtmField(getString(R.string.house), atm.getHouse()));
        list.add(new AtmField(getString(R.string.install_place), atm.getInstallPlace()));
        list.add(new AtmField(getString(R.string.work_time), atm.getWorkTime()));
        list.add(new AtmField(getString(R.string.gps_x), atm.getGpsX()));
        list.add(new AtmField(getString(R.string.gps_y), atm.getGpsY()));
        list.add(new AtmField(getString(R.string.install_place_full), atm.getInstallPlaceFull()));
        list.add(new AtmField(getString(R.string.work_time_full), atm.getWorkTimeFull()));
        list.add(new AtmField(getString(R.string.ATM_type), atm.getAtmType()));
        list.add(new AtmField(getString(R.string.ATM_error), atm.getAtmError()));
        list.add(new AtmField(getString(R.string.currency), atm.getCurrency()));
        list.add(new AtmField(getString(R.string.cash_in), atm.getCashIn()));
        list.add(new AtmField(getString(R.string.ATM_printer), atm.getAtmPrinter()));

        return list;
    }

}
