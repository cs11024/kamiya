package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import play.db.*;

@Entity
public class User extends Model {

	@Id
	public String id;
	public String password;

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);

	public static User authenticate(String id, String password) {
		//System.out.println(id);
		//System.out.println(password);
		
		/*Connection cn = null;
		try {// 繋ぐ
			cn = DB.getConnection();
			PreparedStatement ps = cn.prepareStatement("select * from LOGIN");// SQL生む
			ResultSet rs = ps.executeQuery();// SQL実行
			while (rs.next()) {
				if ((id.equals(rs.getString("ID")))
						&& (password.equals(rs.getString("PASSWORD")))) {
					return new User(id, rs.getString("NAME"), password);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cn != null){
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;*/
		
		
		//new User("70110024","kamiya").save();
		//new User("70110001","aoki").save();
		return find.where().eq("id", id).eq("password", password).findUnique();
	}
}