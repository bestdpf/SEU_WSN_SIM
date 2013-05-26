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
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.LinkedList;
public class ReceiveAndActBehaviour extends WSNBaseBehaviour{

    public ReceiveAndActBehaviour(WSNAgent a) {
        super(a);
    }

    @Override
    public boolean done() {
        return false;
        //return super.done(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void action() {
        super.action();
        ACLMessage msg=agent.receive();
        if(msg!=null){
            try{
            //System.out.println(agent.getMyId()+" got msg ");
            Object content=msg.getContentObject();
            if(content instanceof RoutePackage)
            {
                //System.out.println("the message is good!");
                RoutePackage rp=(RoutePackage)content;
                if(!agent.hset.contains(rp.getHashNum()))
                {
                    //System.out.println("the content is  dst"+rp.getDst() +"src "+rp.getSrc() );
                    agent.hset.add(rp.getHashNum());
                    /*
                     * Attention here!!!
                     * even if dst is here, if next=-2, the ri is also ac;
                     */
                          
                    if(!agent.isNeighbour(rp.getDst())&&(rp.getDst()!=agent.getMyId())&&rp.getSrc()!=-2)agent.rt.addRouteItem(new RouteItem(rp.getDst(),rp.getSrc()));
                    //System.out.println("hset size is "+agent.hset.size());
             
                   // System.out.println("decode msg ");
                    int i;
                    for(i=0;i<agent.neighbour.size();i++)
                    {
                        ACLMessage msgsend=new ACLMessage(ACLMessage.INFORM);
                        msgsend.setContentObject(rp);
                        msgsend.addReceiver(new AID(Integer.toString((int)agent.neighbour.get(i)),AID.ISLOCALNAME));
                        agent.WSNSend(msgsend);
                    }
                   
                }
                else
                {
                    //System.out.println("msg has also in set");
                }
                    
            }
            else if(content instanceof InfoPackage)
            {
                InfoPackage ip=(InfoPackage)content;
                if(ip.getType()==InfoPackage.INFOTYPE.SEND)
                {//send info pack
                    if(ip.getDst()==agent.getMyId())
                    {//i am the recv(dst)
                    if(PublicData.DEBUG)
                    {
                        System.out.println(agent.getMyId()+ " got msg from "
                                +ip.getSrc());
                    }
                    ip.setType(InfoPackage.INFOTYPE.BACK);
                    ACLMessage backMsg=new ACLMessage(ACLMessage.INFORM);
                    int lastNext=ip.rtTab.get(ip.rtTab.size()-1);
                    backMsg.addReceiver(new AID(Integer.toString(lastNext),AID.ISLOCALNAME));
                    backMsg.setContentObject(ip);
                    agent.WSNSend(backMsg);
                    }
                    else
                    {//the recv is not me
                        ip.incJump();
                        if(ip.getJump()<PublicData.maxJump)
                        {//forward
                             int myloc=agent.getMyLoc(ip);
                             agent.updateNext();
                             ip.incJump();
                             //update ip content below
                            if(myloc==-1)
                            {//not in the routeTab, the first time cross me
                                ip.rtTab.add(agent.getMyId());
                                if(PublicData.DEBUG)
                                {
                                    System.out.println("i am not in rttab");
                                }
                            }
                            else
                            {
                                if(PublicData.DEBUG)
                                {
                                    System.out.println("i am in rttab");
                                }
                                LinkedList<Integer> list=new LinkedList<>();
                                int i;
                                for(i=0;i<=myloc;i++)list.add(ip.rtTab.get(i));
                                ip.rtTab=list;
                            }
                            AID nextAgent;
                            if(agent.isNeighbour(ip.getDst()))nextAgent=new AID(Integer.toString(ip.getDst()),AID.ISLOCALNAME);
                            else nextAgent=new AID(Integer.toString(agent.neighbour.get(agent.getRandomNextId())),AID.ISLOCALNAME);
                            ACLMessage msg2=new ACLMessage(ACLMessage.INFORM);
                            msg2.addReceiver(nextAgent);
                            msg2.setContentObject(ip);
                            agent.WSNSend(msg2);
                        }
                        else
                        {//beyond maxJump
                            if(PublicData.DEBUG)
                            {
                                System.out.println("beyond max Jump");
                            }
                        }
                    }
                }
                else if(ip.getType()==InfoPackage.INFOTYPE.BACK)
                {//the type is back
                    if(ip.getSrc()==agent.getMyId())
                    {//come back of my send pack
                        if(PublicData.DEBUG)
                        {
                            System.out.println("back msg of myself");
                        }
                        agent.succUpdateRouteStatus(ip.getFirstNext());
                    }
                    else
                    {//to back forward a pack
                    int myRtLoc=agent.getMyLoc(ip);
                    if(myRtLoc==-1)
                    {//cannot find my loc in rt of pack
                        System.err.println("error the back msg is error");
                    }
                    else
                    {//found
                        if(PublicData.DEBUG)
                        {
                            System.out.println("help back the back-msg");
                        }
                        ACLMessage passBackMsg=new ACLMessage(ACLMessage.INFORM);
                        if(myRtLoc<1)
                        {//myRtLoc is 0, it is not correct
                         System.err.println("too short rt in pack");
                        }
                        passBackMsg.addReceiver(new AID(Integer.toString(ip.rtTab.get(myRtLoc-1)),AID.ISLOCALNAME));
                        passBackMsg.setContentObject(ip);
                        agent.WSNSend(passBackMsg);
                    }
                    }
                }
            }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            //msg=null;
            //System.out.println("I received this msg: "+msg.getContent()+" from "+msg.getSender().getLocalName()+" I am "+myAgent.getAID().getLocalName());
        }
        
            //this block is very important!!!
        else block();
       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
