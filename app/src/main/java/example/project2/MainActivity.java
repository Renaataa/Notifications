package example.project2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int clickCounter = 0;
    NotificationManager mNotifyMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("channel_ID", "name", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("description");
        mNotifyMgr = getSystemService(NotificationManager.class);
        mNotifyMgr.createNotificationChannel(channel);
    }

    private void showNotification(Notification.Builder builder) {
        mNotifyMgr.notify(clickCounter, builder.build());
    }

    private Notification.Builder getBuilder(String title, String text) {
        return new Notification.Builder(this, "channel_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);
    }

    public void showBasicNotification(View view) {
        showNotification(getBuilder("basic notification", totalClicks()));
    }

    public void showCustomNotification(View view) {
        String title = "custom notification";
        String text = totalClicks();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.mtitle, title);
        remoteViews.setTextViewText(R.id.mtext, text);
        remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.crow);
        remoteViews.setImageViewResource(R.id.imagenotiright, R.drawable.crow2);

        Notification.Builder builder = getBuilder(title, text);
        builder.setCustomContentView(remoteViews);
        showNotification(builder);
    }

    public void showBasicToast(View view) {
        Toast.makeText(this, totalClicks(), Toast.LENGTH_SHORT).show();
    }

    public void showCustonToast(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
        TextView text = layout.findViewById(R.id.textView);
        text.setText(totalClicks());

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, -100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private String totalClicks() {
        return "total clicks on project2 so far: " + ++clickCounter;
    }
}
