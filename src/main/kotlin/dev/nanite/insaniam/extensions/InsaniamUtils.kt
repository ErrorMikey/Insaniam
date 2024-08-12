package dev.nanite.insaniam.extensions

import org.gradle.api.Project

/**
 * TODO: mod-info manifest generator
 */
abstract class InsaniamUtils(private val project: Project) {
    fun createChangelog(): String {
        // Pull the changelog settings from the insainam extension
        val data = InsaniamExtension.changelog(project)

        // Get the changelog text or try the file value
        val changelogFile = data.file.asFile
        if (!changelogFile.get().exists()) {
            throw IllegalArgumentException("Changelog file does not exist: ${changelogFile.get().path}")
        }

        val version = data.version.getOrElse(project.version.toString())
        val versionPattern = data.versionPattern.get()

        var versionData = ""
        var isReading = false
        changelogFile.get().forEachLine { line ->
            if (line.matches(versionPattern.toRegex())) {
                isReading = version in line
                if (!isReading) {
                    return@forEachLine
                }
            }

            if (isReading) {
                versionData += line + "\n"
            }
        }

        return versionData.trim().takeIf { it.isNotEmpty() }
            ?: data.fallbackValue.get()
    }

    /**
     * Takes in a suffix of the mod version, then uses the projects Minecraft version to create a full mod version.
     *
     * This handles the weird edge case that Minecraft creates with its inconsistent versioning. When ever we see a
     * version without a third number, we automatically add a 0 to the end.
     */
    fun createModVersion(): String {
        val ext = InsaniamExtension.extension(project)
        val minecraftVersion = ext.minecraftVersion.get()

        val version = project.version.toString()

        if (minecraftVersion.isEmpty()) {
            throw IllegalArgumentException("Minecraft version is not set")
        }

        if (version.isEmpty()) {
            throw IllegalArgumentException("Mod version is not set")
        }

        val mcVersionParts = minecraftVersion.split(".").let {
            // Remove the first part of the version
            it.subList(1, it.size)
        }.let {
            // Add a 0 to the end if the version is only 1 part
            if (it.size == 1) {
                it + "0"
            } else {
                it
            }
        }

        // Finally, add the mod version to the end
        return mcVersionParts.joinToString(".") + "." + version
    }
}
