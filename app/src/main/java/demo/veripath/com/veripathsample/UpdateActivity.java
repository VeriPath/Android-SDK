package demo.veripath.com.veripathsample;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.veripath.sdk.VeriPath.MainFragment;
import com.veripath.sdk.VeriPath.showVeripath;

public class UpdateActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    MainFragment m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            /*
            This is how to launch the VeriPath Permission Screen
            whenever the "update permissions" buttin is pressed.
             */
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update);
            FragmentManager fm = getSupportFragmentManager();
            m = (MainFragment) fm.findFragmentById(R.id.fragment2);

            // Set the flags here on what to show & hide.
            m.showContacts(false);
            m.showTransactions(false);
            m.showDemographics(false);
            m.showDeviceData(true);
            m.showLocation(true);
            m.showAppInteractions(true);

            // Showing the PayForPrivacy dialog
            m.showPayForPrivacy(true);

            // How much are you going to charge if you can't use their data?
            m.setPrivacyPrice("$1.99 / month");

            // NOTE:  If you are displaying payForPrivacy, and you get a "false" value in your callback...
            // That means they confirmed that they want to pay.

            /*
                This line forces the launcher to show, even if they've submitted
                it already.
            */
            m.forceLauncher(true);

            // This initializes the SDK.
            m.veripathSetup("SDK_KEY", "SECRET_KEY", "PUBLIC_KEY");

            // Now let's show it all :)
            m.setOnShowVeripath(new showVeripath() {
                @Override
                public void success(String s) {
                        /*
                        When the user is done, you'll get a permission JSON object here.
                        Do with it what you will.
                         */
                    Log.d("Updated Permissions: ", s);
                    m.forceLauncher(false);
                }
            });
        } catch (Exception e) {
            // Do something.
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
