package com.appspot.kujibikihoge;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class KujibikiServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/index.html");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {

		String name = req.getParameter("name");
		if(name == null || "".equals(name)){
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().println("名前を入力してください");
			return;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Kuji.class);
		List<Kuji> kujis = (List<Kuji>) query.execute();
		if(kujis.isEmpty()){
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().println("くじができてません。管理者に言ってください。");
			pm.close();
			return;
		}
		//すでに"4"を引いている人がいたら終わり
		Kuji kuji = pm.getObjectById(Kuji.class, "4");
		if(!("".equals(kuji.getOwner()))){
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().println(kuji.getOwner()+"さんが既にあたりを引いたので終了です");
			pm.close();
			return;
		}
		//まだあたりは引かれていない
		int limit = kujis.size();
		//limitまでで乱数発生
		Random rnd = new Random();
		while(true){
			int ran = rnd.nextInt(limit);
			if(ran==4){
				//"4"引いちゃった
				kuji = pm.getObjectById(Kuji.class, "4");
				kuji.setOwner(name);
				pm.makePersistent(kuji);

				//ここに庄田にメール通知する処理入れたい
				//なんかパーミッションが必要だった気がする

				RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/atari.html");
				rd.forward(req, resp);
				pm.close();
				return;
			}else{
				kuji = pm.getObjectById(Kuji.class, String.valueOf(ran));
				if("".equals(kuji.getOwner())){
					kuji.setOwner(name);
					pm.makePersistent(kuji);
					RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/WEB-INF/hazure.html");
					rd.forward(req, resp);
					pm.close();
					return;
				}else{
					//Randomクラスで乱数生成してるので来ないと思うけど
					continue;
				}
			}

		}//while
	}
}