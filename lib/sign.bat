set path=%path%;C:\Program Files\Java\jdk1.6.0_13\bin

#jarsigner -keystore portfolio.cert -storepass password -keypass password Portfolio.jar proserus
jarsigner -keystore portfolio.cert -storepass password -keypass password joda-time-1.6_part2.jar proserus
jarsigner -keystore portfolio.cert -storepass password -keypass password joda-time-1.6_part1.jar proserus
#jarsigner -keystore portfolio.cert -storepass password -keypass password jcommon-1.0.16.jar proserus
#jarsigner -keystore portfolio.cert -storepass password -keypass password jfreechart-1.0.13.jar proserus

pause