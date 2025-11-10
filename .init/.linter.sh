#!/bin/bash
cd /home/kavia/workspace/code-generation/recipe-hub-184552-184561/recipe_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

