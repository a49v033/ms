# msToken
This tool helps get the refresh token for any App created in portal.azure.com

Client Id = 9b354133-9271-4398-8797-c2c31ab6329f

Client Secret = 7TS9.CiIRLJj~6__8G4wG3Sy1cpctyV.Aa

Redirect Uri = https://107.173.149.207/getToken

Example Page: https://107.173.149.207

# Generate jks file by keytool
keytool -genkeypair -alias hqr -keyalg RSA -keypass 123456 -keystore hqr.jks -storepass 123456 -validity 3650


# 修改证书 2种方法，推荐第二种
1.证书默认放到java的cacerts证书里面

--Add cer file to java cacerts

keytool -import -keystore cacerts -storepass changeit -keypass changeit -alias ejbca -file /root/ejb.cer

--Remove cer file from java cacerts

keytool -delete -v -alias ejbca -keystore /app/jvm/jdk1.8.0_202/jre/lib/security/cacerts -storepass changeit -keypass changeit

 
2.生成自己的证书文件
--named the cert to ejbca and save it to ecombatch.keystore

keytool -import -trustcacerts -alias ejbca -file /root/ejb.cer -keystore ecombatch.keystore -storepass ecombatch

keytool -list -rfc -keystore ecombatch.keystore -storepass ecombatch 看keystore 的内容

keytool -delete -v -alias ejbca -keystore ecombatch.keystore -storepass ecombatch

如果call的url是域名而不是IP还需要配置host： 
10.170.184.112   loli.com

在code加载ejb 之前设置如下properties


System.setProperty("javax.net.ssl.trustStore", "KEYSTORE PATH");

System.setProperty("javax.net.ssl.trustStorePassword", "KEYSTORE PASSWORD");

System.setProperty("javax.net.ssl.keyStore", "KEYSTORE PATH");

System.setProperty("javax.net.ssl.keyStorePassword", "KEYSTORE PASSWORD");

