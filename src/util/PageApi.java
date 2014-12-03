package util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import entity.Page;

public class PageApi {

	private String apcontinue = "";
	private List<String> apContinueList;

	public List<Page> getPageInfo(JSONObject json) throws JSONException {
		if (!json.has("query")) {
			return null;
		}
		JSONArray jsonArray = CommonFunctions.getSubJSON(json, "query")
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

	public List<Page> getPageInfoFromCategoryList(JSONObject json)
			throws JSONException {
		if (!json.has("query")) {
			return null;
		}
		JSONArray jsonArray = CommonFunctions.getSubJSON(json, "query")
				.getJSONArray("categorymembers");

		List<Page> returnList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			Gson gson = new Gson();
			Page p = gson.fromJson(jsonArray.getJSONObject(i).toString(),
					Page.class);

			returnList.add(p);
		}

		return returnList;
	}

	public List<Page> getPageInfoFromLinkList(JSONObject json)
			throws JSONException {
		if (!json.has("query")) {
			return null;
		}
		Gson gso = new Gson();
		
		JSONObject j = CommonFunctions.getSubJSON(json, "query");
		JSONArray jsonArray = j.getJSONArray("pages");

		List<Page> returnList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			Gson gson = new Gson();
			Page p = gson.fromJson(jsonArray.getJSONObject(i).toString(),
					Page.class);

			returnList.add(p);
		}

		return returnList;
	}

	public boolean getApContinue(JSONObject json) throws JSONException {
		if (!json.has("query-continue")) {
			apcontinue = "undefined";
			return false;
		}
		apcontinue = CommonFunctions.getSubJSON(json, "query-continue")
				.getJSONObject("allpages").getString("apcontinue");
		apContinueList.add(apcontinue);

		return true;
	}
}
