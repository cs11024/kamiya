package controllers;

import java.util.List;
import play.db.ebean.Model.Finder;
import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import play.data.validation.Constraints.*;

public class Application extends Controller {

    public static class MyForm {
    	public String id;
    	public String number;
    	public String filename;
    	
    	public String toString() {
    		return "ID:" + id + ", Number:" + number + ", FileName:" + filename;
    	}
    }
    
    public static class Login {
    	public String id;
    	public String password;
    	public String validate() {
    		if (User.authenticate(id, password) == null) {
    			return "Invalid id or password";
    		}
    		return null;
    	}
    }
    
    public static Result login() {
    	return ok(
    			login.render(form(Login.class))
    	);
    }
    
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
        	return badRequest(login.render(loginForm));
        } else {
        	session().clear();
        	session("id", loginForm.get().id);
        	return redirect(routes.Application.index());
        }
    }
    
	public static Result index() {
		String title = "Kamiya System";
		String msg = "ようこそ！";
        return ok(index.render(title, msg, form(MyForm.class)));
        
    }
	
	public static Result sendform() {
		String title = "Kamiya System Send Form";
		Form<MyForm> form = form(MyForm.class).bindFromRequest();
		if (form.hasErrors()) {
			String msg = "入力に問題があります。";
			return badRequest(index.render(title, msg, form));
		} else {
			MyForm data = form.get();
			String msg = data.toString();
			return ok(index.render(title, msg, form));
		}
	}

}
