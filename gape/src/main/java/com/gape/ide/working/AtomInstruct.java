package com.gape.ide.working;

/**
 * Created by cyandr on 2016/12/5.
 */
public class AtomInstruct {
    private int Ins_ID;
    private String Ins_type;
    private String Ins_name;
    private String Ins_Value;
    private String Ins_Info;
    private String Ins_gparser;

    AtomInstruct(int id, String name, String value, String info, String gparser) {
        if (id != 0) {
            Ins_ID = id;
        }

        Ins_name = name;
        Ins_Value = value;
        Ins_Info = info;
        Ins_gparser = gparser;
    }

    AtomInstruct() {

    }

    public int getIns_ID() {
        return Ins_ID;
    }

    public void setIns_ID(int ins_ID) {
        Ins_ID = ins_ID;
    }

    public String getIns_name() {
        return Ins_name;
    }

    public void setIns_name(String ins_name) {
        Ins_name = ins_name;
    }

    public String getIns_Value() {
        return Ins_Value;
    }

    public void setIns_Value(String ins_Value) {
        Ins_Value = ins_Value;
    }

    public String getIns_Info() {
        return Ins_Info;
    }

    public void setIns_Info(String ins_Info) {
        Ins_Info = ins_Info;
    }

    public String getIns_type() {
        return Ins_type;
    }

    public void setIns_type(String ins_type) {
        Ins_type = ins_type;
    }

    public String getIns_gparser() {
        return Ins_gparser;
    }

    public void setIns_gparser(String ins_gparser) {
        Ins_gparser = ins_gparser;
    }
}
