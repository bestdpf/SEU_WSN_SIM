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
public class WSNTopParallelManulRandomCommBehaviour extends ParallelBehaviour{

    public WSNTopParallelManulRandomCommBehaviour(WSNAgent a) {
        super(a,ParallelBehaviour.WHEN_ALL);
        this.addSubBehaviour(new WSNManulRandomTickerBehaviour(a));
        this.addSubBehaviour(new SecondReceiveAndActBehaviour(a));
        //this.addSubBehaviour(new WSNBaseUpdateRouteTableBehaviour(a));
    }
    
}
