package sg.edu.rp.c346.song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import sg.edu.rp.soi.song.R;

public class MainActivity extends AppCompatActivity {
    EditText ettitle, etsinger, etyear;
    Button btnadd, btnshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ettitle = findViewById(R.id.etsong);
        etsinger = findViewById(R.id.etsingers);
        etyear= findViewById(R.id.etyear);
        btnshow = findViewById(R.id.btnshow);
        btnadd = findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.rg1);
                // Get the Id of the selected radio button in the RadioGroup
                int selectedButtonId = rg.getCheckedRadioButtonId();
                // Get the radio button object from the Id we had gotten above
                RadioButton rb = (RadioButton) findViewById(selectedButtonId);

                String title = ettitle.getText().toString();
                String Singer = etsinger.getText().toString();
                int year = Integer.parseInt(etyear.getText().toString());
                int stars = Integer.parseInt(rb.getText().toString());

                DBHelper db = new DBHelper(MainActivity.this);
                // Insert a task
                db.insertSong(title, Singer, year, stars);
                db.close();
                Toast.makeText(MainActivity.this, "Inserted",
                        Toast.LENGTH_LONG).show();
                etyear.setText("");
                ettitle.setText("");
                etsinger.setText("");

            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}
