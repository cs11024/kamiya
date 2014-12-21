package models;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class Data extends Model {
	@Id
	public String id;
	public int rightnum;
	
	public Data(String id, int rightnum) {
		this.id = id;
		this.rightnum = rightnum;
	}
	
	public static Finder<String, Data> find = new Finder<String, Data>(
			String.class, Data.class);
	
	public static void register(String id, int rightnum) {
		new Data(id, rightnum).save();
		//return find.where().eq("id", id).eq("password", password).findUnique();
	}
	
	public static int kaisuu(String id) {
		//return find.where().eq("id", id).eq("password", password).findUnique();
		return find.where().like("id", id+"%").findRowCount();
	}
	
	public static int rireki(String id) {
		//return find.where().eq("id", id).eq("password", password).findUnique();
		return find.where().eq("id", id).findUnique().rightnum;
		//return find.where().like("id", id+"%").findRowCount();
	}
	
	/*public static int kadainumber(String id) {
		//return find.where().eq("id", id).eq("password", password).findUnique();
		return find.where().like("id", id+"%").findUnique();
	}*/
}
