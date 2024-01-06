package com.hpn.model.mpoint;

/**
 * 由于android.graphic.point中存储x和y的数据只是int型的，
 * 所以需重新构建point用于存储数据（double,double）,如经纬度。
 * 参考android.graphics.point
 * Created by CUMT_BJX on 2017/3/20.
 */

public class MPoint {
    public double x;
    public double y;
    public float azimuth;//当前的
    public String azimuthStr;//1s内的所有方位角
    public MPoint() {}


    public MPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public MPoint(int x,int y){
        this.x=(double)x;
        this.y=(double)y;
    }

    /**
     * Set the point's x and y coordinates
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * set the point's current azimuth
     * @param azimuth
     */
    public void setAzimuth(float azimuth){
        this.azimuth=azimuth;
    }
    /**
     * get the point's current azimuth
     * @return
     */
    public float getAzimuth(){
        return this.azimuth;
    }


    /**
     * set the point's azimuth in one second
     * almost 5 times
     * @param string
     */
    public void setAzimuthStr(String string){
        this.azimuthStr=string;
    }
    /**
     * get the point's azimuth string in one second
     * @return
     */
    public String getAzimuthStr(){
        return this.azimuthStr;
    }
    /**
     * Negate the point's coordinates
     */
    public final void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Offset the point's coordinates by dx, dy
     */
    public final void offset(double dx, double dy) {
        x += dx;
        y += dy;
    }
    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(double x, double y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MPoint point = (MPoint) o;

        if (x != point.x) return false;
        if (y != point.y) return false;
        if (azimuth!=point.azimuth) return false;
        return true;
    }
    @Override
    public String toString() {
        return "MPoint(" + x + ", " + y +", 当前方位角为" + azimuth+", 方位角字符串为" +azimuthStr+")";
    }


    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


}
