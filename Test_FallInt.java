import java. io.*;

class Test_FallInt {
	public static void main(String args[]) {
		/*cs14011*/
		int x,y, x_speed,y_speed;
		int g;
		
		g = -10;

		x = 0;
		y = 100000;

		try{
		 BufferedReader br
			= new BufferedReader
			(new InputStreamReader(System.in));
			x_speed = Integer.parseInt(br.readLine());

		
			y_speed = 0;

			while(y >= 0) { 
				y_speed = y_speed + g;
				x = x + x_speed;
				y = y + y_speed;
			}
			System.out.print("estimated distance = "+x+"\n");

			return;

		} catch(Exception e){
			System.out.print("ERROR"+e);
		}
	}
}

