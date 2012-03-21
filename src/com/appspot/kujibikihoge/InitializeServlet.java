package com.appspot.kujibikihoge;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitializeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//例:http://kujibikihoge.appspot.com/initialize?num=15
		String num = req.getParameter("num");
		long number = Long.parseLong(num);

		System.out.println(number);

		//一回全部消す
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Kuji.class);
		//全部抜き出す
		List<Kuji> kujis = (List<Kuji>) query.execute();
		//んで削除
		for (Iterator i = kujis.iterator(); i.hasNext();) {
			pm.deletePersistent(i.next());
		}

		//0から人数-1までインデックス
		for(int i=0 ;i<number ;i++ ){
			Kuji kuji = new Kuji(String.valueOf(i),"");
			pm.makePersistent(kuji);
		}

		pm.close();
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println( number+ "個のくじを作成しました");
		return;

	}

}
