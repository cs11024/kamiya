import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Filenew {
	public static void main(String args[]){
		try {
			File file = new File("aiueo.txt");
			FileWriter filewriter = new FileWriter(file);
			
			filewriter.write("こんにちは");
			filewriter.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
