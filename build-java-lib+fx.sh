################################################################################
# build java library (non-executable) with JavaFX components
################################################################################

echo 'building regular java code..'
sh build-java-lib.sh

echo 'creating javafx output directories..'
mkdir -p ./build/java/bin/com/huguesjohnson/dubbel/fx/

echo 'compiling..'
javac -cp ./java/lib/javafx.base.jar:./java/lib/javafx.controls.jar:./java/lib/javafx.fxml.jar:./java/lib/javafx.graphics.jar:./java/lib/javafx.media.jar:./java/lib/javafx.swing.jar:javafx.web.jar ./java/src/com/huguesjohnson/dubbel/fx/*.java

echo 'moving compiled classes..'
mv ./java/src/com/huguesjohnson/dubbel/fx/*.class ./build/java/bin/com/huguesjohnson/dubbel/fx/

echo 'building jar..'
now=$(date +"%Y%m%d")
jar cf ./build/java/dubbel-fx"-$now".jar -C ./build/java/bin/ .

echo 'done'


