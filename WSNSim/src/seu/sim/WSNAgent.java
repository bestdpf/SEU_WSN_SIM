/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.*;
import java.util.concurrent.TimeUnit;
import jade.util.leap.Iterator;
public class WSNAgent extends Agent{
    public HashSet<Integer> hset;
    public  LinkedList<Integer> neighbour;
    public LinkedList<RouteStatus>rs;
    public double minDisPower;
   // public double minDis;//var val
    public  int minNeighbour;
    private int myid;
    private int randomNextId;
    private Random rand;
    private Random nextRand;
    //public String receiver;
    public RouteTable rt;
    
    public WSNAgent() {
        this.hset = new HashSet();
        rt=new RouteTable();
        rand=new Random();
        nextRand=new Random();
        randomNextId=-1;
        rs=new LinkedList();
        //initPara();
        //this.receiver = str;
    }
    public int getRandomNextId()
    {
        return randomNextId;
    }
    /*
     * inc next method
     */
    public void incNextId()
    {
        if(neighbour.size()<=0)return;
        randomNextId=(++randomNextId)%neighbour.size();
    }
    /*
     * random method
     */
    public void setRandomNext()
    {
        if(neighbour.size()<=0)return;
        randomNextId=Math.abs(nextRand.nextInt())%neighbour.size();
     }
    public void updateNext()
    {
        incNextId();
        //setRandomNext();
    }
    public int getMyId()
    {
        return myid;
    }
    public void failUpdateRouteStatus(int inx)
    {
        RouteStatus tmpRs=rs.get(inx);
        tmpRs.incFa();
        rs.set(inx, tmpRs);
    }
    public void succUpdateRouteStatus(int inx)
    {
        RouteStatus tmpRs=rs.get(inx);
        tmpRs.incSucc();
        rs.set(inx, tmpRs);
    }
    /*
     * return -1 if not found in ip's routTab
     */
    public int getMyLoc(InfoPackage ip)
    {
        int i;
        for(i=0;i<ip.rtTab.size();i++)
        {
            if(ip.rtTab.get(i)==getMyId())return i;
        }
        return -1;
    }
     private void initPara()
    {
        this.setEnabledO2ACommunication(true, PublicData.o2aSize);
        PublicData pd=PublicData.getData();
        myid=(int)Integer.parseInt(getLocalName());
        this.minDisPower=pd.span*pd.span;
        minNeighbour=-1;
        neighbour=new LinkedList();
       // PublicData pd=PublicData.getData();
        int i;
        for(i=0;i<pd.agentCnt;i++)
        {
            if(i==getMyId())continue;//is myself
            if(isReach(i))
            {
                neighbour.add(i);
                rs.add(new RouteStatus());
                rt.addRouteItem( new RouteItem(i,-1));//direct reach
                if(getPowerDis(i)<minDisPower)
                {
                    /*
                    if(minNeighbour>-1)System.out.println("update minDisPower "
                            +"old is "+minDisPower+" new is "
                            +getPowerDis(i)+"\n pos "+getMyId()+
                            " is "+PublicData.agentsPos.get(getMyId()).toString()
                            +"\n pos "+i+" is "+PublicData.agentsPos.get(i).toString()
                            +"\n pos old is "+ PublicData.agentsPos.get(minNeighbour).toString());
                   */
                    minDisPower=getPowerDis(i);
                    minNeighbour=i;
                }
            }
        }
        //System.out.println(getMyId()+" dump minNeighbour "+minNeighbour);
    }
     
       protected double calcPowerDis(int inx1,int inx2)
    {
        return Math.pow(PublicData.agentsPos.get(inx1).getX()
                -PublicData.agentsPos.get(inx2).getX(),2.0)+
                Math.pow(PublicData.agentsPos.get(inx1).getY()
                -PublicData.agentsPos.get(inx2).getY(),2.0);
    }
    protected double getPowerDis(int inx)
    {
        return calcPowerDis(getMyId(),inx);
    }
    protected boolean isReach(int inx)
    {
        if(getPowerDis(inx)<PublicData.dis*PublicData.dis)return true;
        else return false;
    }
    
    public void WSNSend(ACLMessage msg)
    {
        try{
            Thread.sleep(Math.abs(rand.nextLong()%PublicData.sendSleep));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //System.out.println("wsn send now");
        super.send(msg);
        PublicData pd=PublicData.getData();
        EdgeOp eo;
        Iterator it=msg.getAllReceiver();
        while(it.hasNext())
        {
            Object obj=it.next();
            if(obj instanceof AID)
            {
                AID receiver=(AID)obj;
                int rid=Integer.parseInt(receiver.getLocalName());
                //System.out.println("dump recver is "+rid+" and sender is "+getMyId());
                eo=new EdgeOp(0,getMyId(),rid,10);
                pd.addEdgeTask(eo);
            }
            else
            {
                System.out.println("error can't get  receiver !");
            }
        }
    }
    @Override
    protected void setup() {
        //System.out.println("I am a new agent!");
        //System.out.println("my local-name is "+getAID().getLocalName());
       // System.out.println("my address are:");
       // Iterator it=getAID().getAllAddresses();
       // while(it.hasNext()){
        //System.out.println("- "+it.next());
        //}
       // Object[] args=getArguments();
        //this.receiver=(String)args[0];
        //random sleep to add behaviour
        initPara();
        /*
        int i;
        for(i=0;i<neighbour.size();i++)
        {
            System.out.println( getMyId()+ " 's neighbour "+neighbour.get(i));
        }
        System.out.println(getMyId()+" agent pos is "+PublicData.agentsPos.get(getMyId()).getX()
                +" "+PublicData.agentsPos.get(getMyId()).getY());
                */
        Random rand=new Random();
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextLong()%PublicData.addBehaviourSleep);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        //this.addBehaviour(new WSNToParallelBehaviour(this));
        switch(PublicData.simType){
            case FLOURISH: 
            this.addBehaviour(new WSNTopParallelFlourishBehaviour(this));
            break;
            case AUTORANDOM:
            this.addBehaviour(new WSNTopParallelAutoRandomCommBehaviour(this));
            break;
            case MANULRANDOM:
            this.addBehaviour(new WSNTopParallelManulRandomCommBehaviour(this));
            break;
            case CLASSIC:
            this.addBehaviour(new WSNTopParallelClassicCommBehaviour(this));
            break;
    }
        
    }
     /*
     * update the route table to a shorter one
     */
    public boolean isNeighbour(int i)
    {
        java.util.Iterator it;
        it=neighbour.iterator();
        while(it.hasNext())
        {
            if(it.next().equals(i))return true;
        }
        return false;
    }
    public void BaseUpdateRouteTable()
    {
        //if(PublicData.DEBUG)System.out.println("base update route table");
        int i;
        for(i=0;i<rt.list.size();i++)
        {
            RouteItem tmpri=rt.list.get(i);
            int tmpid=i;
            if(tmpri.matchDst(-1)||tmpri.matchDst(-2))continue;//default route skip, (-1,-2)
            int newNext=-1;
            boolean loop=false;
            //-1 is got, -2 is unreachable
            while(!tmpri.matchNext(-1))
            {
                if(rt.list.get(tmpid).isUpdate())
                {//loop
                    loop=true;
                    break;
                }
                rt.setUpdateItem(tmpid);
                newNext=tmpri.getNext();
                if(newNext==-2)
                {//unreachable end,update the ris to -2 also;
                    break;
                }
                tmpid=rt.findByDst(newNext);
                if(tmpid==-1)//can not found table now
                {
                    break;
                }
                tmpri=rt.list.get(tmpid);
            }
            if(newNext!=-1)
            {
             if(loop)newNext=-2;
            int j;
            for(j=0;j<rt.list.size();j++)
            {
                if(rt.list.get(j).isUpdate())
                {
                 rt.upadateItemNext(j, newNext);
                }
            }
            }
        }
        putGlobalRouteTable();
    }
    public void putGlobalRouteTable()
    {
        PublicData.rt.set(getMyId(), rt);
      //  if(PublicData.DEBUG)System.out.println("set pd rt list: id: "
      //          +getMyId()+"rt size "+rt.list.size());
    }
}
