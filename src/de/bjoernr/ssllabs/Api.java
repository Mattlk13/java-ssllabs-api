package de.bjoernr.ssllabs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Api
{
	private static final String API_URL = "https://api.ssllabs.com/api/v2";
	
	public JSONObject fetchApiInfo()
	{
		String jsonString;
		JSONObject json = new JSONObject();
		
		try 
		{
			jsonString = sendApiRequest("info", null);
			json = new JSONObject(jsonString);
		}
		catch (Exception ignored){}

		return (json);
	}
	
	public JSONObject fetchHostInformation(String host, boolean publish, boolean startNew, boolean fromCache, String maxAge, String all, boolean ignoreMismatch)
	{
		String jsonString;
		JSONObject json = new JSONObject();
		
		try
		{
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("host", host);
			parameters.put("publish", booleanToOnOffString(publish));
			parameters.put("startNew", booleanToOnOffString(startNew));
			parameters.put("fromCache", booleanToOnOffString(fromCache));
			parameters.put("maxAge", maxAge);
			parameters.put("all", all);
			parameters.put("ignoreMismatch", booleanToOnOffString(ignoreMismatch));
			
			jsonString = sendApiRequest("analyze", parameters);
			json = new JSONObject(jsonString);
		}
		catch (Exception ignored){}
		
		return (json);
	}
	
	public JSONObject fetchHostInformationCached(String host, String maxAge, boolean publish, boolean ignoreMismatch)
	{
		return (fetchHostInformation(host, publish, false, true, maxAge, "done", ignoreMismatch));
	}
	
	public JSONObject fetchEndpointData(String host, String s, boolean fromCache)
	{
		String jsonString;
		JSONObject json = new JSONObject();
		
		try
		{
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("host", host);
			parameters.put("s", s);
			parameters.put("fromCache", booleanToOnOffString(fromCache));
			
			jsonString = sendApiRequest("getEndpointData", parameters);
			json = new JSONObject(jsonString);
		}
		catch (Exception ignored){}
		
		return (json);
	}
	
	public JSONObject fetchStatusCodes()
	{
		String jsonString;
		JSONObject json = new JSONObject();
		
		try 
		{
			jsonString = sendApiRequest("getStatusCodes", null);
			json = new JSONObject(jsonString);
		}
		catch (Exception ignored){}

		return (json);
	}
	
	public String sendCustomApiRequest(String apiCall, Map<String, String> parameters)
	{
		String jsonString = "";
		
		try
		{
			jsonString = sendApiRequest(apiCall, parameters);
		}
		catch(Exception ignored){}
		
		return (jsonString);
	}
	
	private String sendApiRequest(String apiCall, Map<String, String> parameters) throws IOException
	{
		URL url = new URL(API_URL + "/" + apiCall);
		
		if(parameters != null)
		{
			url = new URL(url.toString() + buildGetParameterString(parameters));
		}
		
		InputStream is = url.openStream();
		int nextByteOfData = 0;
		
		StringBuffer apiResponseBuffer = new StringBuffer();
		
		while((nextByteOfData = is.read()) != -1)
		{
			apiResponseBuffer.append((char) nextByteOfData);
		}
					
		is.close();

		return (apiResponseBuffer.toString());
	}
	
	private String buildGetParameterString(Map<String, String> parameters)
	{
		String getParameterString = "";
		
		for(Map.Entry<String, String> param : parameters.entrySet())
		{
			if(param.getValue() == null)
			{
				continue;
			}
			
			getParameterString += (getParameterString.length() < 1) ? ("?") : ("&");
					
			getParameterString += param.getKey() + "=" + param.getValue();
		}
		
		return (getParameterString);
	}
	
	private String booleanToOnOffString(boolean b)
	{
		return (b == true) ? "on" : "off";
	}
	
	public String getApiUrl()
	{
		return API_URL;
	}
}