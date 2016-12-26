package oguzhan.alarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView AlarmsList;
    AlarmsListAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmsList = (ListView) findViewById(R.id.AlarsList);
        AlarmsList.setItemsCanFocus(true);
        Adapter = new AlarmsListAdapter(this, R.layout.alarm_row, Const.Data);
        AlarmsList.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
/*
        AlarmsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tıkladık", "açılmadı");
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                MainActivity.this.startActivity(intent);
                intent.putExtra("pos", position);

                return true;
            }
        });
*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newAlarm:
                Const.Data.add(new Alarm());
                Adapter.notifyDataSetChanged();
                return true;
            case R.id.Settings:
                Intent SettingIntent =new Intent(this,Settings.class);
                startActivity(SettingIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
