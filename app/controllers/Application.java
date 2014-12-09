package controllers;

import java.util.List;
import java.util.Map;
import java.util.*;
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
		int num = Integer.parseInt(kadai.substring(5));
		System.out.println(num);
		System.out.println(kadai);
		/*try {
			System.out.println(Runtime.getRuntime().exec("dir").getOutputStream().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (checkf != null) {
			String fileName = checkf.getFilename();
			int point = fileName.lastIndexOf(".");
			if (point != -1) {
				String fname = fileName.substring(0, point);
				String extension = fileName.substring(point + 1);
				if (extension.equals("java")) {
					/*学習者プログラムをローカルに保存*/
					File getfile = checkf.getFile();
					File writefile = new File(fileName);
					try {
						FileReader filereader = new FileReader(getfile);
						FileWriter filewriter = new FileWriter(writefile);
						int ch = filereader.read();
						while (ch != -1) {
							System.out.print((char)ch);
							filewriter.write((char)ch);
							ch = filereader.read();
						}
						filereader.close();
						filewriter.close();
					} catch (FileNotFoundException e) {
						System.out.println(e);
					} catch (IOException e) {
						System.out.println(e);
					}
					
					/*学習者プログラムをコンパイル*/
					ProcessBuilder compile = new ProcessBuilder("javac", fileName);
					try {
						Process processc = compile.start();
						int ret = processc.waitFor();
						System.out.println("compile exited with value: " + ret);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
						//System.out.println(Runtime.getRuntime().exec("javac " + fileName).getOutputStream().toString());
						/*Runtime.getRuntime().exec("javac " + fileName);
						Runtime.getRuntime().exec("java " + fname);*/
						//Process pro = Runtime.getRuntime().exec("java " + fname);
						//System.out.println(pro.getInputStream());
						//System.out.println(Runtime.getRuntime().exec("java " + fname).getOutputStream().toString());
						//ProcessBuilder builder = new ProcessBuilder("java", "-version");
					
					/*学習者プログラムをリダイレクション実行*/
					int i = 1;
					//while () {
					ProcessBuilder exec = new ProcessBuilder("cmd", "/c", "java", fname, "<", "test.txt", ">", "stu"+ num +"_"+ i +".txt");
					try {
			            Process processe = exec.start();
			            InputStream stdIn = processe.getInputStream();
			            InputStream errIn = processe.getErrorStream();
			            int c;
			            while ((c = stdIn.read()) != -1) {
			            	System.out.print((char)c);
			            }
			            stdIn.close();
			            while ((c = errIn.read()) != -1) {
			            	System.out.print((char)c);
			            }
			            errIn.close();
			            System.out.println();
			            int ret = processe.waitFor();
			            System.out.println("execute exited with value: " + ret);
			            /*InputStream stream = process.getErrorStream();
			            while (true) {
			                int c = stream.read();
			                if (c == -1) {
			                    stream.close();
			                    break;
			                }
			                System.out.print((char)c);
			            }*/
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//i++;
					//}
					
					/*正解結果と学習者プログラムを比較*/
					BufferedReader br1 = null;
					BufferedReader br2 = null;
					try {
						br1 = new BufferedReader(new InputStreamReader(new FileInputStream("seikai.txt")));
						br2 = new BufferedReader(new InputStreamReader(new FileInputStream("stu1_1.txt")));
						StringBuffer sb1 = new StringBuffer();
						StringBuffer sb2 = new StringBuffer();
						int c1, c2;
						while ((c1 = br1.read()) != -1) {
							sb1.append((char) c1);
						}
						while ((c2 = br2.read()) != -1) {
							sb2.append((char) c2);
						}
						String seikai = sb1.toString();
						String kekka = sb2.toString();
						System.out.println(seikai);
						System.out.println(kekka);
						
						String line = br2.readLine();
						StringTokenizer st1 = new StringTokenizer(seikai);
						int index = 0;
						while (st1.hasMoreTokens()) {
							String token = st1.nextToken();
							if (line.indexOf(token) != -1) {
								index = kekka.indexOf(token);
								if (!st1.hasMoreTokens()) {
									System.out.println("正解だよ");
									/*データベース保存*/
								}
							} else {
								System.out.println("だめだよ");
								/*データベース保存*/
							}
						}
						/*StringTokenizer st1 = new StringTokenizer(seikai);
						int index = 0;
						while (st1.hasMoreTokens()) {
							String token = st1.nextToken();
							if (kekka.indexOf(token) != -1 && kekka.indexOf(token) > index) {
								index = kekka.indexOf(token);
								if (!st1.hasMoreTokens()) {
									System.out.println("正解だよ");
									//データベース保存
								}
							} else {
								System.out.println("だめだよ");
								//データベース保存
							}
						}*/
						br1.close();
						br2.close();
					} catch (FileNotFoundException e) {
						System.out.println(e);
					} catch (IOException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					
					return redirect(routes.Application.result());
				}
			}
		}
		flash("error", "ファイルない");
		return redirect(routes.Application.index());    
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
