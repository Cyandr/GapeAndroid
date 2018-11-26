package com.gape.ide.working;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.gape.cyandr.gapeandroid.gape.R;

import java.util.List;

/**
 * Created by yjk on 2016/11/23.
 */
public class DBManager extends AppCompatActivity {
    private String TABLE, INS_type;
    private DBHelper MyDbhelper;
    private TextView table_label;
    private EditText Ed_ins_name, Ed_ins_value, Ed_ins_info, Ed_ins_gparser;
    private Spinner All_types;
    private AtomInstruct selected_ins;
    private boolean isUpdateIns = false;
    private String[] instruction_types;
    private ListView All_ins;
    private List<AtomInstruct> ALLINS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbmanager);
        Intent intent = getIntent();///////////////////////////////////////////.;./
        TABLE = intent.getStringExtra("PLATFORM");
        MyDbhelper = new DBHelper(this);
        instruction_types = getResources().getStringArray(R.array.instruction_type);
        table_label = findViewById(R.id.table_label);

        initDataBase();
        initGui();
        initListView();

    }

    @Override
    public void onBackPressed() {
        MyDbhelper.close();
        super.onBackPressed();
    }

    private void initListView() {
        All_ins = findViewById(R.id.id_list_of_instructions);
        ALLINS = MyDbhelper.readVariables();
        InAdapterManageDb adapter = new InAdapterManageDb(DBManager.this, ALLINS);
        All_ins.setAdapter(adapter);
        All_ins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_ins = ALLINS.get(position);
                Toast.makeText(DBManager.this, "您选择了" + position, Toast.LENGTH_SHORT).show();
                for (int i = 0; i <= instruction_types.length - 1; i++) {
                    if (selected_ins.getIns_type().equals(instruction_types[i])) {
                        All_types.setSelection(i);
                        break;
                    }
                }
                Ed_ins_name.setText(selected_ins.getIns_name());
                Ed_ins_value.setText(selected_ins.getIns_Value());
                Ed_ins_info.setText(selected_ins.getIns_Info());
                Ed_ins_gparser.setText(selected_ins.getIns_gparser());
                isUpdateIns = true;
            }
        });

    }

    private void initDataBase() {


        if (MyDbhelper.open(MyDbhelper.GetDbPath()) && !istable_exist(TABLE)) {
            MyDbhelper.createNewTable(TABLE);
        }
        MyDbhelper.setCurrent_table(TABLE);
        table_label.setText("当前表" + TABLE);
    }

    private void initGui() {
        All_types = findViewById(R.id.id_ins_type_edit);
        Ed_ins_name = findViewById(R.id.Ed_ins_name);
        Ed_ins_value = findViewById(R.id.Ed_ins_value);
        Ed_ins_info = findViewById(R.id.Ed_ins_info);
        Ed_ins_gparser = findViewById(R.id.Ed_ins_Gparser);
        Button btn_ins_add = findViewById(R.id.btn_ins_add);
        Button btn_ins_canceled = findViewById(R.id.btn_ins_delete);
        All_ins = findViewById(R.id.id_list_of_instructions);
        btn_ins_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ed_ins_name.getText().toString().length() > 0 && Ed_ins_value.getText()
                        .toString().length() > 0 && Ed_ins_info.getText().toString().length() > 0
                        && Ed_ins_gparser.getText().toString().length() > 5
                ) {
                    AtomInstruct ainss = new AtomInstruct();
                    ainss.setIns_ID(selected_ins.getIns_ID());
                    ainss.setIns_name(Ed_ins_name.getText().toString());
                    ainss.setIns_Value(Ed_ins_value.getText().toString());
                    ainss.setIns_Info(Ed_ins_info.getText().toString());
                    ainss.setIns_type(INS_type);
                    ainss.setIns_gparser(Ed_ins_gparser.getText().toString());
                    int sud;
                    String operate = "Null Operation";
                    if (MyDbhelper.Db_is_opened()) {
                        if (isUpdateIns) {
                            sud = MyDbhelper.update_Instruction(ainss);
                            isUpdateIns = false;
                            operate = "Update";

                        } else {
                            sud = MyDbhelper.insert_Instruction(ainss);
                            operate = "Insert";
                        }
                        if (sud != -1)
                            Toast.makeText(DBManager.this, operate + Ed_ins_name.getText().toString() + "successful!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        All_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                INS_type = instruction_types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private boolean istable_exist(String table) {
        List<String> ls = MyDbhelper.list_all_tables();

        for (int i = 0; i <= ls.size() - 1; i++) {
            if (table.equals(ls.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        MyDbhelper.close();
        super.onDestroy();
    }
}