package com.gape.ide.working;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.gape.cyandr.gapeandroid.gape.GpeApplication;
import com.gape.cyandr.gapeandroid.gape.R;


/**
 * Created by cyandr on 2016/9/11 0011.
 */
public class LanguageActivity extends Activity implements View.OnClickListener {
    Button btn_new, btn_open, btn_run;
    Spinner spinner_choose_platform;
    GpeApplication myapp;
    Button text_show_platform;
    String the_choice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);


        myapp = (GpeApplication) getApplication();
        myapp.setMYAPPTAG("GPE");
        initView();
    }

    @Override
    public void onClick(View view) {
        if (myapp.getCurrent_table().length() != 0) {
            if (view.getId() == R.id.button) {
                Intent intent = new Intent(LanguageActivity.this, OpenglesActivity.class);
                startActivity(intent); }
            else if (view.getId() == R.id.button2) {
                }
            else if(view.getId()==R.id.show_platform_choice)
            {
                Intent openEdit = new Intent(LanguageActivity.this, DBManager.class);
                openEdit.putExtra("PLATFORM", the_choice);
                startActivity(openEdit);

            }
        } else
            Toast.makeText(LanguageActivity.this, "还没有选择指令集", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        final String[] platforms = getResources().getStringArray(R.array.platforms);
        btn_new = findViewById(R.id.button);
        btn_new.setOnClickListener(this);
        btn_open = findViewById(R.id.button2);
        btn_open.setOnClickListener(this);
        btn_run = findViewById(R.id.button3);
        btn_run.setOnClickListener(this);
        text_show_platform = findViewById(R.id.show_platform_choice);
        text_show_platform.setEnabled(false);
        spinner_choose_platform = findViewById(R.id.spinner_choose_platform);

        spinner_choose_platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                the_choice = platforms[position];
                text_show_platform.setText(the_choice);
                if (position != 0) {
                    text_show_platform.setEnabled(true);
                    myapp.setCurrent_table(the_choice);
                } else {
                    text_show_platform.setEnabled(false);
                    myapp.setCurrent_table("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        text_show_platform.setOnClickListener(this);

    }
}