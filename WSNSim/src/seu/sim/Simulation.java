/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
//import jade.core.Agent;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.ProfileException;
import javax.swing.*;
import jade.wrapper.ContainerController;
import jade.wrapper.AgentController;
import java.util.Random;
//import java.lang.Integer;

//import jade.util.leap.Properties;
//import jade.util.ExtendedProperties;
//import jade.util.Logger;

public class Simulation{
    //public FirstAgent a1,a2;
    public static int agentCnt;
    public static Random rand;
    public static Random rand1;
    public Simulation() {
        //a1=new FirstAgent("duan");
        //a2=new FirstAgent("alice");
        StartSimulation();
    }
    
    public  static void StartSimulation(){
        PublicData mData;
        mData = PublicData.getData();
        rand=new Random();
        rand1=new Random(1000);
       // System.out.println("test dump public data");
        JFrame viewer=new MapViewer("SEU Route Viewer");
        agentCnt=mData.agentCnt;
        String host=mData.host;
        String port=mData.port;
        String ifmain=mData.ifgui;
        String ifgui=mData.ifgui;
        //String agents="duan:seu.sim.FirstAgent( alice );alice:seu.sim.FirstAgent( duan )";
        Runtime rt=Runtime.instance();
        rt.setCloseVM(true);
        ProfileImpl p=new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, host);
        p.setParameter(Profile.MAIN_PORT, port);
        p.setParameter(Profile.MAIN, ifmain);
        p.setParameter(Profile.GUI, ifgui);
        //p.setParameter(Profile.AGENTS,agents);
        ContainerController cc=rt.createMainContainer(p);
        AgentController []ac=null;
        PublicData pd=PublicData.getData();
        PosOp tmpPosOp;
        Pos tmpPos;
        ac=new AgentController[agentCnt];
        int i;
        for(i=0;i<agentCnt;i++)
        {
                tmpPosOp=new PosOp(0,i);//add vertex i
                tmpPos=new Pos(rand.nextFloat()*pd.span+50,rand.nextFloat()*pd.span+50);
                pd.addPosTask(tmpPosOp);
                pd.addAgentsPos(tmpPos);
                pd.addRouteTable(new RouteTable());
               // System.out.println(i+"is at pos : ("+tmpPos.getX()+","+tmpPos.getY()+")");
        }
        try {
            //int i;
            for(i=0;i<agentCnt;i++){
                /*
                tmpPosOp=new PosOp(0,i);//add vertex i
                tmpPos=new Pos(rand.nextFloat()*pd.span+50,rand.nextFloat()*pd.span+50);
                pd.addPosTask(tmpPosOp);
                pd.addAgentsPos(tmpPos);
                */
                
                try{
                Thread.sleep(Math.abs(rand1.nextInt()%pd.createAgentSleep));
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                ac[i]=cc.createNewAgent(Integer.toString(i),"seu.sim.WSNAgent",null);
                ac[i].start();
            }
            pd.ac=ac;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        rand=null;
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }   
}
