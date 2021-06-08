# 6310_team11

## How to compile project
### 1. Go to root folder(6310_team11) of project
cd 6310_team11
### 2.Compile
javac -d bin src/com/gatech/osmowsis/action/*.java src/com/gatech/osmowsis/controller/*.java src/com/gatech/osmowsis/simstate/*.java src/com/gatech/osmowsis/simsystem/*.java src/com/gatech/osmowsis/square/*.java src/com/gatech/osmowsis/strategy/*.java src/com/gatech/osmowsis/ui/frame/*.java src/com/gatech/osmowsis/ui/model/*.java src/com/gatech/osmowsis/ui/panel/*.java src/com/gatech/osmowsis/ui/service/*.java
### 3.Create jar under 6310_team11/bin folder
a.

cd bin

b.

jar cfe osmowsis.jar com/gatech/osmowsis/simsystem/Main com/gatech/osmowsis/action/*.class com/gatech/osmowsis/controller/*.class com/gatech/osmowsis/simstate/*.class com/gatech/osmowsis/simsystem/*.class com/gatech/osmowsis/square/*.class com/gatech/osmowsis/strategy/*.class com/gatech/osmowsis/ui/frame/*.class com/gatech/osmowsis/ui/model/*.class com/gatech/osmowsis/ui/panel/*.class com/gatech/osmowsis/ui/service/*.class ../images/*

## How to run project
java -jar osmowsis.jar "name of scenario file"
