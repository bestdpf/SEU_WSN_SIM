/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
public class EdgeOp {
    public int op;
    public int start;
    public int end;
    public int delay;
    public EdgeOp(int task,int s,int e,int d)
    {
        /*
         * if op==1, it is to remove the edge, 
         * here end is meaningless, but start is the edge id;
         */
        op=task;
        start=s;
        end=e;
        delay=d;
    }
}
