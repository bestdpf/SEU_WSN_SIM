/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import jade.core.behaviours.Behaviour;
public class WSNBaseBehaviour extends Behaviour{
    protected WSNAgent agent;
    public WSNBaseBehaviour(WSNAgent a) {
        agent=a;
        myAgent=agent;
    }
    public WSNBaseBehaviour()
    {
        agent=(WSNAgent)myAgent;
        myAgent=agent;
        //initPara();
    }
    

    @Override
    public boolean done() {
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void action() {
      //
    }
}
