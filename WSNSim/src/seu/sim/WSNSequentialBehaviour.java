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
public class WSNSequentialBehaviour extends SequentialBehaviour{

    public WSNSequentialBehaviour(WSNAgent a) {
        super(a);
        this.addSubBehaviour(new WSNInitRouteBehaviour(a));
        this.addSubBehaviour(new ReceiveAndActBehaviour(a));
    }
    
}
