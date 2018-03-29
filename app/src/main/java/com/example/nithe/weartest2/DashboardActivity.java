package com.example.nithe.weartest2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends WearableActivity {

    private TextView mTextView;

    private ToggleButton eco;
    DrawerLayout drawer;
    private TextToSpeech tts;
    ArrayList<String> device_status = new ArrayList<>();
    ArrayList<String> name_voice = new ArrayList<>();
    ArrayList<String> name1 = new ArrayList<>();

    ArrayList<Integer> a_thumb = new ArrayList<>();
    ArrayList<String> a_name = new ArrayList<>();
    ArrayList<Boolean> a_switch = new ArrayList<>();
    ArrayList<Boolean> a_eco = new ArrayList<>();

    ListView applianceListView;
    TextView name;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String command, reply;
    DatabaseReference databaseReference;
    public static String passcode_pass;
    public int pass = 0, all = 0;
    public SQLiteDatabase db;
    Cursor c;
    String sno = "1", pro_name;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   mTextView = (TextView) findViewById(R.id.text);

//        passcode_pass = getIntent().getStringExtra("KEY");
//
        try {
            db = openOrCreateDatabase("REGISTRATION_STATUS", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS reg(sno VARCHAR,passcode VARCHAR);");
            c = db.rawQuery("SELECT * FROM reg", null);
            if (c.getCount() == 0) {
                Intent nxt = new Intent(DashboardActivity.this,RegisterActivity.class);
                startActivity(nxt);
                return;
            }
            else {
                String a = "1";
                c = db.rawQuery("SELECT * FROM reg WHERE sno='" + a + "'", null);
                if (c.moveToFirst()) {
                    passcode_pass = c.getString(1);
                    Log.d("pass", "pass: " + passcode_pass);
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Database Failure", Toast.LENGTH_SHORT).show();
        }

//        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i != TextToSpeech.ERROR) {
//                    tts.setLanguage(Locale.UK);
//                }
//            }
//        });

//        databaseReference = FirebaseDatabase.getInstance().getReference(passcode_pass).child("USER DETAILS");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child:dataSnapshot.getChildren()) {
//                    String usrs = child.getValue(String.class);
//                    name1.add(usrs);
//                }
//                if(name1.get(5).equals("false")) {
//                    db = openOrCreateDatabase("REGISTRATION_STATUS", Context.MODE_PRIVATE, null);
//                    c = db.rawQuery("SELECT * FROM reg WHERE sno='" + sno + "'", null);
//                    if (c.moveToFirst()) {
//                        db.execSQL("DELETE FROM reg WHERE sno='" + sno + "'");
//                        showMessage("Logged Out from Home Control", "Synchronisation Failed");
//                        Intent nxt=new Intent(DashboardActivity.this,RegisterActivity.class);
//                        startActivity(nxt);
//                    }
//                } else {
//                    name1.clear();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

//        databaseReference= FirebaseDatabase.getInstance().getReference(passcode_pass).child("DEVICE STATUS");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int count = 0;
//                for (DataSnapshot child:dataSnapshot.getChildren()) {
//                    String usrs = child.getValue(String.class);
//                    device_status.clear();
//                    device_status.add(usrs);
//
//                    String[] check = usrs.split("_");
//                    Boolean swit = Boolean.parseBoolean(check[1]);
//                    if (pass < 2) {
//                        a_name.add(count, check[0]);
//                        name_voice.add(count, check[0].toLowerCase());
//                        a_switch.add(count, swit);
//                        Log.d("Initial", "initial: " + pass);
//                    } else {
//                        a_name.set(count, check[0]);
//                        name_voice.set(count, check[0].toLowerCase());
//                        a_switch.set(count, swit);
//                        Log.d("change", "onDataChange: " + pass);
//                    }
//                    Log.d("Appliances", "Appliance: " + a_name.get(count));
//
//                    count++;
//                }
//                all++;
//                pass++;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference= FirebaseDatabase.getInstance().getReference(passcode_pass).child("ECOMODE STATUS");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int count = 0;
//                for (DataSnapshot child:dataSnapshot.getChildren()) {
//                    String usrs = child.getValue(String.class);
//                    device_status.clear();
//                    device_status.add(usrs);
//
//                    String[] check = usrs.split("_");
//
//                    Boolean swit = Boolean.parseBoolean(check[1]);
//                    if (pass < 2)
//                        a_eco.add(count, swit);
//                    else {
//                        a_eco.set(count, swit);
//                    }
//                    Log.d("init", "init: " + pass);
//                    Log.d("eco", "eco: " + a_eco.get(count));
//                    count++;
//                }
//                all++;
//                pass++;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        // Enables Always-on
//        setAmbientEnabled();
//    }
//
//    public void ecoMode(View view) {
//        if (eco.isChecked()) {
//            databaseReference= FirebaseDatabase.getInstance().getReference(passcode_pass).child("ECOMODE STATUS");
//            databaseReference.child("WATER HEATER").setValue("Water Heater_true");
//            databaseReference.child("IRON BOX").setValue("Iron Box_true");
//            databaseReference.child("OUTSIDE LIGHT").setValue("Outside Light_true");
//            databaseReference.child("BEDROOM LIGHT").setValue("Bedroom Light_true");
//            databaseReference.child("WATER MOTOR").setValue("Water Motor_true");
//            databaseReference.child("BEDROOM FAN").setValue("Bedroom Fan_true");
//            databaseReference.child("WASHING MACHINE").setValue("Washing Machine_true");
//        } else {
//            databaseReference= FirebaseDatabase.getInstance().getReference(passcode_pass).child("ECOMODE STATUS");
//            databaseReference.child("WATER HEATER").setValue("Water Heater_false");
//            databaseReference.child("IRON BOX").setValue("Iron Box_false");
//            databaseReference.child("OUTSIDE LIGHT").setValue("Outside Light_false");
//            databaseReference.child("BEDROOM LIGHT").setValue("Bedroom Light_false");
//            databaseReference.child("WATER MOTOR").setValue("Water Motor_false");
//            databaseReference.child("BEDROOM FAN").setValue("Bedroom Fan_false");
//            databaseReference.child("WASHING MACHINE").setValue("Washing Machine_false");
//        }
//    }
//
//
//    public void talk(View view) {
//        promptSpeechInput();
//    }
//
//    private void promptSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                getString(R.string.speech_prompt));
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getApplicationContext(),
//                    getString(R.string.speech_not_supported),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> result = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    command = result.get(0).toLowerCase();
//                    makeToast(command);
//                }
//                break;
//            }
//
//        }
//    }
//
//    private void makeToast(String cmd) {
//        int got = 0;
//        for (int i = 0; i < name_voice.size(); i++) {
//            if (got == 1)
//                break;
//            String name_voice_ = name_voice.get(i);
//            if (cmd.contains("eco")) {
//                if (cmd.contains(name_voice_)) {
//                    if (cmd.contains("on")) {
//                        if (a_eco.get(i)) {
//                            got = 1;
//                            reply = "eco mode of " + name_voice_ + " already turned on";
//                        }
//                        else {
//                            got = 1;
//                            reply = "eco mode of " + name_voice_ + " turned on";
//                            databaseReference = FirebaseDatabase.getInstance().getReference(passcode_pass).child("ECOMODE STATUS");
//                            databaseReference.child(name_voice_.toUpperCase()).setValue(a_name.get(i) + "_true");
//                        }
//                    } else if (cmd.contains("off")) {
//                        if (!a_eco.get(i)) {
//                            got = 1;
//                            reply = "eco mode of " + name_voice_ + " already turned off";
//                        }
//                        else {
//                            got = 1;
//                            reply = "eco mode of " + name_voice_ + " turned off";
//                            databaseReference = FirebaseDatabase.getInstance().getReference(passcode_pass).child("ECOMODE STATUS");
//                            databaseReference.child(name_voice_.toUpperCase()).setValue(a_name.get(i) + "_false");
//                        }
//                    } else {
//                        reply = "Pardon! Speak Again.";
//                    }
//                } else if (cmd.contains("on")) {
//                    got = 1;
//                    eco.setChecked(true);
//                    ecoMode(eco);
//                    reply = "eco mode turned on";
//                } else if (cmd.contains("off")) {
//                    got = 1;
//                    eco.setChecked(false);
//                    ecoMode(eco);
//                    reply = "eco mode turned off";
//                } else {
//                    reply = "Pardon! Speak Again.";
//                }
//            } else if (cmd.contains(name_voice_)) {
//                if (cmd.contains("on")) {
//                    if (a_switch.get(i)) {
//                        got = 1;
//                        reply = name_voice_ + " already turned on";
//                    }
//                    else {
//                        got = 1;
//                        reply = name_voice_ + " turned on";
//                        databaseReference = FirebaseDatabase.getInstance().getReference(passcode_pass).child("DEVICE STATUS");
//                        databaseReference.child(name_voice_.toUpperCase()).setValue(a_name.get(i) + "_true");
//                    }
//                } else if (cmd.contains("off")) {
//                    if (!a_switch.get(i)) {
//                        got = 1;
//                        reply = name_voice_ + " already turned off";
//                    }
//                    else {
//                        got = 1;
//                        reply = name_voice_ + " turned off";
//                        databaseReference = FirebaseDatabase.getInstance().getReference(passcode_pass).child("DEVICE STATUS");
//                        databaseReference.child(name_voice_.toUpperCase()).setValue(a_name.get(i) + "_false");
//                    }
//                } else {
//                    Log.d("pardon", "pardon: in");
//                    reply = "Pardon! Speak Again.";
//                }
//            } else {
//                Log.d("pardon", "pardon: out");
//                reply = "Pardon! Speak Again.";
//            }
//        }
//        Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_SHORT).show();
//        tts.speak(reply, TextToSpeech.QUEUE_FLUSH, null);
//    }
//
//    public void showMessage(String title, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//


}

    public void logout(View view) {
        db = openOrCreateDatabase("REGISTRATION_STATUS", Context.MODE_PRIVATE, null);

        c = db.rawQuery("SELECT * FROM reg WHERE sno='" + sno + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("DELETE FROM reg WHERE sno='" + sno + "'");
            showMessage("Success", "Successfully Logged Out");


            Intent nxt=new Intent(DashboardActivity.this,RegisterActivity.class);
            startActivity(nxt);
        }

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
