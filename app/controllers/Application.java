package controllers;

import java.util.*;

import play.db.ebean.Model.Finder;
import models.User;
import models.Data;
import play.api.*;
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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.plot.PlotOrientation;


import javax.swing.JFrame;

import java.awt.BorderLayout;

import org.jfree.chart.ChartPanel;

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
    
    public static Result index() {
		/*インデックスページ表示する*/
		String title = "Check System";
		//session("id","kamiya");
		String user = session("id");
		play.Logger.debug(user+": index");
        return ok(index.render(title, user, form(MyForm.class)));   
    }
    
    public static Result login() {
    	/*ログインページ表示する*/
    	//User user1 = new User("70110024","kamiya","kamiya");  
    	//play.Logger.debug("hogehoge");
    	return ok(login.render(form(Login.class)));
    }
    
    public static Result authenticate() {
    	//System.out.println( form(Login.class));
    	Form<Login> loginForm = form(Login.class).bindFromRequest();
    	//System.out.println(loginForm);
        if (loginForm.hasErrors()) {
        	return badRequest(login.render(loginForm));
        } else {
        	session().clear();
        	session("id", loginForm.get().id);
        	play.Logger.debug(loginForm.get().id+": login");
        	return redirect(routes.Application.index());
        }
    }
	
	public static Result sendform() {
		/*前作ったフォームを送るやつ*/
		String title = "Check System Send Form";
		String user = session("id");
		play.Logger.debug("sendform: "+user);
		Form<MyForm> form = form(MyForm.class).bindFromRequest();
		if (form.hasErrors()) {
			String msg = "入力に問題があります。";
			play.Logger.debug(user+": index");
			return badRequest(index.render(title, user, form));
		} else {
			MyForm data = form.get();
			String msg = data.toString();
			play.Logger.debug(user+": index");
			return ok(index.render(title, user, form));
		}
	}
	
	public static Result check() {
		/*チェックページ表示する*/
		String user = session("id");
		play.Logger.debug(user+": check");
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
	
	public static Result checker() {
		String user = session("id");
		play.Logger.debug("checker: "+user);
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart checkf = body.getFile("checkf");
		String kadai = body.asFormUrlEncoded().get("kadai")[0];
		int num = Integer.parseInt(kadai.substring(5));
		//System.out.println(num);
		//System.out.println(kadai);
		int rightnum = 0;
		int kaisu = 1;
		kaisu = Data.kaisuu(user+"_"+num+"_") + 1;
		String text ="";
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
					File savefile = new File("./public/program/"+user+"_"+num+"_"+kaisu+".txt");
					//File writefile = new File(user+"_"+num+"_"+kaisu+".java");
					try {
						FileReader filereader = new FileReader(getfile);
						FileWriter filewriter = new FileWriter(writefile);
						FileWriter savefilewriter = new FileWriter(savefile);
						int ch = filereader.read();
						while (ch != -1) {
							//System.out.print((char)ch);
							filewriter.write((char)ch);
							savefilewriter.write((char)ch);
							ch = filereader.read();
						}
						filereader.close();
						filewriter.close();
						savefilewriter.close();
					} catch (FileNotFoundException e) {
						System.out.println(e);
					} catch (IOException e) {
						System.out.println(e);
					}
					
					/*学習者プログラムをコンパイル*/
					ProcessBuilder compile = new ProcessBuilder("javac", fileName);
					//compile.redirectErrorStream(true);
					try {
						Process processc = compile.start();
						processc.waitFor();
						/*int ret = processc.exitValue();
						if (ret != 0) {
							System.out.println("uoooooooooooo");
							processc.destroy();
							return redirect(routes.Application.index());
						}*/
						//System.out.println("compile exited with value: " + ret);
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
				    		/*int c;
				    		while ((c = stdIn.read()) != -1) {
				    			System.out.print((char)c);
				    		}*/
				    		stdIn.close();
				    		/*while ((c = errIn.read()) != -1) {
				    			System.out.print((char)c);
				    		}*/
				    		errIn.close();
				    		//System.out.println();
				    		int ret = processe.waitFor();
				    		//System.out.println("execute exited with value: " + ret);
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
				    String[] testcase;
				    StringBuilder results = new StringBuilder();
				    File teachfile = new File(".");  
				    File[] teachFiles = teachfile.listFiles(getFileRegexFilter("teach" + num + "_", ".txt"));
				    File stufile = new File(".");  
				    File[] stuFiles = stufile.listFiles(getFileRegexFilter("stu" + num + "_", ".txt"));
				    stringArray = new String[teachFiles.length];
				    feedbacks = new String[teachFiles.length];
				    testcase = new String[testFiles.length];
				    for (int i = 0; i < teachFiles.length; i++) {
				    	BufferedReader br1 = null;
				    	BufferedReader br2 = null;
				    	//System.out.println("ファイル" + (i+1) + "→" + teachFiles[i]);
				    	//System.out.println("ファイル" + (i+1) + "→" + stuFiles[i]);
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
							System.out.println("合格だよ");
						} else {
							System.out.println("不合格だよ");
						}*/
				    		StringTokenizer st1 = new StringTokenizer(teacher);
				    		int index = 0;	
				    		while (st1.hasMoreTokens()) {
				    			String token = st1.nextToken();
				    			if (student.indexOf(token,index) != -1 && student.indexOf(token,index) >= index) {
				    				index = student.indexOf(token) + 1;
				    				if (!st1.hasMoreTokens()) {
				    					//System.out.println("合格です");
				    					//stringArray[i] = "合格です";
				    					stringArray[i] = "feed"+num+"_"+(i+1)+".txt";
				    					testcase[i] = "○";
				    					results.append("○");
				    					rightnum++;
				    					//データベース保存
				    				}
				    			} else {
				    				//System.out.println("不合格です");
				    				stringArray[i] = "feed"+num+"_"+(i+1)+"_f.txt";
				    				testcase[i] = "×";
				    				results.append("×");
				    				//stringArray[i] = "不合格です";
				    				BufferedReader feedbr = null;
				    				/*try {
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
							    		stringArray[i] = "不合格です：" +"\r\n" + feedback;
							    		System.out.println(stringArray[i]);
				    				} catch (FileNotFoundException e) {
				    					System.out.println(e);
				    				} catch (IOException e) {
				    					System.out.println(e);
				    				}*/
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
				    /*for (int n = 0; n < stringArray.length; n++) {   
				    	System.out.println(stringArray[n]);
				    }*/				    
				    /*データベース登録*/
				    //kaisu = Data.kaisuu(user+"_"+num+"_") + 1;
				    String resultset = new String(results);
				    Data.register(user+"_"+num+"_"+kaisu, rightnum, resultset);

				    int seikaisu = 0;
				    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
				    DefaultCategoryDataset data = new DefaultCategoryDataset();
				    for (int i = 1; i <= kaisu; i++) {
				    	seikaisu = Data.rightn(user+"_"+num+"_"+i);
				    	//data.setValue(seikaisu, user, kaisu+"回目");
				    	data.addValue(seikaisu, user, i+"");
				    }
				    /*data.setValue(3, "神谷", "1回目");
				    data.setValue(2, "神谷", "2回目");
				    data.setValue(6, "神谷", "3回目");*/
				    JFreeChart chart = ChartFactory.createLineChart("課題"+num, "回数", "合格数", data, PlotOrientation.VERTICAL, true, false, false);
				    
				    CategoryPlot plot = chart.getCategoryPlot();
				    NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
				    numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				    
				    LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
				    renderer.setSeriesShapesVisible(0, true);

				    File rireki = new File("./public/images/"+user+"_"+num+".png");
				    try {
				    	ChartUtilities.saveChartAsPNG(rireki, chart, 400, 300);
				    } catch (IOException e) {
				    	e.printStackTrace();
				    }
					//return redirect(routes.Application.result());
				    text = user+"_"+num+"_"+kaisu+".txt";
				    play.Logger.debug(user+" :result-"+num+"-"+kaisu);
					return ok(result.render(user,num,kaisu,stringArray,text));
				}
				flash("error", "ファイルない");
				play.Logger.debug(user+" :index error ( "+num+" )");
				return redirect(routes.Application.index());
			}
			flash("error", "ファイルない");
			play.Logger.debug(user+" :index error ( "+num+" )");
			return redirect(routes.Application.index());
		}
		flash("error", "ファイルない");
		play.Logger.debug(user+" :index error ( "+num+" )");
		return redirect(routes.Application.index());    
	}
	
	public static Result result() {
		/*フィードバックページ表示する*/
		//String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
		int kaisu = 0;
		int num = 0;
		String[] stringArray;
		//String[] feedbacks;
		stringArray = new String[5];
		String text = "";
		//feedbacks = new String[5];
		play.Logger.debug(user+" :result");
        return ok(result.render(user,num,kaisu,stringArray,text));
    }
	
	public static Result record() {
		/*履歴ページ表示する*/
		String title = "Check System";
		//session("id","kamiya");
		String user = session("id");
		//File recordfile = new File(".");
		//File recordfile = new File("C:\\Users\\cs11024\\Documents\\checker\\play-2.2.4\\kamiya\\public\\images");
		File recordfile = new File("./public/images");
		File[] recordFiles = recordfile.listFiles(getFileRegexFilter(user+"_", ".png"));
		int size = recordFiles.length;
		String[] filesName;
		filesName = new String[size];
		for (int i = 0; i < size; i++) {
			filesName[i] = recordFiles[i].getName();
		}
		
		List<Data> results = new ArrayList<Data>();
		String testcase2[][];
		testcase2 = new String[size][];
		for (int i = 0; i < size; i++) {
			results = Data.kekka(user+"_"+(i+1));
			testcase2[i] = new String[results.size()];
			for (int j = 0; j < results.size(); j++) {
				testcase2[i][j] = results.get(j).testcase;
			}
		}
		
		/*results = Data.kekka(user+"_");
		System.out.println(results.get(0).testcase);
		String testcase[][];
		testcase = new String[results.size()][results.size()];
		int num = 1;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).id.startsWith(user+"_"+num)) {
				testcase[num-1][i] = results.get(i).testcase;
			} else {
				num++;
			}
		}*/
		
		/*for (int i = 0; i < size; i++) {
			System.out.println("配列"+i);
			for (int j = 0; j < testcase2[i].length; j++) {
				System.out.println(testcase2[i][j]);
			}
		}*/
		play.Logger.debug(user+" :record");
        return ok(record.render(title, user, size, filesName, testcase2));
    }
	
	public static Result program(int num, int kaisu) {
		/*履歴から○回目のページ表示する*/
		//String title = "Kamiya System";
		//session("id","kamiya");
		String user = session("id");
		String rireki = Data.rireki(user+"_"+num+"_"+kaisu);
		String[] stringArray;
		stringArray = new String[rireki.length()];
		String text = null;
		for (int i = 0; i < rireki.length(); i++) {
			char t = rireki.charAt(i);
			//System.out.println(t);
			//System.out.println((int)t);
			if ((int)t == 9675) {
				stringArray[i] = "feed"+num+"_"+(i+1)+".txt";
			} else {
				stringArray[i] = "feed"+num+"_"+(i+1)+"_f.txt";
			}
			/*if ((int)t == 9675) {
				stringArray[i] = "合格です";
				//System.out.println(t);
			} else {
				BufferedReader feedbr = null;
				try {
					File feedfile = new File("feed"+num+"_"+(i+1)+".txt");
					feedbr = new BufferedReader(new FileReader(feedfile));
					StringBuffer feedsb = new StringBuffer();
		    		int feed;
		    		while ((feed = feedbr.read()) != -1) {
		    			feedsb.append((char) feed);
		    		}
		    		String feedback = feedsb.toString();				    		
		    		feedbr.close();
		    		String crlf = System.getProperty("line.separator");
		    		//System.lineSeparator();
		    		stringArray[i] = "不合格です：" +"\r\n" + feedback;
				} catch (FileNotFoundException e) {
					System.out.println(e);
				} catch (IOException e) {
					System.out.println(e);
				}
			}*/
		}
		text = user+"_"+num+"_"+kaisu+".txt";
		//int kaisuu = Integer.parseInt(kaisu);
		play.Logger.debug(user+" :program-"+num+"-"+kaisu);
        return ok(program.render(user,num,kaisu,stringArray,text));
    }
}
