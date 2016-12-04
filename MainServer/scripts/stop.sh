#!/bin/bash

# Environment variables
NAME=teamFlyServer
DIR=/var/cs262/$NAME/src/
PID=$DIR/$USER-$NAME.pid

# Write our PID file
/usr/bin/pkill -P `cat $DIR/$USER-$NAME.pid`

# sleep 1 second
sleep 1

# clean up screen
/usr/bin/screen -wipe 
