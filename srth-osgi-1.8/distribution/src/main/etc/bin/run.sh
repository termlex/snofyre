#!/bin/sh
# remove the spring test bundle which gets felix in a twist
rm lib/org.springframework.osgi.test.jar
rm lib/org.springframework.osgi.mock.jar
rm lib/org.eclipse.osgi*.jar

# start the osgi framework with equinox - with config.ini located in platform folder
java -Xmx500m -jar org.eclipse.osgi.jar -console -configuration platform

#java -Xmx500m -jar pax-runner-0.17.0.jar
