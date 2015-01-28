import java.io.*;

public class Kadai2 {
	public static void main(String args[]) throws IOException {
		int equalCount = 0;
		int a, b, c;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		a = Integer.parseInt(buf);
		buf = br.readLine();
		b = Integer.parseInt(buf);
		buf = br.readLine();
		c = Integer.parseInt(buf);
		
		/*(-1:不正な引数, -2:三角形ではない, 1:不当辺三角形, 2:二等辺三角形, 3:正三角形)*/
		if (a == b) equalCount = equalCount + 1;
		if (a == c) equalCount = equalCount + 2;
		if (b == c) equalCount = equalCount + 3;
		if (a <= 0 || b <= 0 || c <= 0) {
			System.out.println(-1);
		} else if (equalCount == 0) {
			if (a + b <= c || b + c <= a || a + c <= b) {
				System.out.println(-2);
			} else {
				System.out.println(-1);
			}
		} else if (equalCount > 3) {
			System.out.println(3);
		} else if ((equalCount == 1 && a + b > c) || (equalCount == 2 && a + c > b) || (equalCount == 3 && b + c > a)) {
			System.out.println(2);
		} else {
			System.out.println(-2);
		}
	}
}
