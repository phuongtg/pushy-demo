package me.pushy.example;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.exceptions.PushyException;

public class Main extends AppCompatActivity
{
    TextView mRegistrationID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load activity_main.xml layout
        setContentView(R.layout.main);

        // Cache TextView object
        mRegistrationID = (TextView)findViewById(R.id.registrationID);

        // Restart the socket service, in case the user force-closed
        Pushy.listen(this);

        // Register up for push notifications (will return existing token if already registered before)
        new RegisterForPushNotifications().execute();
    }

    private class RegisterForPushNotifications extends AsyncTask<String, Void, String>
    {
        ProgressDialog mLoading;

        public RegisterForPushNotifications()
        {
            // Create progress dialog and set it up
            mLoading = new ProgressDialog(Main.this);
            mLoading.setMessage(getString(R.string.loading));
            mLoading.setCancelable(false);

            // Show it
            mLoading.show();
        }

        @Override
        protected String doInBackground(String... params)
        {
            // Temporary string that will hold the registration result
            String result;

            try
            {
                // Get registration ID via Pushy
                result = Pushy.register(Main.this);
            }
            catch (PushyException exc)
            {
                // Show error instead
                result = exc.getMessage();
            }

            // Write to log
            Log.d("Pushy", "Registration result: " + result);

            // Return result
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // Activity died?
            if ( isFinishing() )
            {
                return;
            }

            // Hide progress bar
            mLoading.dismiss();

            // Display it
            mRegistrationID.setText(result);
        }
    }
}
