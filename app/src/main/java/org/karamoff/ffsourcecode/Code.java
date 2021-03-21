package org.karamoff.ffsourcecode;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Code extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "org.karamoff.ffsourcecode.MESSAGE";
    TextView mHTMLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        Intent intent = getIntent();
        final String url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mHTMLView = (TextView) findViewById(R.id.textView);

        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL mURL = new URL (url);

                    HttpURLConnection connection = (HttpURLConnection) mURL.openConnection();

                    InputStreamReader in = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(in);
                    final StringBuilder response = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        response.append(line);
                        line = reader.readLine();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHTMLView.setText(response.toString());
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mThread.start();
    }

    /*public void onWYSIWYGClick (View v) {
        TextView code = (TextView) findViewById(R.id.textView);

        String codeString = code.getText().toString();

        Intent intent = new Intent(this, WYSIWYG.class);
        intent.putExtra(EXTRA_MESSAGE, codeString);
        startActivity(intent);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code, menu);
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
}
