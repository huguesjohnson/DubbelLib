################################################################################
# builds an executable .jar that contains the retail clerk build tools
################################################################################
echo 'removing previous builds..'
rm -rf ./build/java/

echo 'creating output directories..'
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/file/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/util/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/objects/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/objects/echo/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/parameters/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/xmToEsf/

echo 'compiling..'
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/file/PathResolver.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/util/DateUtil.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/util/GenesisColorUtil.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/util/ZipUtil.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/retailclerk/build/objects/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/retailclerk/build/objects/echo/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/retailclerk/build/parameters/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/retailclerk/build/xmToEsf/*.java
javac -cp ./java/src/:./java/lib/gson-2.10.1.jar ./java/src/com/huguesjohnson/dubbel/retailclerk/build/*.java

echo 'moving compiled classes..'
mv ./java/src/com/huguesjohnson/dubbel/file/*.class ./build/java/bin/com/huguesjohnson/dubbel/file/
mv ./java/src/com/huguesjohnson/dubbel/util/*.class ./build/java/bin/com/huguesjohnson/dubbel/util/
mv ./java/src/com/huguesjohnson/dubbel/retailclerk/build/objects/*.class ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/objects/
mv ./java/src/com/huguesjohnson/dubbel/retailclerk/build/objects/*.class ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/objects/echo/
mv ./java/src/com/huguesjohnson/dubbel/retailclerk/build/parameters/*.class ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/parameters/
mv ./java/src/com/huguesjohnson/dubbel/retailclerk/build/xmToEsf/*.class ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/xmToEsf/
mv ./java/src/com/huguesjohnson/dubbel/retailclerk/build/*.class ./build/java/bin/com/huguesjohnson/dubbel/retailclerk/build/

echo 'building jar..'
now=$(date +"%Y%m%d")
jar cfm ./build/java/RCBuildTools"-$now".jar ./java/MANIFEST.MF  -C ./build/java/bin/ .
