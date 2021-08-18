@REM mvn install:install-file ^
@REM    -Dfile=src/test/resources/batik-all-1.14.jar ^
@REM    -DgroupId=org.apache.batik ^
@REM    -DartifactId=org.apache.batik.all ^
@REM    -Dversion=1.14 ^
@REM    -Dpackaging=jar ^
@REM    -DgeneratePom=true

@REM mvn install:install-file ^
@REM    -Dfile=src/test/resources/xml-apis-ext-1.3.04.jar ^
@REM    -DgroupId=org.apache.batik ^
@REM    -DartifactId=org.apache.batik.xml-apis-ext ^
@REM    -Dversion=1.14 ^
@REM    -Dpackaging=jar ^
@REM    -DgeneratePom=true

mvn install:install-file ^
   -Dfile=src/test/resources/xmlgraphics-commons-2.6.jar ^
   -DgroupId=org.apache.batik ^
   -DartifactId=org.apache.batik.xmlgraphics-commons-2.6 ^
   -Dversion=1.14 ^
   -Dpackaging=jar ^
   -DgeneratePom=true