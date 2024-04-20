@echo off


echo Construint el projecte... & ^
gradlew.bat build & ^

echo Generant documentació... & ^
gradlew.bat generateJavadoc & ^


echo Fet.
