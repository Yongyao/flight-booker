#!/bin/bash

# Get the script's directory
DIR="$(cd "$(dirname "$0")" && pwd)"

# Find the latest JAR matching the expected name pattern
JAR_FILE=$(ls "$DIR"/target/flight-booker-*.jar 2>/dev/null | head -n 1)

# Check if a JAR file was found
if [[ ! -f "$JAR_FILE" ]]; then
  echo "Error: No JAR file found in target/. Run 'mvn package' first."
  exit 1
fi

# Run the JAR with all passed arguments
exec java -jar "$JAR_FILE" "$@"
