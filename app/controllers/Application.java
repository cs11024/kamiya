package controllers;

import java.util.*;

import play.db.ebean.Model.Finder;
import models.User;
import models.Data;
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

/*import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import org.jfree.chart.ChartPanel;*/

public class Application extends Controller {
    
    public static class Login {
    	/*ログインフォーム*/
    	/*ログインフォーム*/
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
	
	public static FilenameFilter getFileRegexFilter(String start, String end) {  
		final String start_ = start;
		final String end_ = end;
		return new FilenameFilter() {  
			public boolean accept(File file, String name) {  
				boolean ret = name.startsWith(start_) && name.endsWith(end_);   
				return ret;  
			}  
		};  
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
		int rightnum = 0;
		int kaisu = 1;
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
					//while () {
					File testfile = new File(".");  
				    File[] testFiles = testfile.listFiles(getFileRegexFilter("test" + num + "_", ".txt"));
				    for (int i = 0; i < testFiles.length; i++) {
				    	//System.out.println("ファイル" + (in+1) + "→" + testFiles[in]);
				    	ProcessBuilder exec = new ProcessBuilder("cmd", "/c", "java", fname, "<", testFiles[i].getName(), ">", "stu"+ num +"_"+ (i+1) +".txt");
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
				    }
					
					/*正解結果と学習者プログラムを比較*/
				    String[] stringArray;
				    String[] feedbacks;
				    File teachfile = new File(".");  
				    File[] teachFiles = teachfile.listFiles(getFileRegexFilter("teach" + num + "_", ".txt"));
				    File stufile = new File(".");  
				    File[] stuFiles = stufile.listFiles(getFileRegexFilter("stu" + num + "_", ".txt"));
				    stringArray = new String[teachFiles.length];
				    feedbacks = new String[teachFiles.length];
				    for (int i = 0; i < teachFiles.length; i++) {   
				    	BufferedReader br1 = null;
				    	BufferedReader br2 = null;
				    	System.out.println("ファイル" + (i+1) + "→" + teachFiles[i]);
				    	System.out.println("ファイル" + (i+1) + "→" + stuFiles[i]);
				    	try {
				    		br1 = new BufferedReader(new InputStreamReader(new FileInputStream(teachFiles[i].getName())));
				    		br2 = new BufferedReader(new InputStreamReader(new FileInputStream(stuFiles[i].getName())));
				    		StringBuffer sb1 = new StringBuffer();
				    		StringBuffer sb2 = new StringBuffer();
				    		int c1, c2;
				    		while ((c1 = br1.read()) != -1) {
				    			sb1.append((char) c1);
				    		}
				    		while ((c2 = br2.read()) != -1) {
				    			sb2.append((char) c2);
				    		}
				    		String teacher = sb1.toString();
				    		String student = sb2.toString();
				    		br1.close();
				    		br2.close();
						/*if (seikai.equals(kekka)) {
							System.out.println("正解だよ");
						} else {
							System.out.println("だめだよ");
						}*/
				    		StringTokenizer st1 = new StringTokenizer(teacher);
				    		int index = 0;	
				    		while (st1.hasMoreTokens()) {
				    			String token = st1.nextToken();
				    			if (student.indexOf(token,index) != -1 && student.indexOf(token,index) >= index) {
				    				index = student.indexOf(token) + 1;
				    				if (!st1.hasMoreTokens()) {
				    					System.out.println("正解です");
				    					stringArray[i] = "正解です";
				    					rightnum++;
				    					//データベース保存
				    				}
				    			} else {
				    				System.out.println("不正解です");
				    				//stringArray[i] = "不正解です";
				    				BufferedReader feedbr = null;
				    				try {
				    					File feedfile = new File("feed" + num + "_" + (i + 1) +".txt");
				    					feedbr = new BufferedReader(new FileReader(feedfile));
				    					StringBuffer feedsb = new StringBuffer();
							    		int feed;
							    		while ((feed = feedbr.read()) != -1) {
							    			feedsb.append((char) feed);
							    		}
							    		String feedback = feedsb.toString();				    		
							    		feedbr.close();
							    		System.out.println(feedback);
							    		String crlf = System.getProperty("line.separator");
							    		stringArray[i] = "不正解です：" +"\r\n" + feedback;
							    		System.out.println(stringArray[i]);
				    					/*byte[] feedfilesBytes = Files.readAllBytes(Paths.get("feed" + num + "_" + (i + 1) +".txt"));
				    					String feedfile = new String(feedfilesBytes, StandardCharsets.UTF_8);
				    					System.out.println(feedfile);*/
				    				} catch (FileNotFoundException e) {
				    					System.out.println(e);
				    				} catch (IOException e) {
				    					System.out.println(e);
				    				}
				    				//データベース保存
				    			}
				    		}
				    	} catch (FileNotFoundException e) {
				    		System.out.println(e);
				    	} catch (IOException e) {
				    		System.out.println(e);
				    	} catch (Exception e) {
				    		System.out.println(e);
				    	}
				    }
				    for (int n = 0; n < stringArray.length; n++) {   
				    	System.out.println(stringArray[n]);
				    }
				    
				    /*Finder<String, Data> finder = new Finder<String, Data>(String.class,
			                Data.class);
			        List<Data> datas = finder.all();*/
				    
				    /*データベース登録*/
				    kaisu = Data.kaisuu(user+"_"+num+"_") + 1;
				    Data.register(user+"_"+num+"_"+kaisu, rightnum);
				    
				    
				    /*DefaultCategoryDataset data = new DefaultCategoryDataset();
				    data.setValue(3, "神谷", "1回目");
				    data.setValue(2, "神谷", "2回目");
				    data.setValue(6, "神谷", "3回目");
				    JFreeChart chart = ChartFactory.createLineChart("課題1", "回数", "正解数", data, PlotOrientation.VERTICAL, true, false, false);
				    File rireki = new File("./test1_1.jpeg");
				    try {
				    	ChartUtilities.saveChartAsJPEG(rireki, chart, 400, 300);
				    } catch (IOException e) {
				    	e.printStackTrace();
				    }*/
				    
					//return redirect(routes.Application.result());
					return ok(result.render(user,kaisu,stringArray,feedbacks));
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
		int kaisu = 0;
		String[] stringArray;
		String[] feedbacks;
		stringArray = new String[5];
		feedbacks = new String[5];
        return ok(result.render(user,kaisu,stringArray,feedbacks));
    }
	
	public static Result record() {
		/*履歴ページ表示する*/
		String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
        return ok(record.render(title, user));
    }
}
