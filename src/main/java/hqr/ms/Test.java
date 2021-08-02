package hqr.ms;

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
	
	private static String SEED = "abcdefghijklmnopqrstuvwxyz";
	
	private CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
	private HttpClientContext httpClientContext = Brower.getHttpClientContext();
	
	public static void main(String[] args) {
		Test tt = new Test();
		tt.checkDomainAvailability();
		
	}
	
	public String getToken() {
		String token = "";
		String json = "grant_type=password&resource=74658136-14ec-4630-ad9b-26e160ff0fc6&client_id=1950a258-227b-4e31-a9cf-717495945fc2&username=cp000@0cc.onmicrosoft.com&password=Mjj@1234";
		
		HttpPost post = new HttpPost("https://login.microsoftonline.com/common/oauth2/token");
		post.setEntity(new StringEntity(json, ContentType.APPLICATION_FORM_URLENCODED));
		try(CloseableHttpResponse rl = httpclient.execute(post, httpClientContext);){
			if(rl.getStatusLine().getStatusCode()==200) {
				JSONObject jo =JSON.parseObject(EntityUtils.toString(rl.getEntity()));
				token = jo.getString("access_token");
			}
			else {
				System.out.println("fail to get token, status code not correct");
			}
		} catch (Exception e) {
			System.out.println("fail to get token:"+e);
		}
		return token;
	}
	
	public void checkDomainAvailability() {
		for(int i=0;i<26;i++) {
			String token = getToken();
			if("".equals(token)) {
				System.out.println("stop it as token is invalid");
				break;
			}
			char c1 = SEED.charAt(i);
			for(int j=0;j<26;j++) {
				char c2 = SEED.charAt(j);
				for(int k=0;k<26;k++) {
					char c3 = SEED.charAt(k);
					String dirNm = String.valueOf(new char[] {c1,c2,c3});
					HttpGet get = new HttpGet("https://main.iam.ad.ext.azure.com/api/Directories/DomainAvailability/"+dirNm);
					get.addHeader("Authorization", "Bearer "+token);
					get.addHeader("x-ms-client-request-id", UUID.randomUUID().toString());
					get.addHeader("x-ms-effective-locale", "zh-hans.zh-cn");
					
					try(CloseableHttpResponse rl2 = httpclient.execute(get, httpClientContext);) {
						System.out.println(dirNm+":"+EntityUtils.toString(rl2.getEntity()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
