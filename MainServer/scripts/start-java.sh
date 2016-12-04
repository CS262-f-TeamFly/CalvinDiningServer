#!/bin/bash

# Environment variables
NAME=teamFlyServer
DIR=/var/cs262/$NAME/src/
PID=$DIR/$USER-$NAME.pid

# Write our PID file
echo $$ > $DIR/$USER-$NAME.pid

# Change to our working directory
cd $DIR

# Run this script to compile/start the cs262 data service.
javac -cp "../lib/*" edu/calvin/cs262/User.java edu/calvin/cs262/Poll.java edu/calvin/cs262/Response.java edu/calvin/cs262/DiningResource.java
java -cp ".:../lib/*" edu.calvin.cs262.DiningResource
