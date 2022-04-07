#!/bin/bash

# Important Note: When the build is successful, this script creates a JAR file
# named 'jarname' and moves it to the 'target' directory. It also copies the
# properties file to the 'propfiletarget' directory.

function exit_on_error {
		if [ $1 != 0 ]; then
				echo Error status: $1
				exit
		fi
}

jarname=update.jar
target=~/Library/Java/Extensions/
propfiletarget=~/.java/

echo
echo Compiling Java files...

javac -d build -Xlint update.java
exit_on_error $?

echo
echo Creating $jarname...
echo

cd build
exit_on_error $?

jar cvf $jarname *.class
exit_on_error $?

echo
echo Moving $jarname to $target...

mv $jarname $target
exit_on_error $?

echo
echo Copying Properties file...

cd ..
exit_on_error $?

cp update.prop $propfiletarget
exit_on_error $?

echo
echo Build successful!
