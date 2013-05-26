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
public class RouteTable implements Serializable {
    public LinkedList<RouteItem>list;
    public  RouteTable()
    {
        list=new LinkedList<>();
        RouteItem defaultRoute=new RouteItem(-1,-2);//default is not reachable
        list.add(defaultRoute);
    }
    public RouteTable(RouteTable rt)
    {
        list=rt.list;
    }
    public void addRouteItem(RouteItem rt)
    {
        list.add(rt);
    }
    public int findByDst(int dst)//return the index of dst, if not found return -1;
    {//can also change not found to return drt;
     int i;
     int drt=0;//default route,always at index 0;
     for(i=0;i<list.size();i++)
     {
         if(list.get(i).matchDst(dst))return i;
         if(list.get(i).matchDst(-1))drt=i;
     }
     return -1;
     //return drt;
    }
    private void updateItem(int inx,RouteItem ri)
    {
        list.set(inx, ri);
        this.setUnUpdateItem(inx);//make it unUpdate;
    }
    public void upadateItemNext(int inx,int newNext)
    {
        RouteItem ri=list.get(inx);
        ri.setNext(newNext);
        updateItem(inx,ri);
    }
    public int findFirstByNext(int n)
    {
        int i;
        for(i=0;i<list.size();i++)
        {
            if(list.get(i).matchNext(n))return i;
        }
        return -1;
    }
    public void clear()
    {
        list.clear();
    }
    public void removeRouteItem(int inx)
    {
        list.remove(inx);
    }
    public void removeRouteItem(RouteItem ri)
    {
        list.remove(ri);
    }
    public void removeByDst(int  d)
    {
        int inx=findByDst(d);
        if(inx!=-1)removeRouteItem(inx);
    }
    public void removeFirstByNext(int n)
    {
        int inx=findFirstByNext(n);
        if(inx!=-1)removeRouteItem(inx);
    }
    public void removeAllByNext(int n)
    {
        while(true)
        {
            int inx=findFirstByNext(n);
            if(inx!=-1)removeRouteItem(inx);
            else break;
        }
    }
    /*
     * -1 direct send
     * -2 cannot reachable, dst is not found
     * -3 got
     * other >=0 next 
     */
    public int getNext(int dst)
    {
        int inx=findByDst(dst);
        if(inx!=-1)return list.get(inx).getNext();
        else return -2;
    }
    public void disableItem(int i)
    {
       setEnableItemState(i,false);
    }
    public void enableItem(int i)
    {
        setEnableItemState(i,true);
    }
    public void setUpdateItem(int i)
    {
        setUpdateItemState(i,true);
    }
    public void setUnUpdateItem(int i)
    {
        setUpdateItemState(i,false);
    }
     private void setEnableItemState(int i,boolean state)
    {
        if(i<list.size())
        {
            RouteItem ri=list.get(i);
            ri.setEnableState(state);
            list.set(i, ri);
        }
        else
        {
            System.err.println("beyond the size of list");
        }
    }
    private void setUpdateItemState(int i,boolean state)
    {
        if(i<list.size())
        {
            RouteItem ri=list.get(i);
            ri.setUpdateState(state);
            list.set(i, ri);
        }
        else
        {
            System.err.println("beyond the size of list");
        }
    }
    public boolean isItemEnable(int inx)
    {
        return list.get(inx).isEnable();
    }
}
