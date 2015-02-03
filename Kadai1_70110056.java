import java.io.*;

public class Kadai1_70110056 {
	public static void main(String args[]) throws IOException {
		int year, month;
		String buf;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		buf = br.readLine();
		year = Integer.parseInt(buf);
		buf = br.readLine();
		month = Integer.parseInt(buf);

		/*
		 * 出力(-1:不正な引数, 28:閏年以外の2月の日数, 29:閏年の2月の日数, 30:4,6,9,11月の日数,
		 * 31:1,3,5,7,8,10,12月の日数)
		 */
		final int ERROR = -1;

		if (year < 1 || month < 1 || month > 12) {
			System.out.println(ERROR);
		} else {
			checkMonth(month, year);
		}

	}

	static void checkMonth(int month, int year) {
		if (cehck31days(month)) {
			System.out.println(31);
		} else if (month != 2) {
			System.out.println(30);
		} else if (checkURUU(year)) {
			System.out.println(29);
		} else {
			System.out.println(28);
		}

	}

	static boolean cehck31days(int m) {
		int[] day_31 = { 1, 3, 5, 7, 8, 10, 12 };
		for (int i = 0; i < day_31.length; i++) {
			if (day_31[i] == m)
				return true;
		}
		return false;
	}

	static boolean checkURUU(int y) {
		if ((y % 400) == 0) {
			return true;
		} else if ((y % 100) == 0) {
			return false;
		} else if ((y % 4) == 0) {
			return true;
		}
		return false;
	}
}
