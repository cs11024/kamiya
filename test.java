import java.io.*;

public class test {
	public static void main(String args[]) throws IOException{
		int x, y;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        buf = br.readLine();
        x = Integer.parseInt(buf);
        buf = br.readLine();
        y = Integer.parseInt(buf);
        System.out.println("" + x + " - " + y + " = " + (x - y) );
        buf = br.readLine();
        x = Integer.parseInt(buf);
        buf = br.readLine();
        y = Integer.parseInt(buf);
        System.out.println("" + x + " - " + y + " = " + (x - y) );
        buf = br.readLine();
        x = Integer.parseInt(buf);
        buf = br.readLine();
        y = Integer.parseInt(buf);
        System.out.println("" + x + " - " + y + " = " + (x - y) );
	}
}
