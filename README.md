# Insaniam

Because modding Minecraft makes you go insane.

## What is this?

This is a simple gradle plugin that provides some nice to have features, some specific to Minecraft modding other specific to Java development in general.

## Features

- [x] Changelog generation based on a createachangelog.com schema
- [x] Creates standard mod versions based on the current Minecraft version (NeoForge style) 

That's it for now 😘 but more is coming!

Please see the maven for the latest version of the plugin.

```groovy
plugins {
    id 'dev.nanite.plugins.insaniam' version '0.2.0-SNAPSHOT'
}
```

### Changelog generation

```groovy
  insaniam {
    changelog {
      file = file('path/to/changelog.md')
      versionPattern = ~/## \\[[^]]+]/ // This is the default pattern and can be omitted
      fallbackValue = "No changelog provided" // This is the default value and can be omitted
      version = "1.0.0" // This will pull from 'version' in the gradle project if omitted
    }
  }
  
  def changelog = insaniamUtils.createChangelog() // This will create a changelog string that can be used in a task
}
```

### Version generation

```groovy
  version = "1-beta"

  insaniam {
    minecraftVersion = "1.21" // This can be omitted if you have a `minecraft_version` in your gradle project
  }
  
  // This will produce a version string like "21.0.1-beta" based on the Minecraft version in the style of NeoForge versioning
  def modVersion = insaniamUtils.createModVersion() // This will create a mod version string that can be used in a task

  // Sometimes you might just want to instantly change the version to the new one
  // version = modVersion
}
```

## Usage

`build.gradle`
```groovy
plugins {
    id 'dev.nanite.plugins.insaniam' version '0.2.0-SNAPSHOT'
}
```

`settings.gradle`
```groovy
pluginManagement {
    repositories {
        maven {
            url 'https://maven.nanite.dev/snapshots'
        }
        gradlePluginPortal()
    }
}
```
