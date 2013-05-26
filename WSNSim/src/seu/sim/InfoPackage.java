/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import java.util.LinkedList;
import java.io.Serializable;
public class InfoPackage implements Serializable{
    private int dst;
    private int src;
    private int jump;
    private INFOTYPE type;
    private String info;
    public LinkedList<Integer> rtTab;
    private int firstNext;
    public InfoPackage()
    {
        dst=0;
        src=0;
        jump=0;
        type=INFOTYPE.SEND;
        firstNext=-1;
        info=null;
        rtTab=new LinkedList();
    }
    public InfoPackage(int d,int s,int j,INFOTYPE t,int fn,String str)
    {
        dst=d;
        src=s;
        jump=j;
        type=t;
        info=str;
        firstNext=fn;
        rtTab=new LinkedList();
    }
    public void setFirstNext(int fn)
    {
        firstNext=fn;
    }
    public int getFirstNext()
    {
        return firstNext;
    }
    public void incJump()
    {
        jump++;
    }
    public void setInfo(String str)
    {
        info=str;
    }
    public void setDst(int d)
    {
        dst=d;
    }
    public void setSrc(int s)
    {
        src=s;
    }
    public void setType(INFOTYPE t)
    {
        type=t;
    }
    public INFOTYPE getType()
    {
        return type;
    }
    public int getDst()
    {
        return dst;
    }
    public int getSrc()
    {
        return src;
    }
    public int getJump()
    {
        //return getRtSize();
        return jump;
    }
    public int getRtSize()
    {
        return rtTab.size();
    }
    public String getInfo()
    {
        return info;
    }
    public void setJump(int j)
    {
        jump=j;
    }
    public  enum INFOTYPE{
        SEND,
        BACK;
    };
}
