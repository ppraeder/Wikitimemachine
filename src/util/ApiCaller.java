package util;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.Category;
import entity.Page;

public class ApiCaller {

	private Connection c;
	private PreparedStatement stmt;
	private FileWriter fw;

	public ApiCaller() {

	}

	public void start(String startUrl) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		String uhrzeit = sdf.format(new Date());
		System.out.println(uhrzeit);
		// String startString = sendGet(startUrl);
		// JSONObject startJson = getJSON(startString);
		// getApContinue(startJson);
		// String line = "";
		// // fw = new FileWriter("C:/Users/Peter/Desktop/people.csv");
		// do {
		// // List<Page> list = getPageInfo(startJson);
		// //
		// // for (Page page : list) {
		// // // getCategories(page);
		// // // Pr�fe ob kategorie zu Person geh�rt
		// // line = page.getPageid() + ";" + page.getTitle() + ";"
		// // + page.getNs();
		// // fw.write(line + "\n");
		// // fw.flush();
		// //
		// // }
		//
		// startString = sendGet(startUrl + getEncoded(apcontinue));
		// startJson = getJSON(startString);
		//
		// } while (getApContinue(startJson));
		//
		// sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		// uhrzeit = sdf.format(new Date());
		// System.out.println(uhrzeit);
		// System.out.println(apContinueList.size());
		// fw = new FileWriter("C:/Users/Peter/Desktop/apcontinue.csv");
		// for (String string : apContinueList) {
		// fw.write(string + "\n");
		// fw.flush();
		// }
		CategoryApi ca = new CategoryApi();
		Category c = new Category();
		c.setTitle("Mann");
		// String line = "";
		// fw = new FileWriter("C:/Users/Peter/Desktop/people_mann.csv");
		// do {
		@SuppressWarnings("unused")
		List<Page> list = ca.getCategoryMembers(c);

		// for (Page page : list) {
		//
		// line = page.getPageid() + ";" + page.getTitle() + ";"
		// + page.getNs();
		// fw.write(line + "\n");
		// fw.flush();
		//
		// }
		//
		// } while (ca.getCmContinue());
		sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		uhrzeit = sdf.format(new Date());
		System.out.println(uhrzeit);

	}

}