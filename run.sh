#!/bin/sh

# Cleaning the terminal
clear

command="$1"

# Checks if the -h command was used and show the Help if it's the case
if [ "${command}" = "-h" ] || [ "${command}" = "--help" ]; then
  echo "This program spawns 10 workers and searches for the string 'Lpfn' in a random stream of data."
  echo
  echo "usage: ./run.sh [-h | --help] [-t | --timeout]"
  echo
  echo "Requirements"
  echo "Make sure to have java installed in your system"
  echo
  echo "-h, --help                        - Shows this help message"
  echo "-t <value>, --timeout <value>     - Sets the value passed as the timeout in seconds for each worker. The default value is 60 sec"
  echo
  echo "EXAMPLES"
  echo "./run.sh -t 45"
  echo "./run.sh --timeout 120"
  echo

  exit
fi

# Gets the timeout option if passed and executes the program with it
if [ "${command}" = "-t" ] || [ "${command}" = "--timeout" ]; then
  timeout="$2"
fi

cd target || exit

java -jar leapfin-exercise-1.0-SNAPSHOT.jar "${timeout}"