package com.gape.ide.working;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.gape.cyandr.gapeandroid.gape.GpeApplication;
import com.gape.cyandr.gapeandroid.gape.R;
import com.gape.ide.working.instructions.VariableWindow;

import java.util.List;

public class OpenglesActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    TextView tip;
    GpeApplication MyApp;
    Button btn_pop, btn_finish_operaton;
    PopupWindow operation, operation_right;
    LayoutInflater mInflator;
    DBHelper localDB;
    List<AtomInstruct> LLS;
    InstuctionListAdapter linadapter;
    MyRender myRender;
    GPView gpView;
    String type;
    AtomInstruct nowIns;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MessageConst.QUIT_RIGHT_EDIT:
                    onFinishEditingElement();
                    break;
                case MessageConst.VARIABLE_POSITION_COFIRMED:
                    tip.setVisibility(View.INVISIBLE);

                    //  btn_finish_operaton.setEnabled(true);
                    btn_pop.setEnabled(true);
                    Point point = gpView.getVariableCoordinate();
                    GpeSystem.CM.Addins(point);

                    GpeSystem.addVariable(nowIns.getIns_type(), nowIns.getIns_name());
                    break;
            }


            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GpeSystem.MContext = getApplicationContext();
        GpeSystem.Initializing();
        onMain();
        MyApp = (GpeApplication) getApplication();
        MyApp.setMyAppHandler(handler);
        setContentView(R.layout.opengl_main);
        gpView = findViewById(R.id.gpview);
        btn_pop = findViewById(R.id.btn_operation);
        myRender = new MyRender(MyApp);
        gpView.setMyRender(myRender);
        gpView.setMyApp(MyApp);
        gpView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        Log.i("执行顺序", "surface 生成");
        tip = findViewById(R.id.text_touch_tip);

        Log.i("执行顺序", "系统初始化 生成");
        btn_pop.setOnClickListener(view -> onClickPop());
        localDB = new DBHelper(this);
        localDB.setCurrent_table(MyApp.getCurrent_table());
    }

    public void onMain() {

        initialization(false, true, true);

    }

    public void initialization(boolean isOrientation, boolean FullScreen, boolean HideTitle) {
        if (HideTitle)
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (FullScreen)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (isOrientation)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void onClickPop() {
        mInflator = LayoutInflater.from(this);
        LinearLayout pop = (LinearLayout) mInflator.inflate(R.layout.pop_operation, null);
        ListView instruction = pop.findViewById(R.id.list_instru);
        if (!localDB.Db_is_opened()) localDB.open();
        LLS = localDB.readVariables();
        // localDB.close();
        Log.i("弹出窗口", "顺利弹出1");
        if (LLS != null)
            linadapter = new InstuctionListAdapter(OpenglesActivity.this, LLS);
        instruction.setAdapter(linadapter);

        operation = new PopupWindow(pop, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        operation.setFocusable(true);
        operation.setOutsideTouchable(true);
        operation.update();
        operation.setBackgroundDrawable(getResources().getDrawable(R.color.touming,null));
        operation.showAtLocation(btn_pop, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, btn_pop.getHeight() + 5);
        Log.i("弹出窗口", "顺利弹出");
        instruction.setOnItemClickListener((parent, view, position, id) -> {
            nowIns = LLS.get(position);
            gpView.GpViewMode = GPView.WOEKINGMODE.PLACE_VARIABLE;
            OnVariable();

            //VariableWindow(position);
        });
    }

    private void OnVariable() {
        tip.setText("点击确定操作位置！");

        // btn_finish_operaton.setEnabled(false);
        btn_pop.setEnabled(false);

    }

    private void VariableWindow(int position) {


        try {
            final VariableWindow.Builder builder = new VariableWindow.Builder(OpenglesActivity.this);
            builder.setNegativeButton((dialog, which) -> dialog.dismiss());

            builder.setPositiveButton((dialog, which) -> {
                if (builder.getVariableName() != null) {
                    long create_time = System.currentTimeMillis();
                    AtomInstruct m = LLS.get(position);
                    GpeSystem.addVariable(type, m.getIns_Value() + " " + builder.getVariableName());
                    dialog.dismiss();
                    operation.dismiss();
                    gpView.requestRender();
                } else {
                    Toast.makeText(OpenglesActivity.this, "请输入变量名称！", Toast.LENGTH_SHORT).show();
                }
            });

            builder.create().show();
            final String[] strings = OpenglesActivity.this.getResources().getStringArray(R.array.variable_type);
            builder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    type = strings[position];

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    type = strings[0];
                }
            });
        } catch (UnknownError error) {
            error.printStackTrace();
        }
        gpView.requestRender();

    }

    public void onFinishEditingElement() {
        mInflator = LayoutInflater.from(this);
        LinearLayout pop2 = (LinearLayout) mInflator.inflate(R.layout.operation_right, null);
        btn_finish_operaton = pop2.findViewById(R.id.btb_finish_edit);
        operation_right = new PopupWindow(pop2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        operation_right.setFocusable(true);
        operation_right.setOutsideTouchable(true);
        operation_right.update();
        operation_right.setBackgroundDrawable(getResources().getDrawable(R.color.touming));
        operation_right.showAtLocation(btn_finish_operaton, Gravity.RIGHT | Gravity.BOTTOM, 0, btn_finish_operaton.getHeight() + 5);
        Log.i("弹出窗口", "顺利弹出");
        btn_finish_operaton.setOnClickListener(v -> {
            myRender.setOperationLayer(GPView.OPERATION_LAYER.OPERATION_IN_GLOBAL);
            operation_right.dismiss();
        });
    }

    private boolean OnOpeerating(AtomInstruct ins) {
        String ins_type = ins.getIns_type();
        switch (ins_type) {
            case "宏定义":

                break;
            case "基本函数":


                return true;

            case "操作符":


                return true;
            case "类与结构定义":


                return true;
            default:
                return ins_type.equals("GUI控件");
        }

        return false;
    }
}