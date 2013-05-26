/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import java.io.Serializable;
public class RouteItem implements Serializable{
    private int dst;
    private int next;
    private boolean enable;
    private boolean toUpdate;
    public RouteItem()
    {
        dst=-1;
        next=-3;
        enable=true;
        toUpdate=false;
    }
    public RouteItem(int d,int n)
    {
        dst=d;
        next=n;
        enable=true;
        toUpdate=false;
    }
    public RouteItem(int d,int n,boolean en,boolean up)
    {
        dst=d;
        next=n;
        enable=en;
        toUpdate=up;
    }
    public boolean isEnable()
    {
        return enable;
    }
    public void disable()
    {
        enable=false;
    }
    public void setEnableState(boolean state)
    {
        enable=state;
    }
    public void setUpdateState(boolean state)
    {
        toUpdate=state;
    }
    public void setUpdate()
    {
        toUpdate=true;
    }
    public void setUnUpdate()
    {
        toUpdate=false;
    }
    public boolean isUpdate()
    {
        return toUpdate;
    }
    public void enable()
    {
        enable=true;
    }
    public void setDst(int d)
    {
        dst=d;
    }
    public int getDst()
    {
        return dst;
    }
    public void setNext(int n)
    {
        next=n;
    }
    public int getNext()
    {
        return next;
    }
    public boolean matchDst(int d)
    {
        return d==dst;
    }
    public boolean matchNext(int n)
    {
        return n==next;
    }
}
