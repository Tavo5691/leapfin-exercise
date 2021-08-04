#!/bin/sh

# Cleaning the terminal
clear

# Checks if the -h command was used and show the Help if it's the case
if [ "$1" = "-h" ]; then
  echo This is the h command

  exit
fi

timeout="$1"

cd target/classes/com/leapfin || exit

java Program "${timeout}"