# msToken
This tool helps get the refresh token for any App created in portal.azure.com

Client Id = 9b354133-9271-4398-8797-c2c31ab6329f

Client Secret = 7TS9.CiIRLJj~6__8G4wG3Sy1cpctyV.Aa

Redirect Uri = https://107.173.149.207/getToken

Example Page: https://107.173.149.207

# Generate jks file by keytool
keytool -genkeypair -alias hqr -keyalg RSA -keypass 123456 -keystore hqr.jks -storepass 123456 -validity 3650
