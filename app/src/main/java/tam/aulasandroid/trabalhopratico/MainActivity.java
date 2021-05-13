package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Calendar cal;
    TextClock hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cal = Calendar.getInstance();
        hora = findViewById(R.id.textClockTime);
        Log.d("MAin", cal.toString());


        //hora.setText( new SimpleDateFormat( "HH:mm" ).format( Calendar.getInstance().getTime()));



    }

    public void updateTime(){
        hora.refreshTime();
    }

    public void planoAlimentarView(View v){
        startActivity(new Intent(this, PlanoAlimentar.class));
    }

    class ThreadHora extends Thread{
        int counter;

        ThreadHora(int _counter){ counter = _counter;}

        public int getCounter(){
            return counter;
        }

        public void run(){

            Log.d("TAG", "Thread started");

            final EditText eRight = (EditText) findViewById(R.id.text2);
            while (counter>0){
                try {
                    Thread.sleep(1000);
                    counter--;
                    // a thread can not access views from the UI
                    eRight.post(new Runnable(){ // pode usar qualquer view para fazer o post

                        @Override
                        public void run() {
                            eRight.setText(String.valueOf(counter));

                        }
                    });

                } catch (InterruptedException e) {
                    //Log.e(TAG, e.toString());
                }
            }
            Log.d("TAG", "Thread ended");
        }
    }
}