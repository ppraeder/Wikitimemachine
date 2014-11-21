package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import entity.Category;
import entity.Page;

public class ApiCaller {

	private final String USER_AGENT = "Mozilla/5.0";
	private String apcontinue = "";

	public void start(String startUrl) throws Exception {
		String startString = sendGet(startUrl);
		JSONObject startJson = getJSON(startString);
		getApContinue(startJson);
		do {
			List<Page> list = getPageInfo(startJson);

			for (Page page : list) {
				getCategories(page);
			}

			startString = sendGet(startUrl + getEncoded(apcontinue));
			startJson = getJSON(startString);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
			String uhrzeit = sdf.format(new Date());
			System.out.println(uhrzeit);
		} while (getApContinue(startJson));

	}

	private String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();

	}

	private boolean getApContinue(JSONObject json) throws JSONException {
		if (!json.has("query-continue")) {
			apcontinue = "undefined";
			return false;
		}
		apcontinue = getSubJSON(json, "query-continue").getJSONObject(
				"allpages").getString("apcontinue");

		return true;
	}

	private List<Category> getCategories(Page p) throws Exception {
		String query = "http://de.wikipedia.org/w/api.php?format=json&action=query&prop=categories&cllimit=500&pageids=";
		String result = sendGet(query + p.getPageid());
		JSONObject json = getJSON(result);
		JSONArray jsonArray = getSubJSON(
				getSubJSON(getSubJSON(json, "query"), "pages"),
				String.valueOf(p.getPageid())).getJSONArray("categories");
		List<Category> returnList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			Gson gson = new Gson();
			Category c = gson.fromJson(jsonArray.getJSONObject(i).toString(),
					Category.class);

			returnList.add(c);
		}
		return returnList;

	}

	private List<Page> getPageInfo(JSONObject json) throws JSONException {
		if (!json.has("query")) {
			return null;
		}
		JSONArray jsonArray = getSubJSON(json, "query")
				.getJSONArray("allpages");

		List<Page> returnList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			Gson gson = new Gson();
			Page p = gson.fromJson(jsonArray.getJSONObject(i).toString(),
					Page.class);

			returnList.add(p);
		}

		return returnList;
	}

	private JSONObject getJSON(String input) throws JSONException {
		return new JSONObject(input);
	}

	private JSONObject getSubJSON(JSONObject json, String subObject)
			throws JSONException {
		return json.getJSONObject(subObject);
	}

	private String getEncoded(String input) throws UnsupportedEncodingException {
		return URLEncoder.encode(input, "UTF-8");
	}

}
