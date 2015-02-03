import java.io.*;

class FallInt {
  public static void main(String args[]) {
    int x, y, x_speed, y_speed;
    int g;
    String buf;
    
    g = -10;
    x = 0;
    y = 100000;
    x_speed = 800;
    y_speed = 0;

    try{
      //System.err.print("x_speed: "); /* out でも可 */
      BufferedReader br
        = new BufferedReader(new InputStreamReader(System.in));
      buf = br.readLine();
      x_speed = Integer.parseInt(buf);
      while( y >= 0 ) {
        y_speed = y_speed + g;
        x = x + x_speed;
        y = y + y_speed;
      }
      //System.out.print("estimated distance = " + x + "\n");
      System.out.println(x);
    }catch(Exception e){
      System.out.print("Error:" + e);
    }
    return;
  }
}
