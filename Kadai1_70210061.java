import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Kadai1_70210061 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		if (1 <= month && month <= 12) {
			if ( year >= 1 ) {
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
					//System.out.println(31);
					System.out.println(year+"年"+month+"月"+"は31日です");
				} else if (month == 4 || month == 6 || month == 9 || month == 11) {
					System.out.println(30);
				} else if (month == 2) {
					if (year % 400 == 0) {
						System.out.println(28);
					} else if (year % 100 == 0) {
						if (year % 400 != 0) {
							System.out.println(29);
						}
					} else if (year % 4 == 0) {
						if(year % 100 != 0){
							System.out.println(28);
						}
					}
				}
			} else {
				System.out.println(-1);
			}
		} else {
			System.out.println(-1);
		}
	}
}
