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
import java.util.LinkedList;
/*
 * src is next, dst is dst in RouteItem
 */
public class RoutePackage  implements Serializable{
    private int type;
    private int src;
    private int dst;
    public LinkedList<Integer>routeList;
    public RoutePackage()
    {
        type=-1;//error type
        src=0;
        dst=0;
        routeList=new LinkedList();
    }
    public RoutePackage(int t,int s,int d)
    {
        type=t;
        src=s;
        dst=d;
        routeList=new LinkedList();
    }

    public void setType(int i)
    {
        type=i;
    }
    public int getType()
    {
        return type;
    }
    public void setSrc(int s)
    {
        src=s;
    }
    public int getSrc()
    {
        return src;
    }
    public Integer getHashNum()
    {
        return (PublicData.agentCnt*src+dst);
    }
    public void setDst(int d)
    {
        dst=d;
    }
    public int getDst()
    {
        return dst;
    }
}
