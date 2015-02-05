import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Kadai1_70211008 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		buf = br.readLine();
		//System.out.println("年を入力");
		year = Integer.parseInt(buf);
		buf = br.readLine();
		//System.out.println("月を入力");
		month = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		//System.out.println(year);
		//System.out.println(month);
		check(year,month);
		
		

		
		
		
		
		
	}
	public static void check(int year,int month){
		int a = year;
		int b = month;
		boolean uru = false;
		
		if(a%4==0||a%400==0){
			
			uru =true;
			
			if(a%100==0){
				uru = false;
			}
			
			
		}
		
		
		if(b==4||b==6||b==9||b==11){
			System.out.println(30+"日");
		}else if(b<1||b>12||a<1){
			System.out.println(-1);
		}else if(b==2&&uru){
			System.out.println(29);
		}else if(b==2&&uru==false){
			System.out.println(28);
		}
		else{
			System.out.println(31);
		}
		
		
	}
}
