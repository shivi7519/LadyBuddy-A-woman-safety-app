package com.moheeeetgupta.ladybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    CardView siren, location, Settings, currentlocation, community, news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Intent backgroundService = new Intent( getApplicationContext(), ScreenOnOffBackgroundService.class );
        this.startService( backgroundService );
        Log.d( ScreenOnOffReceiver.SCREEN_TOGGLE_TAG, "Activity onCreate" );
        int permissionCheck = ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission (MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.custom_dialog,null);

            Button btn_okay = (Button)mView.findViewById(R.id.btn_okay);
            TextView textView=mView.findViewById (R.id.textFormodal);
            textView.setText ("\nSending SMS :-\n" +
                    "\n" +
                    "Ladybudy have a feature that whenever a woman is in danger, then she needs to tap the power button just three times and an emergency message will be sent to her 4 dear ones so we need the SEND SMS permission. \n" +
                    "\n" +
                    "Accessing Location :-\n" +
                    "\n" +
                    "The message sent will be integrated with the live location of the woman, so we need the location access. \n" +
                    "\n" +
                    "Phone Call:-\n" +
                    "\n" +
                    "The app will simultaneously also make a phone call to the first phone number. So, we need CALL PHONE permission. \n" +
                    "\n" +
                    "Declaration:- The app is totally developed in INDIA  and every data related to this app is stored locally, not a single bit of data is transferred to developing team.\n");
            CheckBox checkbox = (CheckBox)mView.findViewById(R.id.checkBox);
            TextView checkBoxtext = (TextView)mView.findViewById(R.id.checkBoxText);
            checkbox.setVisibility (View.VISIBLE);
            checkBoxtext.setVisibility (View.VISIBLE);
            checkbox.setEnabled (true);
            checkBoxtext.setEnabled (true);

            checkbox.setText("");
            checkBoxtext.setText(Html.fromHtml("I agree to the " +
                    "<a href='https://www.websitepolicies.com/policies/view/sLfvQSXP'>TERMS AND CONDITIONS</a>"));
            textView.setClickable(true);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            alert.setView(mView);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkbox.isChecked ()) {

                        alertDialog.dismiss ();
                        ActivityCompat.requestPermissions (MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 0);
                    }else{
                        Toast.makeText (MainActivity.this,"Please accept privacy policy",Toast.LENGTH_LONG).show ();

                    }
                }
            });
            alertDialog.show();

        }

        siren = findViewById( R.id.Siren );
        location = findViewById( R.id.send_Location );
        Settings = findViewById( R.id.Settings );
        currentlocation = findViewById( R.id.Currentlocation );
        news = findViewById( R.id.News );
        // community=findViewById (R.id.community);
        siren.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Flashing.class ) );
            }
        } );
        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Instructions.class ) );
            }
        } );
        Settings.setOnClickListener( v -> startActivity( new Intent( getApplicationContext(), SmsActivity.class ) ) );
        currentlocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), ChoosenActivity.class ) );
            }
        } );
        news.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), NewsActivity.class ) );
            }
        } );

    }



}