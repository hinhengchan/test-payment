# test-payment
UI automation for Neat payment

## Prerequisites
### Assumptions
* OSX is used for development
* The following softwares / plugins are installed
 
### Java
Reference: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

### Eclipse
Reference: https://www.eclipse.org/downloads/

### Maven
Reference: https://maven.apache.org/install.html

### M2Eclipse (m2e) plugin
Reference: http://download.eclipse.org/technology/m2e/releases/

### Cucumber-eclipse plugin
Reference: http://toolsqa.com/cucumber/install-cucumber-eclipse-plugin/

## Command
To run the UI automation, open Terminal and cd to /test-payment.

### Assumptions
* OTP for autotest login is fixed (for testing purpose)
* Always enough fund in testing account
* Payment test cases can only be ran on Desktop mode (Since mobile has a different UI design, more work has to be done for the payment test cases to run on Mobile mode)
* The payee account (autotest) exists
* The test-generated payee accounts should be cleaned up automatically (but this is out of scope for this project)
* Only a set of sample test cases are included (a lot more field validations and payment routes are not tested in this sample project)

### simply run with default options
```
mvn clean test -Dcucumber.tags=@desktop
```

### or run with options
```
mvn clean test -Dplatform=chrome -Dmode=Desktop -DbaseUrl=https://neat-business-qa.ap-southeast-1.elasticbeanstalk.com -DtimeoutSecond=10 -Dcucumber.tags=@desktop
```

### Options
* -Dplatform (default: chrome)
    * headless-chrome
    * chrome
    * firefox
    * opera
* -Dmode (default: Desktop)
    * Desktop
    * Mobile
* -DbaseUrl (default: https://neat-business-qa.ap-southeast-1.elasticbeanstalk.com)
* -DtimeoutSecond (default: 10)
* -DmobileEmulation (only used when -Dmode=mobile, default: Nexus\ 5X)
    * Nexus\ 5X
    * Apple\ iPhone\ 6
* -DchromedriverPath (default: ./chromedriver)
    * /usr/lib/chromium-browser/chromedriver
* -Dtest.tags (OR) or -Dcucumber.tags (AND)
    * @bvt
    * @functional
    * @high
    * @low
    * @desktop
    * @mobile
* -Dsurefire.forkCount (number of threads to run)
            
### tags
* bvt AND high
```
-Dcucumber.tags=@bvt,@high
```
* bvt OR high
```
-Dtest.tags=@bvt,@high
```
* except bvt
```
-Dtest.tags=~@bvt
```
            
## Report
view results in the target folder
