/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import java.util.Timer;
import java.util.TimerTask;
public class WSNBaseUpdateRouteTableBehaviour extends WSNBaseBehaviour{
    private Timer timer;
    private TimerTask task;

    public WSNBaseUpdateRouteTableBehaviour(WSNAgent a) {
        super(a);
        timer=new Timer();
    }
    
    @Override
    public boolean done() {
        return true;
        //return super.done(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void action() {
        task=new TimerTask(){

            @Override
            public void run() {
                agent.BaseUpdateRouteTable();
                //agent.putGlobalRouteTable();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        };
        timer.schedule(task,PublicData.baseUpdateRouteInter, PublicData.baseUpdateRouteInter);
        //super.action(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
