package sg.edu.rp.c346.song;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sg.edu.rp.soi.song.R;

public class SecondActivity extends AppCompatActivity {
    ListView lv;
    Button btnfilter;
    ArrayList<Song> songs;
    ArrayAdapter aa;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        lv = findViewById(R.id.lv);
        btnfilter = findViewById(R.id.btnfilter);
        s = findViewById(R.id.spinner);

        songs = new ArrayList<Song>();
        DBHelper db = new DBHelper(SecondActivity.this);
        songs = db.getAllSongs();
        aa = new CustomAdapter(SecondActivity.this, R.layout.activity_row, songs);
        lv.setAdapter(aa);
        String year[] = new String[songs.size()];
        for(int i = 0; i< songs.size(); i++){
            Song s = songs.get(i);
            year[i] = Integer.toString(s.getYear());
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_spinner_item, year);
        s.setAdapter(a);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(SecondActivity.this);
                int date = Integer.parseInt((String) parent.getItemAtPosition(position));
                songs = db.getDateOfSong(date);
                aa = new CustomAdapter(SecondActivity.this, R.layout.activity_row, songs);
                lv.setAdapter(aa);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songs.clear();
                DBHelper db = new DBHelper(SecondActivity.this);
                songs = db.getRatingOfSong(5);
                aa = new CustomAdapter(SecondActivity.this, R.layout.activity_row, songs);
                lv.setAdapter(aa);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song s = songs.get(position);
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("data", s);
                startActivityForResult(i, 9);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            songs = new ArrayList<Song>();
            songs.clear();
            DBHelper db = new DBHelper(SecondActivity.this);
            songs = db.getAllSongs();
            aa = new CustomAdapter(this, R.layout.activity_row, songs);
            lv.setAdapter(aa);
        }
    }
}



