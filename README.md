# Insaniam

Because modding Minecraft makes you go insane.

## What is this?

This is a simple gradle plugin that provides some nice to have features, some specific to Minecraft modding other specific to Java development in general.

## Features

- [x] Changelog generation based on a createachangelog.com schema
  - Takes a segment of the changelog and generates a changelog based on that

That's it for now 😘 but more is coming!

## Usage

`build.gradle`
```groovy
plugins {
    id 'pro.mikey.plugins.insaniam' version '0.1-SNAPSHOT'
}
```

`settings.gradle`
```groovy
pluginManagement {
    repositories {
        maven {
            url 'https://maven.saps.dev/snapshots'
        }
        gradlePluginPortal()
    }
}
```
