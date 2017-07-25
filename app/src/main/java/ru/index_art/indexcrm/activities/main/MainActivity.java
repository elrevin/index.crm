package ru.index_art.indexcrm.activities.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import ru.index_art.indexcrm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("ent",((EditText)findViewById(R.id.edt)).getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("index.art", "resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("index.art", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("index.art", "destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("index.art", "start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("index.art", "create");
        if (savedInstanceState != null) {
            Log.d("index.art", "ReCreate");
        }
    }
}
