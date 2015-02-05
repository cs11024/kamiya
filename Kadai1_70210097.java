import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Kadai1_70210097 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		if(year >= 1 && month >= 1 && month <= 12){
			
			if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
				System.out.println(month + "月の日数は31日あります。");
			
			}else if(month == 4 || month == 6 || month == 9 || month == 11){
				System.out.println(month + "月の日数は30日あります。");
			}else if(year % 100 == 0){
				if(year % 400 != 0){
					System.out.println(month + "月の日数は28日あります。");
				}else if(year % 400 == 0){
					System.out.println(month + "月の日数は29日あります。");
				}
			}else if(year % 4  == 0|| year % 400 == 0){
				System.out.println(month + "月の日数は29日あります。");
			
			}
		}else{
			System.out.println("-1");
		}
		
		
	}
}
