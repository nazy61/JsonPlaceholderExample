package com.nazycodes.jsonplaceholderexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.nazycodes.jsonplaceholderexample.adapters.ContactsRecyclerAdapter;
import com.nazycodes.jsonplaceholderexample.models.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactsRecyclerAdapter contactsRecyclerAdapter;
    private List<Contact> contactDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        AndroidNetworking.get("https://jsonplaceholder.typicode.com/users")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(Contact.class, new ParsedRequestListener<List<Contact>>() {
                    @Override
                    public void onResponse(List<Contact> contacts) {
                        // do anything with response
                        Log.d("Success", "contactsList size : " + contacts.size());
                        contactDataList = contacts;
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
        contactsRecyclerAdapter = new ContactsRecyclerAdapter(contactDataList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(contactsRecyclerAdapter);
    }
}