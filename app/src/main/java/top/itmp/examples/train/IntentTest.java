package top.itmp.examples.train;

import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.itmp.examples.base.BaseActivity;

/**
 * Created by hz on 16/5/19.
 */
public class IntentTest extends BaseActivity {

    private final static int PICK_CONTACT_REQUEST = 0x11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(listView);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lists = new ArrayList();
        final String[] strings = new String[]{"CallIntent", "MapIntent", "WebIntent", "EmailIntent", "CalendarIntent", "NullIntent",
        "StartActivityForResult"};
        for(int i = 0; i < strings.length; i++){
            Map<String, Object> maps = new HashMap<>();
            maps.put("intent", strings[i]);
            lists.add(maps);
        }



        listView.setAdapter(new SimpleAdapter(this, lists, android.R.layout.simple_list_item_1,
                new String[]{"intent"}, new int[]{android.R.id.text1}));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(new Intent(Intent.ACTION_CALL, Uri.parse("tel://18519198402")));
                        try {
                            startActivity(intent);
                        }catch (SecurityException e){
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 0);
                            }else{
                                // never happen
                            }
                        }
                        break;
                    case 1:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=海淀"));
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itmp.top"));
                        PackageManager manager = getPackageManager();
                        int activitys =  manager.queryIntentActivities(intent2, 0).size();
                        if(activitys > 1){
                            startActivity(Intent.createChooser(intent2, "选择需要使用的浏览器"));
                        }else if (activitys == 1){
                            startActivity(intent2);
                        }else {
                            // error cannot open intent with any activity
                        }


                        //startActivity(intent2);
                        break;
                    case 3:
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
                        emailIntent.setType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
// You can also attach multiple items by passing an ArrayList of Uris
                        startActivity(emailIntent);
                        break;
                    case 4:
                        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(2012, 0, 19, 7, 30);
                        Calendar endTime = Calendar.getInstance();
                        endTime.set(2012, 0, 19, 10, 30);
                        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
                        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
                        try {
                            startActivity(calendarIntent);
                        }catch (SecurityException e){
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                // in fact, we donot need this permission on Andrid M; So this will never happen
                                requestPermissions(new String[]{"android.permission.WRITE_CALENDAR"}, 1);
                            }else {
                                // never happen
                            }
                        }
                        break;
                    case 5:
                        Intent nullIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("null"));

                        PackageManager packageManager = getPackageManager();
                        List<ResolveInfo> activities = packageManager.queryIntentActivities(nullIntent, 0);
                        boolean isIntentSafe = activities.size() > 0;
                        if(!isIntentSafe){
                            Toast.makeText(getApplicationContext(), "No App For Null", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            try {
                                startActivity(nullIntent);
                            } catch (Exception e){
                                // error
                            }
                        }
                        break;
                    case 6:
                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==0 && permissions.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "call permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "call permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 1 && permissions[0].equals("android.permission.WRITE_CALENDAR")){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "calendar permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "calendar permission denied", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK){
            Uri contactUri = data.getData();
            // We only need the NUMBER column, because there will be only one row in the result
            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

            // Perform the query on the contact to get the NUMBER column
            // We don't need a selection or sort order (there's only one result for the given URI)
            // CAUTION: The query() method should be called from a separate thread to avoid blocking
            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
            // Consider using CursorLoader to perform the query.
            Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);
            // never be empty! or not!!!
            try {
                cursor.moveToFirst();
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            // Retrieve the phone number from the NUMBER column
            Log.v("IntentTest", Arrays.asList(cursor.getColumnNames()).toString());

            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String numbers = cursor.getString(column);
            cursor.close();
            new AlertDialog.Builder(IntentTest.this)
                    .setMessage(numbers)
                    .show();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
