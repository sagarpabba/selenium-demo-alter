# Problems Occurred Jenkins #

when integrating selenium with Jenkins ,you will met some errors ,that caused as this reason :
```
GUI Testing in Windows
Most Windows services -- including those run with the option "Allow service to interact with desktop" in Windows XP and Vista -- do not have access to many of the computer's resources, including the console display.  This may cause Automated GUI Tests to fail if you are running Apache Tomcat as a Windows Service and are doing any GUI testing. This is true at least for AWT and Abbot frameworks.  A typical error might look similar to this:
http://wiki.hudson-ci.org/display/HUDSON/Tomcat 
```

error from the jenkins console:
Call to IWebBrowser2::Navigate2 (Link to this error someone had met:https://code.google.com/p/selenium/issues/detail?id=4279 )
```
INFO:2014-04-21 05:50:44 AM:Retrying request
INFO:2014-04-21 05:50:44 AM:Selenium Server Capabilities actually is :
Capabilities [{platform=WINDOWS, javascriptEnabled=true, elementScrollBehavior=0, ignoreZoomSetting=true, enablePersistentHover=false, ie.ensureCleanSession=false, browserName=internet explorer, enableElementCacheCleanup=true, unexpectedAlertBehaviour=accept, version=8, ie.usePerProcessProxy=false, cssSelectorsEnabled=true, ignoreProtectedModeSettings=true, requireWindowFocus=true, handlesAlerts=true, initialBrowserUrl=http://localhost:1578/, ie.forceCreateProcessApi=false, nativeEvents=false, browserAttachTimeout=0, ie.browserCommandLineSwitches=, takesScreenshot=true}]
org.openqa.selenium.WebDriverException: Failed to navigate to http://www.google.com. This usually means that a call to the COM method IWebBrowser2::Navigate2() failed. (WARNING: The server did not provide any stacktrace information)
Command duration or timeout: 18 milliseconds
Build info: version: '2.41.0', revision: '3192d8a6c4449dc285928ba024779344f5423c58', time: '2014-03-27 11:29:39'
System info: host: 'test17', ip: '15.6.39.124', os.name: 'Windows Server 2008', os.arch: 'amd64', os.version: '6.0', java.version: '1.7.0_55'
Session ID: 5fa0bfc3-3b36-4d63-b169-ee2a92790691
Driver info: org.openqa.selenium.ie.InternetExplorerDriver
Capabilities [{platform=WINDOWS, javascriptEnabled=true, elementScrollBehavior=0, ignoreZoomSetting=true, enablePersistentHover=false, ie.ensureCleanSession=false, browserName=internet explorer, enableElementCacheCleanup=true, unexpectedAlertBehaviour=accept, version=8, ie.usePerProcessProxy=false, cssSelectorsEnabled=true, ignoreProtectedModeSettings=true, requireWindowFocus=true, handlesAlerts=true, initialBrowserUrl=http://localhost:1578/, ie.forceCreateProcessApi=false, nativeEvents=false, browserAttachTimeout=0, ie.browserCommandLineSwitches=, takesScreenshot=true}]
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
```


# Best Prepare Solution #

how to solve:
  * Solution 1:Install Jenkins via command line ,no need to intall the web container again (http://wiki.hudson-ci.org/display/HUDSON/Tomcat)

**Command line: java -jar Jenkins.war ,then access it from this URL:http://localhost:8080 ,also you can find the extracted jenkins app from here maybe:C:\Users\youuser\AppData\Local\Temp\jetty-0.0.0.0-8080-jenkins.war--any-\webapp**


  * Solution 2:Not intall Tomcat or other contaner as windows service ,run it from the command line and scheduled a  task to run the tomcat container when the host is logged on .
  1. http://forgetfulprogrammer.wordpress.com/2011/09/21/netbeans-gui-tests-on-jenkins-windows/
  1. http://wiki.hudson-ci.org/display/HUDSON/Tomcat

## Here I used the solution 2 to run the jenkins in a configured web container(Like Tomcat) ##