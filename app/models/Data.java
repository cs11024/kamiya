package models;

import java.util.ArrayList;
import java.util.List;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Data extends Model {
	@Id
	public String id;
	public int rightnum;
	public String testcase;
	
	public Data(String id, int rightnum, String testcase) {
		this.id = id;
		this.rightnum = rightnum;
		this.testcase = testcase;
	}
	
	public static Finder<String, Data> find = new Finder<String, Data>(
			String.class, Data.class);
	
	public static void register(String id, int rightnum, String testcase) {
		new Data(id, rightnum, testcase).save();
		//return find.where().eq("id", id).eq("password", password).findUnique();
	}
	
	public static int kaisuu(String id) {
		//return find.where().eq("id", id).eq("password", password).findUnique();
		return find.where().like("id", id+"%").findRowCount();
	}
	
	public static int rightn(String id) {
		//return find.where().eq("id", id).eq("password", password).findUnique();
		return find.where().eq("id", id).findUnique().rightnum;
		//return find.where().like("id", id+"%").findRowCount();
	}
	
	public static String rireki(String id) {
		return find.where().eq("id", id).findUnique().testcase;
	}
	
	public static List<Data> kekka(String id) {
		//return find.where().like("id", id+"%").findUnique().testcase.toCharArray();
		//return find.where().like("id", id+"%").filterMany("testcase").findList().toArray(50);
		//return find.where().like("id", id+"%").like("testcase", "%").findList();
		return find.where().like("id", id+"%").findList();
		/*String[] uo;
		uo = new String[2];
		uo[0] = "uoo";
		return uo;*/
	}
}
