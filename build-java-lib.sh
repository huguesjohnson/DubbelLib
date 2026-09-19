################################################################################
# build java library without JavaFX components
################################################################################

echo 'removing previous builds..'
rm -rf ./build/java/

echo 'creating output directories..'
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/audio/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/audio/xm/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/aws/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/converters/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/example/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/file/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/file/filter/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/ips/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/opml/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/swing/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/ui/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/util/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/webpublisher/

echo 'compiling..'
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/audio/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/audio/xm/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/aws/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/converters/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/example/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/file/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/file/filter/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/ips/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/opml/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/swing/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/ui/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/util/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/webpublisher/*.java

echo 'moving compiled classes..'
mv ./java/src/com/huguesjohnson/dubbel/audio/*.class ./build/java/bin/com/huguesjohnson/dubbel/audio/
mv ./java/src/com/huguesjohnson/dubbel/audio/xm/*.class ./build/java/bin/com/huguesjohnson/dubbel/audio/xm/
mv ./java/src/com/huguesjohnson/dubbel/aws/*.class ./build/java/bin/com/huguesjohnson/dubbel/aws/
mv ./java/src/com/huguesjohnson/dubbel/converters/*.class ./build/java/bin/com/huguesjohnson/dubbel/converters/
mv ./java/src/com/huguesjohnson/dubbel/example/*.class ./build/java/bin/com/huguesjohnson/dubbel/example/
mv ./java/src/com/huguesjohnson/dubbel/file/*.class ./build/java/bin/com/huguesjohnson/dubbel/file/
mv ./java/src/com/huguesjohnson/dubbel/file/filter/*.class ./build/java/bin/com/huguesjohnson/dubbel/file/filter/
mv ./java/src/com/huguesjohnson/dubbel/ips/*.class ./build/java/bin/com/huguesjohnson/dubbel/ips/
mv ./java/src/com/huguesjohnson/dubbel/opml/*.class ./build/java/bin/com/huguesjohnson/dubbel/opml/
mv ./java/src/com/huguesjohnson/dubbel/swing/*.class ./build/java/bin/com/huguesjohnson/dubbel/swing/
mv ./java/src/com/huguesjohnson/dubbel/ui/*.class ./build/java/bin/com/huguesjohnson/dubbel/ui/
mv ./java/src/com/huguesjohnson/dubbel/util/*.class ./build/java/bin/com/huguesjohnson/dubbel/util/
mv ./java/src/com/huguesjohnson/dubbel/webpublisher/*.class ./build/java/bin/com/huguesjohnson/dubbel/webpublisher/

echo 'building jar..'
now=$(date +"%Y%m%d")
rm ./java/MANIFEST.MF
cp ./java/MANIFEST-LIB.MF ./java/MANIFEST.MF
jar cfm ./build/java/dubbel"-$now".jar ./java/MANIFEST.MF  -C ./build/java/bin/ .

echo 'done'