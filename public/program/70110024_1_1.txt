import java.io.*;

public class Kadai1 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);
		
		/*(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		if (year <= 0 || month <= 0 || month > 12) {
			System.out.println(-1);;
		} else {
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				System.out.println(30);
			} else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				System.out.println(31);
			} else if (month == 2 && (year % 4) == 0) {
				System.out.println(29);
			} else {
				System.out.println(28);
			}
		}
	}
}
