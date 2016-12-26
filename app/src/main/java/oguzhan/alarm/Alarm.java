package oguzhan.alarm;

import android.app.PendingIntent;
import android.util.Log;
import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Oguz on 11.09.2016.
 */
public class Alarm {
    Date Date;
    public static int numberofalarms=0;
    int Id;
    Boolean Isrepeat;
    public ArrayList<Boolean>Days;
    String textMessage;
    PendingIntent pendingIntent;
    String NotifyMusic;

    public Alarm() {
        Days= new ArrayList();
        Date=new Date();
        getDate().setSeconds(0);
        for (int i=0;i<7;i++)
             Days.add(false);
        this.Isrepeat=false;
        this.Id= UUID.randomUUID().hashCode();
        numberofalarms++;
    }



    public Alarm(java.util.Date date, Boolean isrepeat, Boolean isvibrate, String notifyMusic) {
        Date = date;
        Isrepeat = isrepeat;
        Days= new ArrayList();
        NotifyMusic = notifyMusic;
        this.Isrepeat=false;
    }

    public ArrayList<Boolean> getDays() {
        return Days;
    }

    public void setDays(ArrayList<Boolean> days) {
        Days = days;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Boolean getIsrepeat() {
        return Isrepeat;
    }

    public void setIsrepeat(Boolean isrepeat) {
        Isrepeat = isrepeat;
    }

    public String getNotifyMusic() {
        return NotifyMusic;
    }

    public void setNotifyMusic(String notifyMusic) {
        NotifyMusic = notifyMusic;
    }
    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }
}
