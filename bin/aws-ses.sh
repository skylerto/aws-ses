#! /bin/bash

run() {
  java -jar -Dproperties=$1 -Dsandbox=$2 target/aws-ses.1.0-SNAPSHOT.jar
}

run_help() {
  echo "To build the project execute the script with the build argument"
  echo "ex: ./bin/aws-ses build"
  echo "To run, execute the script with the run argument, followed by the properties file followed by the optional sandbox variable"
  echo "ex: ./bin/aws-ses run example.properties true"
}

if [ $# = 0 ]; then
  run_help  
else
  if [ $1 = "build" ]; then
      mvn clean install
  elif [ $1 = "run" ]; then
    if [ -z $2 ]; then
      run_help  
    elif [ -z $3 ]; then
	run $2 $3
    else
	run $2
    fi
  fi
fi

