package controllers;

import java.util.List;
import java.util.Map;

import play.db.ebean.Model.Finder;
import models.User;
import play.*;
import play.mvc.*;
//import play.api.mvc.MultipartFormData;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import play.data.validation.Constraints.*;

import java.io.*;

import play.api.*;

public class Application extends Controller {
    
    public static class Login {
    	/*ログインフォーム*/
    	public String id;
    	public String password;
    	
    	public String validate() {
    		if (User.authenticate(id, password) == null) {
    			return "Invalid id or password";
    		}
    		return null;
    	}
    }
    
    public static class MyForm {
    	/*なんか前作ったフォーム*/
    	public String id;
    	public String number;
    	public String filename;
    	
    	public String toString() {
    		return "ID:" + id + ", Number:" + number + ", FileName:" + filename;
    	}
    }
    
    /*public static class Check {
    	//チェックフォーム
    	public File checkfile;
    }*/
    
    public static Result index() {
		/*インデックスページ表示する*/
		String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
        return ok(index.render(title, user, form(MyForm.class)));   
    }
    
    public static Result login() {
    	/*ログインページ表示する*/
    	//User user1 = new User("70110024","kamiya","kamiya");    	
    	return ok(login.render(form(Login.class)));
    }
    
    public static Result authenticate() {
    	//System.out.println( form(Login.class));
    	Form<Login> loginForm = form(Login.class).bindFromRequest();
    	System.out.println(loginForm);
        if (loginForm.hasErrors()) {
        	return badRequest(login.render(loginForm));
        } else {
        	session().clear();
        	session("id", loginForm.get().id);
        	return redirect(routes.Application.index());
        }
    }
	
	public static Result sendform() {
		/*前作ったフォームを送るやつ*/
		String title = "Kamiya System Send Form";
		String user = session("id");
		Form<MyForm> form = form(MyForm.class).bindFromRequest();
		if (form.hasErrors()) {
			String msg = "入力に問題があります。";
			return badRequest(index.render(title, user, form));
		} else {
			MyForm data = form.get();
			String msg = data.toString();
			return ok(index.render(title, user, form));
		}
	}
	
	public static Result check() {
		/*チェックページ表示する*/
		String user = session("id");
        //return ok(check.render(form(Check.class), user));
        return ok(check.render(user));
    }
	
	/*public static Result checker() {
		String user = session("id");
		Form<Check> checkform = form(Check.class).bindFromRequest();
		System.out.println(checkform);
		if (checkform.hasErrors()) {
			return ok(result.render(user));
		} else {
			return redirect(routes.Application.result());
		}
	}*/
	
	public static Result checker() {
		String user = session("id");
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart checkf = body.getFile("checkf");
		String kadai = body.asFormUrlEncoded().get("kadai")[0];
		System.out.println(kadai);
		/*try {
			System.out.println(Runtime.getRuntime().exec("dir").getOutputStream().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (checkf != null) {
			String fileName = checkf.getFilename();
			String contentType = checkf.getContentType(); 
			File file = checkf.getFile();
			System.out.println(fileName);
			int point = fileName.lastIndexOf(".");
			if (point != -1) {
				String fname = fileName.substring(0, point);
				String extension = fileName.substring(point + 1);
				System.out.println(fname);
				if (extension.equals("java")) {
					try {
						//System.out.println(Runtime.getRuntime().exec("javac " + fileName).getOutputStream().toString());
						Runtime.getRuntime().exec("javac " + fileName);
						//ProcessBuilder builder = new ProcessBuilder("java", fname);
						ProcessBuilder builder = new ProcessBuilder("java", "-version");
			            Process process = builder.start();
			            InputStream stream = process.getErrorStream();
			            while (true) {
			                int c = stream.read();
			                if (c == -1) {
			                    stream.close();
			                    break;
			                }
			                System.out.print((char)c);
			            }
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					return redirect(routes.Application.result());
				}
			}
			flash("error", "Missing file");
			return redirect(routes.Application.index());
		} else {
			flash("error", "Missing file");
			return redirect(routes.Application.index());    
		}
		//return redirect(routes.Application.result());
	}
	
	public static Result result() {
		/*インデックスページ表示する*/
		//String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
        return ok(result.render(user));
    }
	
	public static Result record() {
		/*履歴ページ表示する*/
		String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
        return ok(record.render(title, user));
    }
}
