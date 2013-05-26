/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class WSNControlPane extends JFrame{
    private Simulation sim;
    private int kill;
    private int isRun;
    //private int run;
    public WSNControlPane() throws HeadlessException {
        super("WSN Control Pane");
        sim=null;
        kill=0;
        isRun=0;
        setSize(600,400);
        setLocationRelativeTo(null);
        //setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp=getContentPane();
        cp.setLayout(new GridBagLayout());
        Box box=Box.createVerticalBox();
        cp.add(box);
        JLabel agentsCntLabel=new JLabel("agents num");
        final JTextField agentsCnt=new JTextField("250");
        //agentsCnt.setHorizontalAlignment(JTextField.CENTER);
        //agentsCnt.setPreferredSize(new Dimension(10,5));
        JLabel ifguiLabel=new JLabel("enable jade gui");
        final JRadioButton enable=new JRadioButton("enable",true);
        final JRadioButton disable=new JRadioButton("disable",false);
        final ButtonGroup ifgui=new ButtonGroup();
        
        ifgui.add(enable);
        ifgui.add(disable);
        Box agentsCntBox=Box.createHorizontalBox();
        agentsCntBox.add(agentsCntLabel);
        agentsCntBox.add(agentsCnt);
        box.add(agentsCntBox);
        Box ifguiBox=Box.createHorizontalBox();
        ifguiBox.add(ifguiLabel);
        ifguiBox.add(enable);
        ifguiBox.add(disable);
        box.add(ifguiBox);
        JLabel simMethod=new JLabel("sim method");
        final JRadioButton flourishButton=new JRadioButton("flourish",true);
        final JRadioButton autoRandomButton=new JRadioButton("atuo random",false);
        final JRadioButton manulRandomButton=new JRadioButton("manul random",false);
        final JRadioButton classicButton=new JRadioButton("classic",false);
        final ButtonGroup methodGroup=new ButtonGroup();
        methodGroup.add(flourishButton);
        methodGroup.add(autoRandomButton);
        methodGroup.add(manulRandomButton);
        methodGroup.add(classicButton);
        Box methodBox=Box.createHorizontalBox();
        methodBox.add(simMethod);
        methodBox.add(flourishButton);
        methodBox.add(autoRandomButton);
        methodBox.add(manulRandomButton);
        methodBox.add(classicButton);
        box.add(methodBox);
        JLabel hostLabel=new JLabel("host");
        final JTextField host=new JTextField("127.0.0.1");
        Box hostBox=Box.createHorizontalBox();
        hostBox.add(hostLabel);
        hostBox.add(host);
        box.add(hostBox);
        JLabel portLabel=new JLabel("port");
        final JTextField port=new JTextField("3321");
        Box portBox=Box.createHorizontalBox();
        portBox.add(portLabel);
        portBox.add(port);
        box.add(portBox);
        JLabel routeLabel=new JLabel("agent id");
        final JTextField routeTextField=new JTextField("0");
        JButton routeButton=new JButton("Show Route");
        routeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int agentid=Integer.parseInt(routeTextField.getText());
                if(agentid>=PublicData.agentCnt)
                {
                    JOptionPane.showMessageDialog(null,"invid agent id","Error",
                    JOptionPane.INFORMATION_MESSAGE);
                }
                else if(sim==null)
                {
                    JOptionPane.showMessageDialog(null,"Simulation is not Running!!!","Take Care",
                    JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    ShowRouteTable showRouteTable = new ShowRouteTable(agentid);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        Box routeBox=Box.createHorizontalBox();
        routeBox.add(routeLabel);
        routeBox.add(routeTextField);
        routeBox.add(routeButton);
        box.add(routeBox);
        
        JButton startButton=new JButton("Start");
        startButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(isRun==0){
                PublicData pd=PublicData.getData();
                pd.port=port.getText();
                pd.host=host.getText();
                pd.agentCnt=(int)Integer.parseInt(agentsCnt.getText());
                pd.span=(float)(40*Math.sqrt(pd.agentCnt));
                pd.classicPowerDis=pd.span*pd.span/24;
                pd.warmupTime=pd.agentCnt*pd.warmpara;
                if(enable.isSelected())
                {
                    pd.ifgui="true";
                }
                else
                {
                     pd.ifgui="false";
                   }
                if(flourishButton.isSelected())
                {
                    pd.simType=PublicData.SimType.FLOURISH;
                }
                else if(autoRandomButton.isSelected()){
                    pd.simType=PublicData.SimType.AUTORANDOM;
                }
                else if(manulRandomButton.isSelected()){
                    pd.simType=PublicData.SimType.MANULRANDOM;
                }
                else if (classicButton.isSelected())
                {
                    pd.simType=PublicData.SimType.CLASSIC;
                }
                sim=new Simulation();
                isRun=1;
            }
            else
            {
                  JOptionPane.showMessageDialog(null,"Simulation is Running!!!","Take Care",
                  JOptionPane.INFORMATION_MESSAGE);
            }
        }});
        //box.add(startButton);
        JButton stopButton=new JButton("Stop");
        stopButton.addActionListener(new StopActionListener());
        //box.add(stopButton);
        JLabel sendLabel=new JLabel("Send");
        final JTextField senderJTF=new JTextField("0");
        final JTextField recverJTF=new JTextField("0");
        JButton sendButton=new JButton("Send");
        Box sendBox=Box.createHorizontalBox();
        sendBox.add(sendLabel);
        sendBox.add(senderJTF);
        sendBox.add(recverJTF);
        sendBox.add(sendButton);
        sendButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(senderJTF.getText().equals(recverJTF.getText()))
                {
                     JOptionPane.showMessageDialog(null,"Sender and Recver is the same one!","Take Care",
                     JOptionPane.INFORMATION_MESSAGE);
                }
                else if(sim==null)
                {
                 JOptionPane.showMessageDialog(null,"Simulation is not Running!!!","Take Care",
                 JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    int sender=Integer.parseInt(senderJTF.getText());
                    int recver=Integer.parseInt(recverJTF.getText());
                    if(sender<0||sender>=PublicData.agentCnt
                            ||recver<0||recver>=PublicData.agentCnt)
                    {
                     JOptionPane.showMessageDialog(null,"beyond id!!!","Take Care",
                    JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        SendCommand sc=new SendCommand(recver);
                        try{
                        PublicData.ac[sender].putO2AObject(sc, false);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        
        });
        
        box.add(sendBox);
        box.add(startButton);
        box.add(stopButton);
        //box.setSize(100,50);
        JMenuBar jb=new JMenuBar();
        //box.add(jb);
        JMenu fileMenu=new JMenu("File");
        jb.add(fileMenu);
        JMenuItem exitMenu=new JMenuItem("Exit");
        fileMenu.add(exitMenu);
        exitMenu.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
        System.exit(0);
        }
    });
        JMenu helpMenu=new JMenu("Help");
	jb.add(helpMenu);
	JMenuItem aboutItem=new JMenuItem("About");
	helpMenu.add(aboutItem);
	aboutItem.addActionListener(new AboutActionListener());
        this.setJMenuBar(jb);
        this.setVisible(true);
    }
    
    private class AboutActionListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      JOptionPane.showMessageDialog(null,"pengfeituan@gmail.com","Author",
        JOptionPane.INFORMATION_MESSAGE);
}
    
}

    private class StopActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(isRun==0){
                JOptionPane.showMessageDialog(null,"Simulation is not Running!!!","Take Care",
                JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                kill=1;
                sim=null;
                System.exit(0);
                //sim.stop();//unsafe
                //sim=null;
                /*
                  try
                  {
                     // sim.finalize();
                     // PublicData.getData().finalize();
                      //
                  }
                  catch(Throwable ex)
                  {
                      ex.printStackTrace();
                  }
                  */
                  
            }
        }
    }

    public static void main(String[] args)
    {
        WSNControlPane cp= new WSNControlPane();
    }
    /*
    private class SimulationThread extends Thread{
        @Override
        public void run()
        {
            Simulation simt=new Simulation();
            while(true)
            {
                if(kill==1)
                {
                    System.out.println("thread stop here!");
                    System.exit(0);
                    return;
                }
                else 
                {
                    try{
                        sleep(5000);
                    }
                    catch(Exception ex)
                            {
                                ex.printStackTrace();
                            }
                }
            }
        }
    }
    */
}
