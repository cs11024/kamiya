import java.io.*;
class Test_FallDouble {
    public static void main(String args[]) {
        double x, y, x_speed, y_speed;
        double g;
        String buf;
        
        g = -10;
        
        x = 0;
        y = 100000;
        x_speed = 800;
        y_speed = 0;

        try{
            BufferedReader br
                = new BufferedReader(new InputStreamReader(System.in));
            
            System.err.print("x_speed = ");
            buf = br.readLine();
            x_speed = Integer.parseInt(buf);
            
        } catch(Exception e) {
            System.err.print("Error:" + e);
        }
        
        while( y >= 0 ) {
         y_speed = y_speed + g/100000;
         x = x + x_speed/100000;
         y = y + y_speed/100000;
        }
        System.out.print("estimated distance = " + x + "\n");
            
        return;
    }
}


