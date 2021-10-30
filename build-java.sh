echo 'removing previous builds..'
rm -rf ./build/java/

echo 'creating output directories..'
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/audio/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/converters/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/file/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/file/filter/
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/util/

echo 'compiling..'
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/audio/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/converters/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/file/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/file/filter/*.java
javac -cp ./java/src/ ./java/src/com/huguesjohnson/dubbel/util/*.java

echo 'moving compiled classes..'
mv ./java/src/com/huguesjohnson/dubbel/audio/*.class ./build/java/bin/com/huguesjohnson/dubbel/audio/
mv ./java/src/com/huguesjohnson/dubbel/converters/*.class ./build/java/bin/com/huguesjohnson/dubbel/converters/
mv ./java/src/com/huguesjohnson/dubbel/file/*.class ./build/java/bin/com/huguesjohnson/dubbel/file/
mv ./java/src/com/huguesjohnson/dubbel/file/filter/*.class ./build/java/bin/com/huguesjohnson/dubbel/file/filter/
mv ./java/src/com/huguesjohnson/dubbel/util/*.class ./build/java/bin/com/huguesjohnson/dubbel/util/

echo 'building jar..'
jar cvf ./build/java/dubbel.jar -C ./build/java/bin/ .

echo 'done'


