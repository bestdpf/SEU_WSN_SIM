/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import jade.core.behaviours.*;
import jade.core.Agent;
public class WSNTopParallelAutoRandomCommBehaviour extends ParallelBehaviour{

    public WSNTopParallelAutoRandomCommBehaviour(WSNAgent a) {
        super(a,ParallelBehaviour.WHEN_ALL);
        this.addSubBehaviour(new WSNAutoRandomTickerBehaviour(a));
        this.addSubBehaviour(new SecondReceiveAndActBehaviour(a));
        //this.addSubBehaviour(new WSNBaseUpdateRouteTableBehaviour(a));
    }
    
}
