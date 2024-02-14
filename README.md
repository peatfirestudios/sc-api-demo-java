# sc-api-demo-java
Demo repository for accessing the Savings Champion API using Java 11.

## Prerequisites
You need to have Java 11 JDK or later installed on your machine and the `JAVA_HOME` environment variable set to the installation directory of your JDK.

## Configure, build and run the demo
Configure the API key and secret as environment variables.

```bash
export SAVINGSCHAMPION_API_CLIENT_ID=[your-client-id]
export SAVINGSCHAMPION_API_CLIENT_SECRET=[your-client-secret]
```

Compile the code with Gradle. The following command will download the Gradle build manager and compile the code.

```bash
sh gradlew build
```

Run the code with Gradle.

```bash
sh gradlew run
```
