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
public class WSNTopParallelFlourishBehaviour extends ParallelBehaviour{

    public WSNTopParallelFlourishBehaviour(WSNAgent a) {
        super(a,ParallelBehaviour.WHEN_ALL);
        this.addSubBehaviour(new WSNInitRouteBehaviour(a));
        this.addSubBehaviour(new ReceiveAndActBehaviour(a));
        this.addSubBehaviour(new WSNBaseUpdateRouteTableBehaviour(a));
    }
    
}
