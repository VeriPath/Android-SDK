package demo.veripath.com.veripathsample;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.veripath.sdk.VeriPath.MainFragment;
import com.veripath.sdk.VeriPath.showVeripath;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener{

    MainFragment m;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            /*
             This is how to launch the VeriPath Permission Screen
             on the launch of the app.  It will always show, until they
             select some type of permission opt-in.
            */
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            FragmentManager fm = getSupportFragmentManager();

            // We put in an "update permissions" button, right here.
            updateButton = findViewById(R.id.buttonUpdate);
            updateButton.setVisibility(View.GONE);
            m = (MainFragment) fm.findFragmentById(R.id.fragment);

            // Set the flags here on what to show & hide.
            m.showDeviceData(true);
            m.showLocation(true);
            m.showMatchingData(true);
            m.showMeasurement(true);
            m.showStorage(true);
            m.showPersonalization(true);
            m.showAds(true);
            m.showContent(true);

            String[] dataUsers = {"Google", "Facebook", "VenPath"};

            // Set the 3rd party users of this data, if any.
            // If you comment this line out, it will just say "Internal" to the user, denoting you need this data internally.
            m.setDataUsers(dataUsers);

            // Showing the PayForPrivacy dialog
            m.showPayForPrivacy(true);

            // How much are you going to charge if you can't use their data?
            m.setPrivacyPrice("$1.99 / month");

            // NOTE:  If you are displaying payForPrivacy, and you get a "false" value in your callback...
            // That means they confirmed that they want to pay.

            /*
            If you want to force the VeriPath Permission Screen
            to show on the next launch, even if it has been filled out,
            set showTime to a timestamp string for now.

            WARNING: DO NOT SET IT TO System.currentTimeMillis() !!!

            That will force the VeriPath Permission Screen to show on
            every-single-launch of the app.  Not good...
             */
            //Long showTime = Long.parseLong("1526918285");
            //m.resetLaunch(showTime);

            // This initializes the SDK.
            m.veripathSetup("SDK_KEY", "SECRET_KEY", "PUBLIC_KEY");

            // Now let's show it all :)
            m.setOnShowVeripath(new showVeripath() {
                @Override
                public void success(String s) {
                    Log.d("VeriPath","Granted Permission: " + s);
                    updateButton.setVisibility(View.VISIBLE);
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(),UpdateActivity.class));
                        }
                    });
                }
            });

            // This is how to tell if we've gotten their permisisons already.
            if (m.permissionsDone()) {
                // The user picked his preferences already.  Let's let them update if they choose.
                updateButton.setVisibility(View.VISIBLE);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),UpdateActivity.class));
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
