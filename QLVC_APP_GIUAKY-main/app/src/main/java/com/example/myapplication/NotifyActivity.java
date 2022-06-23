package com.example.myapplication;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotifyActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;

    private EditText editTextTitle;
    private EditText editTextMessage;

    private Button button1;
    private Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        this.editTextTitle = (EditText) this.findViewById(R.id.editText_title);
        this.editTextMessage = (EditText) this.findViewById(R.id.editText_message);

        this.button1 = (Button) this.findViewById(R.id.button1);
        this.button2 = (Button) this.findViewById(R.id.button2);

        this.button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendOnChannel1(  );
            }
        });

        this.button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendOnChannel2(  );
            }
        });

        //
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
    }


    private void sendOnChannel1()  {
        String title = this.editTextTitle.getText().toString();
        String message = this.editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }

    private void sendOnChannel2()  {
        String title = this.editTextTitle.getText().toString();
        String message = this.editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_PROMO) // Promotion.
                .build();

        int notificationId = 2;
        this.notificationManagerCompat.notify(notificationId, notification);
    }
}
