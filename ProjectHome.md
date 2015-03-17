![http://images.cnitblog.com/blog/345627/201310/30145601-4520af968357479cb01bd0472be8aff1.jpg](http://images.cnitblog.com/blog/345627/201310/30145601-4520af968357479cb01bd0472be8aff1.jpg)
> ## Jenkins Run Result ##
![https://selenium-demo-alter.googlecode.com/svn/wiki/jenkins.png](https://selenium-demo-alter.googlecode.com/svn/wiki/jenkins.png)
  1. Make Selenium to support the Windows Application;
  1. used testing tools ,etc selenium,testNG,Ant,jenkins.
  1. next update is : Recovery Scenario which is like the QTP tool ,so it can capture all the exceptions and errors at the run time .but it's important that it can put the error in our report ,but QTP cannot do that;


  * support installing chrome browser into every slave host in the jenkins build ;
  * support starting or stop the selenium server from ant script;   --done now by using     Ant script:
```
     <!-- Start the selenium server -->
    <target name="startSeleniumServer">
        <echo>Starting Selenium Server...</echo>
         <MyTimestamp></MyTimestamp>
        <java jar="${lib.dir}\selenium-server-standalone-2.33.0.jar" fork="true" spawn="true">
            <arg line="-singlewindow -log ${logs.dir}\selenium_server_startup.log" />
        </java>
        <sleep seconds="10" />
        <echo message="We had started the selenium server in the local running host now ......"></echo>
        <MyTimestamp></MyTimestamp>
    </target>

      <!-- Stop the selenium Server -->
    <target name="stopSeleniumServer">
        <echo> Trying to stop selenium server ... </echo>
        <MyTimestamp></MyTimestamp>
        <get taskname="selenium-shutdown" src="http://16.158.69.53:4444/selenium-server/driver/?cmd=shutDownSeleniumServer" dest="${logs.dir}\selenium_server_shutdown.log" ignoreerrors="true" />
        <echo taskname="selenium-shutdown" message="Shutdown selenium server complete,return ok.." />
        <MyTimestamp></MyTimestamp>
    </target>
 

```


  * stop the selenium grid node command:

```
    http://localhost:4444/selenium-server/driver?cmd=getLogMessages (Error 500   java.lang.NullPointerException)
-- http://localhost:4444/lifecycle-manager?action=shutdown (Does nothing)

```
  * support sending email after execution ;
  * support testNG result publish into the jenkins project;

  * Next Update --Download Ant from Apache automation .
  * Support sending the email after the testing is completed ,the code as blow:
```
   
	
```

**Next generation(out-dated):
> Configure the selenium standalone jar file well. end date 8/23/2013** Done 


**Supported Running from Jenkins with Slave running ;**

#   Supported the (RemoteWebDriver) instance;

#   Supported capture all the error when we run the script;

#   Wrapped many useful common functions we need to use in Selenium;

#   Wrapped the AutoItX functions which we can test the windows application in Selenium

> ,but you need to run with local server ,which mean is not capablity with

> (RemoteWebDriver) instance;
#   Execution with case run generated in testlink or testrail ;