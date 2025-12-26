FROM maven:3.9.6-eclipse-temurin-11

# Install Google Chrome
RUN apt-get update && \
    apt-get install -y wget gnupg2 && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /workspace

# Copy project files
COPY . .

# Set environment variable for Maven
ENV MAVEN_OPTS="-Dmaven.repo.local=/workspace/.m2/repository"

# Default command: run tests (can be overridden)
CMD ["mvn", "-f", "selenium-tests/pom.xml", "clean", "verify", "-Pdev"]
