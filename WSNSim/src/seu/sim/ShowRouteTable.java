/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author duan
 */
import java.util.LinkedList;
public class ShowRouteTable extends JFrame{
    private LinkedList<RouteItem> list;
    private JTable tab;
    public ShowRouteTable(int agentid)
    {
        super("rt of "+agentid);
        list=PublicData.rt.get(agentid).list;
        Vector<String> colnames =new Vector();
        colnames.add("dst");
        colnames.add("next");
        Vector vec=new Vector();
        Iterator it=list.iterator();
        while(it.hasNext())
        {
            Vector tmpVec=new Vector();
            RouteItem ri=(RouteItem)it.next();
            tmpVec.add(ri.getDst());
            tmpVec.add(ri.getNext());
            vec.add(tmpVec);
        }
        tab=new JTable(vec,colnames);
        //this.getContentPane().add(tab);
        this.setSize(600,400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(new JScrollPane(tab));
        JScrollPane scrol = new JScrollPane(tab);
        scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrol); 
        setLocationRelativeTo(null);
    }
}
