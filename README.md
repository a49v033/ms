# msToken
This tool helps get the refresh toke for any App created in portal.azure.com

You just need to prepare 3 items:

Application (client) ID
Client secrets
Configure your web uri to https://ip/getToken

For the Redirect Uri is your IP/getToken

Such as your Ip is 107.173.149.207

Client Id = 9b354133-9271-4398-8797-c2c31ab6329f
Client Secret = 7TS9.CiIRLJj~6__8G4wG3Sy1cpctyV.Aa
Redirect Uri = https://107.173.149.207/getToken

# Generate jks file by keytool
keytool -genkeypair -alias hqr -keyalg RSA -keypass 123456 -keystore hqr.jks -storepass 123456
