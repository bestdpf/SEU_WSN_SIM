/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import java.util.Random;
public class WSNClassicTickerBehaviour extends TickerBehaviour{
    public  int TestNum=0;
    protected WSNAgent agent;
    private Random rand;
    private Random recvRand;
  //  public Agent varAgent;
    /*
    public WSNAutoRandomTickerBehaviour(Agent a,long peroid) {
        super(a,peroid);
    }
    */
    public WSNClassicTickerBehaviour(WSNAgent a, long per)
    {
        super(a,per);
        agent=a;
        rand= new Random();
        recvRand=new Random();
       // varAgent=a;
    }
    public WSNClassicTickerBehaviour(WSNAgent a)
    {
        super(a,PublicData.trickerInter);
        agent=a;
        rand= new Random();
        recvRand=new Random();
       // varAgent=a;
    }
    protected void behav() {
        if(Math.abs(rand.nextInt()%(1/*PublicData.maxJump*/))==0)
        {
            int i;
            int recvId=agent.getMyId();
            for(i=0;i<PublicData.agentCnt;i++)
            {
                if(agent.getPowerDis(i)<PublicData.classicPowerDis)recvId=i;
            }
            //int recvId=agent.getMyId()%(PublicData.agentCnt/20);//Math.abs(recvRand.nextInt()%PublicData.agentCnt);
            if(recvId==agent.getMyId())return;//skip myself2myself pack
            String str;
            str="test str from "+agent.getMyId()+" to "+recvId;
            agent.updateNext();
            int nextFirst=agent.getRandomNextId();//is id not agent id
            if(agent.neighbour.size()<=0)return;//not have a neighbour 
            InfoPackage ip=new InfoPackage(recvId,agent.getMyId(),0,InfoPackage.INFOTYPE.SEND,nextFirst,str);
            ip.rtTab.add(agent.getMyId());
            //ip.rtTab.add(nextFirst);
            try{
            if(PublicData.DEBUG)
            {
                System.out.println(agent.getMyId()+" send a str to "+ip.getDst());
            }
            ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(ip);
            msg.addReceiver(new AID(Integer.toString(recvId),AID.ISLOCALNAME));
            agent.WSNSend(msg);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onTick() {
        behav();
        //manualSend();
    }
    public void manualSend()
    {
        Object obj=agent.getO2AObject();
        if(obj!=null)
        {
            if(obj instanceof SendCommand)
            {
            SendCommand sc=(SendCommand)obj;
            int recvId=sc.recv;
            if(recvId==agent.getMyId())return;//skip myself2myself pack
            String str;
            str="test str from "+agent.getMyId()+" to "+recvId;
            agent.updateNext();
            int nextFirst=agent.getRandomNextId();
            if(agent.neighbour.size()<=0)return;//not have a neighbour 
            InfoPackage ip=new InfoPackage(recvId,agent.getMyId(),0,InfoPackage.INFOTYPE.SEND,nextFirst,str);
            ip.rtTab.add(agent.getMyId());
            //ip.rtTab.add(nextFirst);
            try{
            if(PublicData.DEBUG)
            {
                System.out.println(agent.getMyId()+" send a str to "+ip.getDst());
            }
            ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(ip);
            AID nextAgent;
            if(agent.isNeighbour(recvId))nextAgent=new AID(Integer.toString(recvId),AID.ISLOCALNAME);
            else nextAgent=new AID(Integer.toString(agent.neighbour.get(nextFirst)),AID.ISLOCALNAME);
            msg.addReceiver(nextAgent);
            agent.WSNSend(msg);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            }
            else
            {
                System.err.println("error send command");
            }
        }
    }
}
