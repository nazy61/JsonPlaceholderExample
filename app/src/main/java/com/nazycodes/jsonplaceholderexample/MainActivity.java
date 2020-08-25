package com.nazycodes.jsonplaceholderexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.nazycodes.jsonplaceholderexample.adapters.ContactsRecyclerAdapter;
import com.nazycodes.jsonplaceholderexample.models.Contact;
import com.nazycodes.jsonplaceholderexample.utils.NetworkStatusChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ContactsRecyclerAdapter contactsRecyclerAdapter;
    private List<Contact> contactDataList;
    private NetworkStatusChecker networkStatusChecker;

    private final String api = "https://jsonplaceholder.typicode.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_circular);

        contactDataList = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        networkStatusChecker = new NetworkStatusChecker();

        if(networkStatusChecker.isNetworkAvailable(this)){
            Toast.makeText(this, "There is Internet Connection", Toast.LENGTH_SHORT).show();
            getContactData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    protected void getContactData() {
        progressBar.setVisibility(View.VISIBLE);

        AndroidNetworking.get(api)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(Contact.class, new ParsedRequestListener<List<Contact>>() {
                    @Override
                    public void onResponse(List<Contact> contacts) {
                        // do anything with response
                        Log.d("Success", "contactsList size : " + contacts.size());
                        contactDataList = contacts;
                        progressBar.setVisibility(View.INVISIBLE);
                        loadUI();
                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.e("Error", "onError: " + anError.getMessage());
                    }
                });
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // do anything with response
//                        Log.d("testing", "onResponse: " + response.toString());
//
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject obj = response.getJSONObject(i);
//                                String name = obj.getString("name");
//                                String email = obj.getString("email");
//                                String phone = obj.getString("phone");
//                                String website = obj.getString("website");
//                                Contact contact = new Contact(name, email, phone, website);
//                                contactDataList.add(contact);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        loadUI();
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        Log.e("Error", "onError: " + error.getMessage());
//                    }
//                });

    }

    public void loadUI(){
        contactsRecyclerAdapter = new ContactsRecyclerAdapter(contactDataList,this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(contactsRecyclerAdapter);
    }
}