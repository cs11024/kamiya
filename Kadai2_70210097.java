import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Kadai2_70210097 {
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
		if(a < 1 && b < 1 && c < 1){
			System.out.println("-1");		
		}else if(a < b + c && b < a + c && c < a + b){
			if(a==b && b == c && c == a){
				System.out.println("3");
			}else if(a == b || b == c || c == a){
				System.out.println("2");
			}else{
				System.out.println("4");
			}
		}else{
			System.out.println("0");
		}
		
		
	}
}
