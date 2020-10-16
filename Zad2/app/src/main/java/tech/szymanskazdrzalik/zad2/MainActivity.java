package tech.szymanskazdrzalik.zad2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void secondActivityButtonOnClick(View v) {
        Intent startIntent = new Intent(this, SecondActivity.class);
        startActivity(startIntent);
    }

    public void googleButtonOnClick(View v) {
        String google = "http://www.google.com";
        Uri webadress = Uri.parse(google);
        Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webadress);
        if (gotoGoogle.resolveActivity(getPackageManager()) != null) {
            startActivity(gotoGoogle);
        }
    }


    public void mapButtonOnClick(View v) {
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void phoneButtonOnClick(View v) {
        Uri number = Uri.parse("tel:5551234");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
    }

    public void computeDeltaOnClick(View v) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("tech.szymanskazdrzalik.quadratic_equation");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }

}