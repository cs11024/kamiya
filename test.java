import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;

public class Test {
	public static void main(String args[]) throws IOException{
		int x, y;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		x = Integer.parseInt(buf);
		buf = br.readLine();
		y = Integer.parseInt(buf);
		//System.out.println("" + x + " - " + y + " = " + (x - y) );
		System.out.println(x - y);
		buf = br.readLine();
		x = Integer.parseInt(buf);
		buf = br.readLine();
		y = Integer.parseInt(buf);
		System.out.println(x - y);
		buf = br.readLine();
		x = Integer.parseInt(buf);
		buf = br.readLine();
		y = Integer.parseInt(buf);
		System.out.println(x - y);
	}
}