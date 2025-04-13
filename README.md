# BigIDBackendTask
 
A multithreaded Java application that scans a large text file to find occurrences of common names and logs their positions (line and character offsets). It reads the input file in batches, processes them concurrently using callable tasks, and aggregates the results. All file paths and batch sizes are configurable.

## How to Run

### Prerequisites

- Java 8+
- Gradle

### Steps

```bash
# Clone the repository
git clone https://github.com/yourusername/BigIDTextMatcher.git
cd BigIDTextMatcher

# Build the project
./gradlew build

# Run the application
./gradlew run
```

Just run the `org.bigid.Main` class.
