package com.gape.ide.working;


import com.gape.cyandr.opengl.GLEx;

/**
 * Created by cyandr on 2016/9/11 0011.
 * 基类  指令类的基类，子类继承两个接口，
 * 指令中的各个成员都要存储到数据库中
 *
 * @author cyandr
 */
public abstract class Instructions extends AtomInstruct {
    protected Point anchorPoint;
    protected Point realeasePoint;
    protected long CreateTime;
    private int IID;        //数据库中的ID值
    private String java;       //   代表的程序语句
    private String InsName;     //指令或者语句名称
    private String InsImg;      //  指令显示的图像（）
    private String I_Shape;     //指令代表的绘图
    private String I_Para;      //指令的参数
    private String Iinfo;

    protected int getIID() {
        return IID;
    }

    protected void setIID(int IID) {
        this.IID = IID;
    }

    protected String getInsImg() {
        return InsImg;
    }

    protected void setInsImg(String insImg) {
        InsImg = insImg;
    }

    protected String getInsName() {
        return InsName;
    }

    protected void setInsName(String insName) {
        InsName = insName;
    }

    protected String getJava() {
        return java;
    }

    protected void setJava(String java) {
        this.java = java;
    }

    protected String getI_Shape() {
        return I_Shape;
    }

    protected void setI_Shape(String i_Shape) {
        I_Shape = i_Shape;
    }

    protected String getI_Para() {
        return I_Para;
    }

    public void setI_Para(String i_Para) {
        I_Para = i_Para;
    }

    public abstract void DrawShape(GLEx glEx, Point point);

    public abstract void Write2Exe(StringBuilder stringBuilder);

    public String getIinfo() {
        return Iinfo;
    }

    public void setIinfo(String iinfo) {
        Iinfo = iinfo;
    }

    public void setreleasePoint(Point poin) {
        realeasePoint = poin;
    }

    public Point getrealeasePoint() {

        return realeasePoint;
    }

    public Point getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Point anchorPoint) {
        this.anchorPoint = anchorPoint;
    }
}
