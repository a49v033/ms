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

```base
#Add cer file to java cacerts
keytool -import -keystore cacerts -storepass changeit -keypass changeit -alias ejbca -file /root/ejb.cer

#Remove cer file from java cacerts
keytool -delete -v -alias ejbca -keystore /app/jvm/jdk1.8.0_202/jre/lib/security/cacerts -storepass changeit -keypass changeit
```
 
2.生成自己的证书文件

```base
#named the cert to ejbca and save it to ecombatch.keystore
keytool -import -trustcacerts -alias ejbca -file /root/ejb.cer -keystore ecombatch.keystore -storepass ecombatch

keytool -list -rfc -keystore ecombatch.keystore -storepass ecombatch 看keystore 的内容

keytool -delete -v -alias ejbca -keystore ecombatch.keystore -storepass ecombatch
```

如果call的url是域名而不是IP还需要配置host： 
```base
10.170.184.112   loli.com
```

在code加载ejb 之前设置如下properties

```base
System.setProperty("javax.net.ssl.trustStore", "KEYSTORE PATH");
System.setProperty("javax.net.ssl.trustStorePassword", "KEYSTORE PASSWORD");
System.setProperty("javax.net.ssl.keyStore", "KEYSTORE PATH");
System.setProperty("javax.net.ssl.keyStorePassword", "KEYSTORE PASSWORD");
```

# Git Cmd
```base
git clone 仓库URL 下载远程仓库到本地
git pull 刷新远程代码到本地分子
git branch dev2 在本地创建一个dev2的分支
git checkout dev2 切换到dev2
git checkout src/main/resources/scripts/ksh/ta/tabatdrvr.ksh revert单个文件
git add filename 提交此文件到暂存区
git add -u 将所有修改过的文件提交到暂存区
git commit -a -m "提交的注释" 将所有git add标记的的文件提交到本地
git push origin dev2:dev2 将本地dev2推送到远程dev2(远程不存在dev2则会自动创建)

解决GIT冲突 dev_drop2 -> dev_drop4 冲突
先本地更新dev_drop2 & dev_drop4
然后新建一个dev_drop4的merge分支resolve_conf，切换到resolve_conf
用git bash,输入 git merge dev_drop2 将drop2分支merge到我们的本地分支
然后使用git status查看具体冲突的文件，手动解决，并提交
之后再merge resolve_conf->dev_drop4

fatal: unable to access 'https://github.com/vanyouseea/ms.git/': OpenSSL SSL_read: Connection was reset, errno 10054
解除ssl验证
git config --global http.sslVerify false

设置代理
git config --global http.proxy http://proxy.ncs.com.sg:8080

解除代理
git config --global --unset http.proxy
```
