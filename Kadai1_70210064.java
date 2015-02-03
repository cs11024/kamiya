import java.io.*;

public class Kadai1_70210064 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		if(year < 1 || month > 12 || month < 1) {
			System.out.println(-1);
		} else if((year % 4 == 0 && year % 100 != 0 || year % 400 == 0) && month == 2) {
			System.out.println(29);
		} else if(month == 2) {
			System.out.println(28);
		} else {
			switch(month) {
			case 4:
			case 6:
			case 9:
			case 11: System.out.println(30);
			break;
			default: System.out.println(31);
			break;
			}
		}
		
		
		
	}
}
