package hqr.ms;

import java.net.URI;
import java.util.UUID;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hqr.ms.util.Brower;

public class Test {
	
	private static String SEED = "1234567890abcdefghijklmnopqrstuvwxyz";
	
	public static void main(String[] args) {

		try(CloseableHttpClient httpclient = Brower.getCloseableHttpClient();) {
			HttpClientContext httpClientContext = Brower.getHttpClientContext();
			
			String json = "grant_type=password&resource=74658136-14ec-4630-ad9b-26e160ff0fc6&client_id=1950a258-227b-4e31-a9cf-717495945fc2&username=cp000@0cc.onmicrosoft.com&password=Mjj@1234";
			
			HttpPost post = new HttpPost("https://login.microsoftonline.com/common/oauth2/token");
			//post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/531.36 (KHTML, like Gecko) Chrome/87.0.3472.124 Safari/531.36");
			//post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			
			post.setEntity(new StringEntity(json, ContentType.APPLICATION_FORM_URLENCODED));

			CloseableHttpResponse rl = httpclient.execute(post, httpClientContext);

			if(rl.getStatusLine().getStatusCode()==200) {
				JSONObject jo =JSON.parseObject(EntityUtils.toString(rl.getEntity()));
				String at = jo.getString("access_token");
				
				HttpGet get = new HttpGet();
				for(int i=0;i<36;i++) {
					char c1 = SEED.charAt(i);
					for(int j=0;j<36;j++) {
						char c2 = SEED.charAt(j);
						String dirNm = String.valueOf(new char[] {c1,c2});
						get.setURI(URI.create("https://main.iam.ad.ext.azure.com/api/Directories/DomainAvailability/"+dirNm));
						
						get.addHeader("Authorization", "Bearer "+at);
						get.addHeader("x-ms-client-request-id", UUID.randomUUID().toString());
						get.addHeader("x-ms-effective-locale", "zh-hans.zh-cn");
						
						CloseableHttpResponse rl2 = httpclient.execute(get, httpClientContext);
						System.out.println(EntityUtils.toString(rl2.getEntity()));
					}
				}
			}
			else {
				System.out.println("fail to get token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
