package oguzhan.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Oguz on 11.09.2016.
 */
public class AlarmsListAdapter extends ArrayAdapter<Alarm> {

    Context mContext;
    int layoutResourceId;
    ArrayList<Alarm> data;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public AlarmsListAdapter(Context mContext, int layoutResourceId, ArrayList<Alarm> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mContext, SetAlarmActivity.class);
                mContext.startActivity(intent);
                intent.putExtra("pos", position);
                return false;
            }
        });
        final TextView Hour = (TextView) convertView.findViewById(R.id.Clock);
        Switch OnSwitch = (Switch) convertView.findViewById(R.id.switch2);
        final EditText textData=(EditText)convertView.findViewById(R.id.message);

        final Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);


        OnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                if (checked) {
                    data.get(position).setTextMessage( textData.getText().toString());
                    alarmIntent.putExtra("data",data.get(position).getTextMessage());
                    if (data.get(position).getPendingIntent()==null);
                    data.get(position).setPendingIntent( PendingIntent.getBroadcast(mContext, data.get(position).Id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));



                    if (!data.get(position).getIsrepeat()) {
                        manager.set(AlarmManager.RTC_WAKEUP, data.get(position).getDate().getTime(), data.get(position).getPendingIntent());
                        Toast.makeText(mContext, "Alarm kuruldu " + df.format(data.get(position).getDate()), Toast.LENGTH_SHORT).show();
                    } else {
                        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, data.get(position).getDate().getTime(), AlarmManager.INTERVAL_DAY, data.get(position).getPendingIntent());
                        Toast.makeText(mContext, "Alarm 1 gün tekarlı kuruldu " + df.format(data.get(position).getDate()) + " saniye sonra  tekrarlı", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    manager.cancel(data.get(position).getPendingIntent());
                    Toast.makeText(mContext, "Alarm Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });
/*


*/
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        if (data.get(position).getDate() == null) {
            Hour.setText("00:00");
        } else {
            Hour.setText(dateFormat.format(data.get(position).getDate()));
        }

        Hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        data.get(position).setDate(new Date());
                        data.get(position).getDate().setHours(hour);
                        data.get(position).getDate().setMinutes(minute);
                        data.get(position).getDate().setSeconds(0);
                        Hour.setText(dateFormat.format(data.get(position).getDate()));
                        Log.e("hour", data.get(position).getDate().toString());
                    }
                }, 00, 00, true);
                timePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                timePickerDialog.setTitle("");
                timePickerDialog.show();

            }
        });

        return convertView;

    }


}