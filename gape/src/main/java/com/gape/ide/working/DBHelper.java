package com.gape.ide.working;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.gape.cyandr.gapeandroid.gape.GpeApplication;
import com.gape.cyandr.gapeandroid.gape.R;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyt on 16-5-26.
 */
public class DBHelper {
    private static final String DB_NAME = "introduction.db"; //保存的数据库文件名

    private static final String DB_PATH2 = Environment.getExternalStorageDirectory() + "/" + "Gape";
    //在手机里存放数据库的位置
    private static final String OUT_PATH = Environment.getExternalStorageDirectory() + "/LGPSDataBase";
    private static final String INS_ID = "IID";
    private static final String INS_TYPE = "IType";
    private static final String INS_NAME = "IName";
    private static final String INS_VALUE = "IValue";
    private static final String INS_INFO = "IInfo";
    private static final String INS_GPARSER = "IGparser";
    private SQLiteDatabase datab;

    private boolean db_is_opened = false;
    private Context mcontext;
    private String Current_Table;


    DBHelper(Context context) {

        mcontext = context;
        File paths = mcontext.getDatabasePath(DB_NAME);
        System.out.println(Environment.getDataDirectory().getAbsolutePath());
        initDB(GetDbPath());

    }
public   String GetDbPath()
{
    String PACKAGE_NAME =   mcontext.getPackageName();
    String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    return  DB_PATH + "/" + DB_NAME;


}
    boolean open(String DbFile) {
        datab = SQLiteDatabase.openOrCreateDatabase(DbFile, null);
        db_is_opened = true;
        return true;
    }

    public boolean importDB(String DbFile) {
        try {
            if (new File(DbFile).exists()) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                System.out.println("数据库存在****");
                File outdata = new File(OUT_PATH);
                if (!outdata.exists()) {
                  /* for (String file:outdata.list()){
                       File file1=new File(file);*/
                    outdata.mkdirs();
                    //}
                }
                File target = new File(OUT_PATH + "/" + DB_NAME);
                if (target.exists()) {
                    target.delete();
                } else {
                    target.createNewFile();
                }
                // InputStream is = mcontext.getResources().openRawResource(R.raw.data); //欲导入的数据库
                File dbin = new File(DbFile);
                FileInputStream fis = new FileInputStream(dbin);
                FileOutputStream fos = new FileOutputStream(OUT_PATH + "/" + DB_NAME);
                int BUFFER_SIZE = 40000;
                byte[] buffer = new byte[BUFFER_SIZE];
                int count;
                while ((count = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                fis.close();
            }
            System.out.println("数据库存在");

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();

        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        } catch (NullPointerException n) {
            System.out.println("还没有对象哦");
        }
        return false;
    }

    private void initDB(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                System.out.println(dbfile);
                System.out.println("数据库不存在");
                File dbFile = new File(dbfile);
                InputStream is = mcontext.getResources().openRawResource(R.raw.introduction); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                int BUFFER_SIZE = 40000;
                byte[] buffer = new byte[BUFFER_SIZE];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            System.out.println("数据库存在");
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();


        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        } catch (NullPointerException n) {
            System.out.println("还没有对象哦");
        }
    }

    void createNewTable(String table_name) {
        final String NewTable =
                "create table " + table_name + "( " +
                        "IID integer primary key autoincrement, " +
                        "IType text not null," +
                        "IName text not null, " +
                        "IValue text not null," +
                        "IInfo text not null," +
                        "IGparser text not null);";
        datab.execSQL(NewTable);
    }

    int insert_Instruction(AtomInstruct ins) {
//        String sqs = "insert into" + " " + current_table + " " + "(IType,IName,IValue,IInfo,IGparser) values ('" + ins
//                .getIns_type() + "', '" + ins.getIns_name() + "', '" + ins.getIns_Value() + "', '" + ins.getIns_Info() +
//                "+', ' '+ ins.getIns_Info() + "')";
        //datab.execSQL("insert into current_table (IType,IName,IValue,IInfo) values (?,?,?,?)", new
        //        Object[]{ins.getIns_type(), ins.getIns_name(),
        //      ins.getIns_Value(), ins.getIns_Info(), ins.getIns_gparser()});

        ContentValues persondata = new ContentValues();
        persondata.put(INS_TYPE, ins.getIns_type());
        persondata.put(INS_NAME, ins.getIns_name());
        persondata.put(INS_VALUE, ins.getIns_Value());
        persondata.put(INS_INFO, ins.getIns_Info());
        persondata.put(INS_GPARSER, ins.getIns_gparser());


        return (int) datab.insert(Current_Table, null, persondata);


    }

    int update_Instruction(AtomInstruct ins) {

//        datab.execSQL("update ?  set IType=?,IName=?,IValue=?,IInfo=? where IId=?",
//                new String[]{Current_Table, ins.getIns_type(), ins.getIns_name(), ins.getIns_Value(),
//                        ins.getIns_Info(), String.valueOf(ins.getIns_ID())});
        ContentValues persondata = new ContentValues();
        persondata.put(INS_TYPE, ins.getIns_type());
        persondata.put(INS_NAME, ins.getIns_name());
        persondata.put(INS_VALUE, ins.getIns_Value());
        persondata.put(INS_INFO, ins.getIns_Info());
        persondata.put(INS_GPARSER, ins.getIns_gparser());
        String[] nis = new String[]{String.valueOf(ins.getIns_ID())};
        return datab.update(Current_Table, persondata, INS_ID + " =?", nis);

    }

    public String read_Instruction(String Iname) {


        Cursor cursor = datab.rawQuery("select " + INS_VALUE + " from " + Current_Table + " where IName=?", new String[]{Iname});
        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("key_value"));
        }
        return null;
    }

    List<AtomInstruct> readVariables() {
        List<AtomInstruct> aa = new ArrayList<>();
        int i = 0;
        Cursor cursor = datab.rawQuery("select * from " + Current_Table, null);
        Log.i("WRTITE", "select * from" + Current_Table);


        while (cursor.moveToNext()) {
            AtomInstruct oldsst = new AtomInstruct();
            oldsst.setIns_ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("IID"))));
            oldsst.setIns_type(cursor.getString(cursor.getColumnIndex("IType")));
            oldsst.setIns_name(cursor.getString(cursor.getColumnIndex("IName")));
            oldsst.setIns_Value(cursor.getString(cursor.getColumnIndex("IValue")));
            oldsst.setIns_Info(cursor.getString(cursor.getColumnIndex("IInfo")));
            oldsst.setIns_gparser(cursor.getString(cursor.getColumnIndex(INS_GPARSER)));
            aa.add(oldsst);

        }

        cursor.close();
        return aa;
    }

    void close() {
        datab.close();
        db_is_opened = false;
    }

    List<String> list_all_tables() {
        List<String> table_lists = new ArrayList<>();
        final String ss = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name";
        Cursor cursor = datab.rawQuery(ss, null);
        while (cursor.moveToNext()) {
            String sss = cursor.getString(0);
            table_lists.add(sss);
        }
        return table_lists;
    }

    public String getCurrent_table() {
        return Current_Table;
    }

    void setCurrent_table(String current_table) {
        this.Current_Table = current_table;
    }

    boolean Db_is_opened() {
        return db_is_opened;
    }
}


