import java.io.*;

public class Kadai1_70210082 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);
		
		/*出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数, 31:1,3,5,7,8,10,12月の日数)*/
		
		if(year >= 1){
				switch(month){
					case 2:
						if(year%4 == 0){
							if(year%100 != 0){
								System.out.println("29");
							}
							else{
								if(year%400 == 0){
									System.out.println("29");
								}
								else{
									System.out.println("28");
								}
							}
						}
						else{
							if(year%100 != 0){
								System.out.println("29");
							}
							else{
								if(year%400 == 0){
									System.out.println("29");
								}
								else{
									System.out.println("28");
								}
							}
							
						}
						break;
						
						case 4:
						case 6:
						case 9:
						case 11:
							System.out.println("30");
						break;
						
						case 1:
						case 3:
						case 5:
						case 7:
						case 8:
						case 10:
						case 12:
							System.out.println("31");
						break;
						
						default:
							System.out.println("-1");
						break;
					}
				}
				else{
					System.out.println("-1");
				}
			}
		}
