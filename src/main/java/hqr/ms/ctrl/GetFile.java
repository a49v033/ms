package hqr.ms.ctrl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hqr.ms.util.Brower;

@RestController
public class GetFile {
	@RequestMapping(value = "/getFile")
	public String listPath(@RequestParam(name = "fileId", required = false) String fileId, @RequestParam(name="accessToken", required = true)  String accessToken) {
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
	
		if(fileId==null||"".equals(fileId)) {
			return "Pls pass fileId";
		}
		
		String url = "https://graph.microsoft.com/v1.0/me/drive/items/"+fileId;
		
		System.out.println("FileId URL is "+url);
		HttpGet get = new HttpGet(url);
		get.setConfig(Brower.getRequestConfig());
		get.setHeader("Authorization", accessToken);
		
		try {
			CloseableHttpResponse cl = httpclient.execute(get, httpClientContext);
			String res = EntityUtils.toString(cl.getEntity());
			JSONObject jo = JSON.parseObject(res);
			httpclient.close();
			if(jo.getString("file")!=null) {
				return "<a href=\""+jo.getString("@microsoft.graph.downloadUrl")+"\">"+jo.getString("name")+"</a>";
			}
			else {
				return "Can't download folder";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
