/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import java.awt.*;
import java.util.Timer;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections15.functors.ConstantTransformer;

public class MapViewer extends JFrame{
    private int edgeCnt;
    private DirectedGraph<Number,Number> g = null;

    private VisualizationViewer<Number,Number> vv = null;

    private StaticLayout<Number,Number> layout = null;

    java.util.Timer timer;
    private void  startMap()
    {
        g=new DirectedSparseMultigraph<>();
        layout=new StaticLayout<>(g);
        vv=new VisualizationViewer<>(layout, getSize());
        timer=new Timer();
        timer.schedule(new UpdateMapTask(), PublicData.refreshInter, PublicData.refreshInter);
        //vv.repaint();
    }
    
    public MapViewer(String title)
    {
        super(title);
        setSize(800,600);
        //setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        edgeCnt=0;
        startMap();
        Container cp=getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.lightGray);
        //vv.getModel().getRelaxer().setSleepTime(500);
        vv.setGraphMouse(new DefaultModalGraphMouse<Number,Number>());

        vv.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Number>());
        vv.setForeground(Color.white);
        cp.add(vv,BorderLayout.CENTER);
        getContentPane().setFont(new Font("Serif", Font.PLAIN, 12));
        setVisible(true);
        JMenuBar jb=new JMenuBar();
        JMenu fileMenu=new JMenu("File");
        jb.add(fileMenu);
        JMenuItem exitMenu=new JMenuItem("Exit");
        fileMenu.add(exitMenu);
        exitMenu.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        MapViewer.this.dispose();
     }
    });
        JMenu helpMenu=new JMenu("Help");
	jb.add(helpMenu);
	JMenuItem aboutItem=new JMenuItem("About");
	helpMenu.add(aboutItem);
	aboutItem.addActionListener(new AboutActionListener());
        this.setJMenuBar(jb);
        setLocationRelativeTo(null);
    }
    private class AboutActionListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
       // test();
      JOptionPane.showMessageDialog(null,"pengfeituan@gmail.com","Author",
        JOptionPane.INFORMATION_MESSAGE);
}
}
    public class UpdateMapTask extends TimerTask{
    @Override
    public void run() {
        //System.out.println("update map now");
        LinkedList<EdgeOp> backlist=new LinkedList<>();
        PublicData pd=PublicData.getData();
        int i;
        PosOp tmpPosOp;
        EdgeOp tmpEdgeOp;
        Pos tmpPos;
        for(i=0;i<pd.posTask.size();i++)
        {
            tmpPosOp=pd.posTask.get(i);
            if(tmpPosOp.op==0)
            {//op==0 add a pos
                MapViewer.this.g.addVertex(new Integer(tmpPosOp.pos));
                tmpPos=pd.agentsPos.get(tmpPosOp.pos);
                MapViewer.this.layout.setLocation(tmpPosOp.pos, tmpPos.getX(),tmpPos.getY());
            }
            else
            {
                MapViewer.this.g.removeVertex(tmpPosOp.pos);
            }
        }
        for(i=0;i<pd.edgeTask.size();i++)
        {
            tmpEdgeOp=pd.edgeTask.get(i);
            if(tmpEdgeOp.op==0)
            {//add a edge
                //int edgeNum=g.getEdgeCount();
                MapViewer.this.g.addEdge(edgeCnt,tmpEdgeOp.start,tmpEdgeOp.end);
                backlist.add(new EdgeOp(1,edgeCnt,0,pd.displayCnt));
                edgeCnt++;
            }
            else
            {//remove
                
                if(tmpEdgeOp.delay<=0)
                {
                        g.removeEdge(tmpEdgeOp.start);
                }
                else
                {
                    tmpEdgeOp.delay--;
                    backlist.add(tmpEdgeOp);
                }
            }
        }
        pd.resetTask();
        pd.edgeTask=backlist;
         vv.repaint();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
    
}
