import java.io.*;

class Test_FallInt {
    public static void main(String args[]) {
        int x, y, x_speed, y_speed;
        int g;
        int num;
         String buf;
      try{
          BufferedReader br
              = new BufferedReader(new InputStreamReader(System.in));

          buf = br.readLine();
          num = Integer.parseInt(buf);

        g = -10;
        
        x = 0;
        y = 100000;
        y_speed = 0;
        
        while( y >= 0 ) {
            y_speed = y_speed + g;
            x = x + num;
            y = y + y_speed;
        }
        System.out.print("estimated distance = " + x + "\n");
      }catch(Exception e) {
       System.out.print("Error:" + e);
      }

        
        return;
    }
}

