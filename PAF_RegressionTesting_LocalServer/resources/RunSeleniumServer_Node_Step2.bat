rem register the autoit dll into the host so we can use the autoitx command to operate the windows
c:\Windows\SysWOW64\regsvr32.exe /s AutoItX3.dll
c:\Windows\SysWOW64\regsvr32.exe /s AutoItX3_x64.dll


rem change the directory to c:\temp
rem java -jar selenium-server-standalone-2.33.0.jar -h for help 
c:
cd c:\temp
java -Dwebdriver.chrome.driver=c:\temp\chromedriver.exe -Dwebdriver.ie.driver=c:\temp\IEDriverServer.exe -jar selenium-server-standalone-2.35.0.jar -role node  -hub http://localhost:4444/grid/register