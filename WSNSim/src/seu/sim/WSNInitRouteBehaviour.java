/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author duan
 */
import java.util.Timer;
import java.util.TimerTask;
public  class WSNInitRouteBehaviour extends WSNBaseBehaviour{
    private Timer timer;
    private TimerTask task;
    
     public  WSNInitRouteBehaviour(WSNAgent a)
     {
         super(a); 
     }
     public void firstGreet()
    {
        
        if(agent.minNeighbour!=-1)
        {
           // System.out.println("first Greet of "+agent.getMyId());
            RoutePackage nearest=new RoutePackage(0,agent.minNeighbour,agent.getMyId());
            ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID(Integer.toString(agent.minNeighbour),AID.ISLOCALNAME));
           try{
            msg.setContentObject(nearest);
            //agent.hset.add(nearest.getHashNum());//should not send future;
            if(msg.getAllIntendedReceiver()!=null)agent.WSNSend(msg);
           }
           catch(Exception ex)
           {
            ex.printStackTrace();
           }
        }
    }

    @Override
    public boolean done() {
        return super.done(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void action() {
       super.action();
       //To change body of generated methods, choose Tools | Templates.
        
        timer=new Timer();
        task=new TimerTask()
        {

           @Override
           public void run() {
               firstGreet();
               //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           }
            
         };
        timer.schedule(task, PublicData.warmupTime);
    }
     
}
