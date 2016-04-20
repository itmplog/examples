package top.itmp.examples.temps;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import top.itmp.examples.R;
import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 2016/4/20.
 */
public class ProgressNotification extends BaseActivity {
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_notification);

        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(ProgressNotification.this);
                mBuilder.setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);

                new Downloader().execute();

            }
        });

    }

    private class Downloader extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }
    }
}
