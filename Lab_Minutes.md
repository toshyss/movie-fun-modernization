# Modernization section

## Breaking the Monolith into Components 
### Question I'm not sure the following means
```
We will eventually be moving all movie-specific code to the movies component. To ensure this is still available to the movie-fun-app, add the movies component as an additional dependency in the movie-fun-app build.gradle file:

compile project(":components:movies")
```

### Stack: Failed to build: Can't find `components:movies:`

It's because "components/build.gradle"
 was not recognized as a Java component.
 
We should have created `java.gradle` at the top layer and `apply` it from build.gradle in `components` sub project.

```
user@Seminar144:~/workspace/movie-fun-modernization$ ./gradlew clean build

FAILURE: Build failed with an exception.

* What went wrong:
Could not determine the dependencies of task ':applications:movie-fun-app:bootWar'.
> Could not resolve all task dependencies for configuration ':applications:movie-fun-app:runtimeClasspath'.
   > Could not resolve project :components:movies.
     Required by:
         project :applications:movie-fun-app
      > Unable to find a matching configuration of project :components:movies: None of the consumable configurations have attributes.

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 0s
```

### Stack: Failed to build (compile err)

We had to the following:
- Modify import statement related to `BlobStore` cuz we moved them
- Move the dependencies in applications/build.gradle used by specific component (e.g. `albums`)  to the build.gradle under `components/albmus`

### Stack: Failed to test

1. The way of testing
Probably, we didn't start the app when exec `./gradlew clean build`.

```
org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://localhost:8080/": Connection refused (Connection refused); nested exception is java.net.ConnectException: Connection refused (Connection refused)
```

1. Somehow the path for @Controller was changed

---

## Microservices

- Make components microservices



