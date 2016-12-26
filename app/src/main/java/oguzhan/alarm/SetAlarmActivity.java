package oguzhan.alarm;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SetAlarmActivity extends AppCompatActivity {
    int Position;
    Date date;
    private String[] erteleme={"8 saatte  ","12 saatte","1 günde","haftada 1"};
    private ArrayAdapter<String> dataadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        Spinner spinner =(Spinner) findViewById(R.id.spinner);
        dataadapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, erteleme);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataadapter);
        TimePicker picker =(TimePicker) findViewById(R.id.timePicker);
        Button save=(Button) findViewById(R.id.save);
        Position=this.getIntent().getIntExtra("pos",0);
        date = Calendar.getInstance().getTime();
        final EditText messagetext=(EditText)findViewById(R.id.textmessage);
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                date.setHours(hourOfDay);
                date.setMinutes(minute);
                date.setSeconds(0);
                Const.Data.get(Position).setDate(date);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= messagetext.getText().toString();
                Const.Data.get(Position).setTextMessage(text);
                finish();
            }
        });

    }


}
