import java.io.*;

public class Kadai2_70210064 {
	public static void main(String args[]) throws IOException {
		int a, b, c;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		a = Integer.parseInt(buf);
		buf = br.readLine();
		b = Integer.parseInt(buf);
		buf = br.readLine();
		c = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 0:三角形ではない, 2:二等辺三角形, 3:正三角形, 4:不当辺三角形)*/
		int result;
		if(a < 1 || b < 1 || c < 1) {
			result = -1;
		} else if(!(a + b > c && b + c > a && c + a > b)) {
			result = 0;
		} else if(a == b || a == c || b == c) {
			if(a == b && a == c) {
				result = 3;
			} else {
				result = 2;
			}
		} else {
			result = 4;
		}
		System.out.println(result);
		
		
		
	}
}
