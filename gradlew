#!/bin/bash

# Attempt to set APP_HOME
if [ -z "$APP_HOME" ] ; then
  APP_HOME="$(cd "$(dirname "$0")" >/dev/null 2>&1 && pwd -P)"
fi

# Set up the gradle command
GRADLE="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Execute Gradle
java -jar "$GRADLE" "$@"
