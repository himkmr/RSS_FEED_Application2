package mydomain1.main_package;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivitylayoutname);

        //Should use network code in AsycTask, this is just a quick fix
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        IotdHandler handler  = new IotdHandler();
        handler.processFeed();
        reset_Display(IotdHandler.getTitle(), IotdHandler.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_res_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void reset_Display(String title, String description)
    {
        TextView titleview = (TextView)findViewById(R.id.textView);
        titleview.setText(title);

        TextView desc = (TextView)findViewById(R.id.textView2);
        titleview.setText(description);

        ImageView imgview = (ImageView)findViewById(R.id.imageView);
        imgview.setImageBitmap(IotdHandler.getBitmap());


    }
}
