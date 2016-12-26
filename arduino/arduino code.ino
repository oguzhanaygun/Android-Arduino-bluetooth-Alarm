#include <SoftwareSerial.h>
#include <LiquidCrystal.h>
LiquidCrystal lcd(7,6,5,4,3,2);
String data1="";
String data2;
int saat=20;//su an ki oldugunda ki saat ve dakika girilir
int dakika=26;
unsigned long  before=0;
unsigned long now=0;
unsigned long messageTime=0;
void setup() {
    Serial.begin(9600);
     lcd.begin(16, 2);
  lcd.setCursor(0,0);         // 1.satır
   lcd.print("saat= "+String(saat)+":"+String(dakika));
now=millis();
}

void loop()
{

 while(!Serial.available()){
  now=millis();
  if((now-before)>=60000){
    before=now;
    lcd.clear();
    if((millis()-messageTime)<30000)
     {lcd.setCursor(0,1);
      lcd.print(data1);
      Serial.print((millis()-messageTime));
      }
      else{
        data1="";}
    dakika++;
    lcd.setCursor(0,0);        
  lcd.print("saat= "+String(saat)+":"+String(dakika));
    if(dakika ==60){
      dakika=0;
      lcd.setCursor(0,0);         
      lcd.print("saat= "+String(saat)+":"+String(dakika));
      saat++;
      if(saat==24)
        saat==0;
        lcd.setCursor(0,0);         
        lcd.print("saat= "+String(saat)+":"+String(dakika));
      }
    }
   lcd.noDisplay();  // LCD Ekranı Sil
  delay(500);
  lcd.display();    // LCD Ekranı Göster
  delay(500);
  };
   
 data1 =Serial.readString();
  Serial.print("okay"+data1);
  lcd.setCursor(0,1);
  lcd.print(data1);
  messageTime=millis();
  Serial.flush();
  }


