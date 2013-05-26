/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
public class RouteStatus {
    private int succ;
    private int fa;
    public RouteStatus(){
        succ=0;
        fa=0;
    }
    public void setSucc(int s)
    {
        succ=s;
    }
    public void setFa(int f)
    {
        fa=f;
    }
    public int getSucc()
    {
        return succ;
    }
    public int getFa()
    {
        return fa;
    }
    public void incFa()
    {
        fa++;
    }
    public void incSucc()
    {
        succ++;
    }
}
