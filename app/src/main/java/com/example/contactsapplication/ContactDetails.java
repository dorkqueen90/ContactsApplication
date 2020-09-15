package com.example.contactsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.contactsapplication.MainActivity.CONTACT_KEY;
import static com.example.contactsapplication.MainActivity.adapter;

public class ContactDetails extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = "demo";
    Button updateButton;
    Button deleteButton;
    TextView name;
    TextView email;
    TextView phone;
    TextView type;
    String id;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        setTitle("Contact Details");
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        name = findViewById(R.id.nameId);
        email = findViewById(R.id.emailId);
        phone = findViewById(R.id.phoneId);
        type = findViewById(R.id.typeId);

        if(getIntent() != null && getIntent().getExtras() != null) {
            contact = (Contact) getIntent().getExtras().getSerializable(CONTACT_KEY);
            name.setText(contact.name);
            email.setText(contact.email);
            phone.setText(contact.phone);
            type.setText(contact.type);
            id = contact.id;
        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToEdit = new Intent(ContactDetails.this, EditContact.class);
                intentToEdit.putExtra(CONTACT_KEY, contact);
                startActivity(intentToEdit);
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contact);
                finish();
            }
        });
    }
    private void deleteContact(Contact contact){
        RequestBody formBody = new FormBody.Builder()
                .add("id", contact.id)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/contact/delete")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if(response.isSuccessful()){

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}