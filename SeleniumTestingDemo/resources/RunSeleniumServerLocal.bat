rem register the autoit dll into the host so we can use the autoitx command to operate the windows
c:\Windows\SysWOW64\regsvr32.exe /s AutoItX3.dll
c:\Windows\SysWOW64\regsvr32.exe /s AutoItX3_x64.dll


rem change the directory to c:\temp
rem java -jar selenium-server-standalone-2.33.0.jar -h for help 
c:
cd c:\temp
java -jar selenium-server-standalone-2.33.0.jar -port 8888 -timeout 180 -browserTimeout 120 --trustAllSSLCertificates   >seleniumstartup.log