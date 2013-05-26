/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 * a singleton data class
 * @author duan
 */
//import java.util.Queue;
import java.util.LinkedList;
import jade.wrapper.AgentController;
public class PublicData {
    private static final PublicData mdata=new PublicData();
    private PublicData(){
       agentsPos=new LinkedList<Pos>();
       posTask=new LinkedList<PosOp>();
       edgeTask=new LinkedList<EdgeOp>();
       ac=null;
       agentCnt=250;
       host="127.0.0.1";
       port="3321";
       ifgui="true";
       maxJump=64;
       displayCnt=2;
       refreshInter=150;
       routeDisplayCnt=maxJump*displayCnt;
       dis=50;
       span=(float)(40*Math.sqrt(agentCnt));
       classicPowerDis=span*span/24;
       sendSleep=1000;
       warmpara=15;
       warmupTime=warmpara*agentCnt;
       sendSleep=1000;
       addBehaviourSleep=1000;
       createAgentSleep=5;
       DEBUG=true;
       baseUpdateRouteInter=20000;
       trickerInter=1000;
       simType=SimType.FLOURISH;
       rt=new LinkedList<>();
       o2aSize=20;
    };
    public static boolean DEBUG;
    public static LinkedList<Pos>agentsPos;
    public static LinkedList<PosOp> posTask;
    public static LinkedList<EdgeOp> edgeTask;
    public static AgentController[] ac;
    public static int agentCnt;
    public static String host;
    public static String port;
    public static String ifgui;
    public static int maxJump;//max jump nums agent can bear of commucating
    public static float dis;//max dis that can commucate
    public static float span;//the whole wsn span;
    public static int displayCnt;
    public static int routeDisplayCnt;
    public static long refreshInter;
    public static long warmpara;//should be bigger than createAgentSleep
    public static long warmupTime;
    public static long sendSleep;
    public static long addBehaviourSleep;
    public static long createAgentSleep;
    public static long baseUpdateRouteInter;
    public static long trickerInter;
    public static int o2aSize;
    public static LinkedList<RouteTable>rt;
    public static SimType simType;
    public static double classicPowerDis;
    public static PublicData getData(){
        return mdata;
    }
    public void addPosTask(PosOp task)
    {
        posTask.add(task);
    }
    public void addEdgeTask(EdgeOp task)
    {
        edgeTask.add(task);
    }
    public void resetTask()
    {
        posTask.clear();
        edgeTask.clear();
    }
    public void resetEdgeTask()
    {
        edgeTask.clear();
    }
    public void resetPosTask()
    {
        posTask.clear();
    }
    public void addAgentsPos(Pos loc)
    {
        agentsPos.add(loc);
    }
    public void addAgentsPos(int inx,Pos loc)
    {
        agentsPos.add(inx,loc);
    }
    public void addRouteTable(RouteTable tmprt)
    {
        rt.add(tmprt);
    }
    public void addRouteTable(int inx,RouteTable tmprt)
    {
        rt.add(inx,tmprt);
    }
/*
 * never use the finalize() 
 * it is very dangerous!
 */ 
  @Override
    protected void finalize() throws Throwable {
        agentsPos=null;
        posTask=null;
        edgeTask=null;
        ac=null;
        host=null;
        port=null;
        ifgui=null;
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    public enum SimType{
      FLOURISH,
      AUTORANDOM,
      MANULRANDOM,
      CLASSIC;
  };
}
