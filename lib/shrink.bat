set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_13
set ANT_HOME=C:\Program Files\ant\apache-ant-1.8.0
set path=%path%;;%ANT_HOME%\BIN;%JAVA_HOME%\bin;

set classpath=%classpath%;%JAVA_HOME%\lib;

cmd /k ant

pause