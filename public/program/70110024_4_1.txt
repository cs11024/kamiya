import java.io.*;

class FallDouble {
  public static void main(String args[]) {
    double x, y, x_speed, y_speed;
    double g;
    String buf;
    
    g = -10;
    x = 0;
    y = 100000;
    x_speed = 800;
    y_speed = 0;
    
    try {
      //System.out.print("x_speed: ");
      BufferedReader br
        = new BufferedReader(new InputStreamReader(System.in));
      buf = br.readLine();
      x_speed = Double.parseDouble(buf);
      
      while( y >= 0 ) {
        y_speed = y_speed + g/100000;
        x = x + x_speed/100000;
        y = y + y_speed/100000;
      }
      //System.out.print("estimated distance = " + x + "\n");
      System.out.println(x);
    }catch(Exception e){
      System.out.print("Error:" + e);
    }
    return;
  }
}
