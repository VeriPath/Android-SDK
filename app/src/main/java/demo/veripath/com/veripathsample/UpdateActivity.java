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
        /*
        This is how to launch the VeriPath Permission Screen
        whenever the "update permissions" buttin is pressed.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        FragmentManager fm = getSupportFragmentManager();
        m = (MainFragment) fm.findFragmentById(R.id.fragment2);

        /*
         This line forces the launcher to show, even if they've submitted
         it already.
        */
        m.forceLauncher(true);

        // This initializes the SDK.
        m.veripathSetup("SDK_KEY", "SECRET_KEY", "PUBLIC_KEY");

        // This is the friendly name for your app.
        m.setAppName("My Cool App");

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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
