@ECHO OFF
REM Remove the existing osgi.test bundle -- which just cases felix to go nuts
IF EXIST bundles\org.springframework.osgi.test.jar (
	ECHO Found osgi.test bundle. This jar bundle will now be deleted!
	DEL bundles\org.springframework.osgi.test.jar
)

@ECHO OFF
REM Remove the existing osgi.mock bundle
IF EXIST bundles\org.springframework.osgi.mock.jar (
	ECHO Found osgi.mock bundle. This jar bundle will now be deleted!
	DEL bundles\org.springframework.osgi.mock.jar
)

@ECHO OFF
REM Launch the osgi console using equinox and set it to show the console to make the tech-head a happy bunny :)
IF EXIST org.eclipse.osgi.jar (
	ECHO Found Equinox jar, launching OSGi Application
	java -Xms250m -Xmx1000m -jar org.eclipse.osgi.jar -console -configuration platform
)
@ECHO OFF