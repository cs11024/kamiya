import java.io.*;

class Test_FallDouble {
    public static void main(String args[])throws IOException {
BufferedReader br =
    new BufferedReader(new InputStreamReader(System.in));

String str = br.readLine();
double x_speed = Double.parseDouble(str);
	double x, y, y_speed;
	double g;

	g = -10;
	x = 0;
	y = 100000;
       
	y_speed = 0;

	while( y >=0 ) {
	    y_speed = y_speed+ g/100000;
	    x = x + x_speed/100000;
	    y = y + y_speed/100000;
	}
	System.out.print("estimated distance = " + x + "\n");

	return;
    }
}
