package com.example.nithe.weartest2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText et1, et2, et4;
    LinearLayout et3;
    ImageView loc, reg;
    String name, aadhar, econsumer, location = null;
    String ano, eno, an = "", en = "";
    private String command;
    int field = 1;
    DatabaseReference databaseReference;
    Dialog myDialog;
    ArrayList<String> login_details=new ArrayList<>();
    String passcode_pass;
    double latitude, longitude;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView sync;
        final EditText adhaar,consumer;

        sync = findViewById(R.id.sync);
        adhaar =findViewById(R.id.adhar);
        consumer = findViewById(R.id.consumer);

        databaseReference= FirebaseDatabase.getInstance().getReference("USER LOGIN DETAILS");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    String usrs = child.getValue(String.class);

                    login_details.add(usrs);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(adhaar.getText().length()==5)
                {
                    consumer.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        consumer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(consumer.getText().length()==5)
                {
                    consumer.clearFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login=adhaar.getText().toString()+consumer.getText().toString();

                if (login_details.contains(login)) {
                    String sno="1";
                    db = openOrCreateDatabase("REGISTRATION_STATUS", Context.MODE_PRIVATE, null);

                    db.execSQL("INSERT INTO reg VALUES('" + sno + "','" +login + "');");



                    Intent nxt = new Intent(RegisterActivity.this, DashboardActivity.class);
                    nxt.putExtra("KEY", login);
                    startActivity(nxt);
                } else {
                    Toast.makeText(getApplicationContext(),"Wrong Passcode",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent nxt = new Intent(Intent.ACTION_MAIN);
            nxt.addCategory(Intent.CATEGORY_HOME);
            nxt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(nxt);
        }
        return super.onKeyDown(keyCode, event);
    }
}
