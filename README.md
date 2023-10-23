# hds
Hiconsy Design System

## Gradle
Add the dependency below into your module's build.gradle file:
```
dependencies {
    implementation 'com.github.wnehdals1:hds:latest_version'
}
```
Add the dependency below intto your module's settings.gradle file:
```
dependencyResolutionManagement {
    ...
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
