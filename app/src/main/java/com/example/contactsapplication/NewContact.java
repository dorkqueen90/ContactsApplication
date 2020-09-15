package com.example.contactsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static com.example.contactsapplication.MainActivity.adapter;

public class NewContact extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = "demo";
    Button submitButton;
    Button cancelButton;
    EditText name;
    EditText email;
    EditText phone;
    EditText type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        setTitle("New Contact");
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        name = findViewById(R.id.enterName);
        email = findViewById(R.id.enterEmail);
        phone = findViewById(R.id.enterPhone);
        type = findViewById(R.id.enterType);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNameFilled(name) && isEmailValid(email) && isPhoneFilled(phone) &
                isTypeFilled(type)){
                    String contactInfo = "," + name.getText().toString() + "," + email.getText().toString() + "," +
                            phone.getText().toString() + "," + type.getText().toString().toUpperCase();
                    Contact contact = new Contact(contactInfo);

                    createContact(contact);

                    finish();
                } else if (!isNameFilled(name)){
                    Toast.makeText(NewContact.this, "Error, please insert name", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email)){
                    Toast.makeText(NewContact.this, "Error, please insert email", Toast.LENGTH_SHORT).show();
                } else if (!isPhoneFilled(phone)){
                    Toast.makeText(NewContact.this, "Error, please insert phone", Toast.LENGTH_SHORT).show();
                } else if (!isTypeFilled(type)){
                    Toast.makeText(NewContact.this, "Error, please insert CELL, OFFICE, or HOME", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
    private boolean isNameFilled(EditText name) {
        return name.getText().toString().length() > 0;
    }
    private boolean isEmailValid(EditText email) {
        return email.getText().toString().length() > 0 && email.getText().toString().contains("@") &&
                email.getText().toString().contains(".");
    }
    private boolean isPhoneFilled(EditText phone) {
        return phone.getText().toString().length() > 0;
    }
    private boolean isTypeFilled(EditText type) {
        return type.getText().toString().length() > 0 && (type.getText().toString().contains("CELL") ||
                type.getText().toString().contains("OFFICE") || type.getText().toString().contains("HOME"));
    }

    private void createContact(Contact contact){
        RequestBody formBody = new FormBody.Builder()
                .add("name", contact.name)
                .add("email", contact.email)
                .add("phone", contact.phone)
                .add("type", contact.type)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/contact/create")
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