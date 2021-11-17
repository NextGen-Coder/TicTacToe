package co.in.nextgencoder.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById( R.id.progressBar);

        new Thread(new Runnable() {
            int i = progressBar.getProgress();

            public void run() {
                while (i < 100) {
                    i += 1;
                    progressBar.setProgress( i);
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent( getApplicationContext(), StartActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity( intent);
            }
        }).start();
    }
}