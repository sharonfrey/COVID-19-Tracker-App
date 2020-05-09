package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.afinal.R;

import java.util.ArrayList;

import static com.example.afinal.DatabaseHelper.TAG;

public class SearchFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnSearch;
    private EditText region;
    private ListView list;
    public static final String EXTRA_MESSAGE = "com.example.afinal.MESSAGE";

    SendMessage SM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //search for region
        region = view.findViewById(R.id.region);
        btnAdd = view.findViewById(R.id.save);
        btnSearch = view.findViewById(R.id.search);
        mDatabaseHelper = new DatabaseHelper(getContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = region.getText().toString();

                if(region.length() != 0) {
                    SM.sendData(newEntry);
                } else {
                    toastMessage("Error: no region entered");
                }
            }
        });
        //add a region
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = region.getText().toString();

                if(region.length() != 0) {
                    AddData(newEntry);
                } else {
                    toastMessage("Error: no region entered");
                }
            }
        });

        //view all saved regions
        list = view.findViewById(R.id.favorites);
        populateListView();
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Region Saved");
            populateListView();
        } else {
            toastMessage("Error saving region, please try again");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void populateListView() {
        //get data and append to list
        Cursor regions = mDatabaseHelper.getData();
        ArrayList<String> listCity = new ArrayList<>();
        while(regions.moveToNext()) {
            listCity.add(regions.getString(1));
        }
        if(!listCity.isEmpty()) {
            ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listCity);
            list.setAdapter(adapter);
        }


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String name = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);


                Cursor data = mDatabaseHelper.getItemID(name); //the id associated with given city
                int regionID = -1;
                while(data.moveToNext()) {
                    regionID = data.getInt(0);
                }
                if(regionID > -1) {
                    Intent editScreenIntent = new Intent(getContext(), EditDataActivity.class);
                    editScreenIntent.putExtra("id",regionID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that city");
                }
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                SM.sendData(name);
            }
        });

    }
    interface SendMessage {
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }


}
