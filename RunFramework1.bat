@ECHO OFF
ECHO(
ECHO ===============================================================================
ECHO ====== WelCome To TestYantra's Mobile Automation Tool ======
ECHO ===============================================================================
ECHO(
ECHO -------------------------------------------------------------------------------
ECHO Please first set the Configuration and TestData file Before starting the Execution...
ECHO Once Configuration and TestData file is set, save and CLOSE all files and Hit Enter to start Execution...
:: Print some text
ECHO -------------------------------------------------------------------------------
PAUSE
java -jar SetTestData.jar
timeout 5 > nul
java -jar Driver.jar
timeout 5 > nul
call mvn -f pom.xml test
ECHO -------------------------------------------------------------------------------
ECHO ---- Open "Reports Folder-->index.html"
ECHO ---- To See Current Execution Report 
ECHO -------------------------------------------------------------------------------
PAUSE
