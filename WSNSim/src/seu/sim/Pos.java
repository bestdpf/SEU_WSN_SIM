/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.sim;

/**
 *
 * @author duan
 */

public class Pos {
    private float x;
    private float y;
    public Pos()
    {
        x=0.0f;
        y=0.0f;
    }
    public Pos(float a,float b)
    {
        x=a;
        y=b;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
}
