#!/bin/bash

PROJECT_NAME="BadIceCream"
BASE_FOLDER=""
TEST_FOLDER="src/test/java"
MAIN_FOLDER="src/main/java"
OUTPUT_CSV="tsdetector-input-full.csv"

echo "Generating $OUTPUT_CSV..."
> "$OUTPUT_CSV"

# Function to find the corresponding class under test
find_class_under_test() {
    local test_file_path=$1
    # Replace TEST_FOLDER prefix with MAIN_FOLDER, and Test.java with .java
    local relative_path="${test_file_path#$TEST_FOLDER/}"
    local class_under_test="$MAIN_FOLDER/${relative_path/Test.java/.java}"
    
    if [[ -f "$class_under_test" ]]; then
        echo "$class_under_test"
    else
        echo ""
    fi
}

# Iterate over test files and generate CSV rows
while IFS= read -r -d '' test_file; do
    class_under_test=$(find_class_under_test "$test_file")
    if [[ -n "$class_under_test" ]]; then
        echo "$PROJECT_NAME,$test_file,$class_under_test" >> "$OUTPUT_CSV"
    fi
done < <(find "$TEST_FOLDER" -type f -name "*Test.java" -print0)

echo "CSV file generated at: $OUTPUT_CSV"
