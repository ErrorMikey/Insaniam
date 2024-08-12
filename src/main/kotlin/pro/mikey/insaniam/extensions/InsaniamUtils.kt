package pro.mikey.insaniam.extensions

import org.gradle.api.Project

/**
 * TODO: mod-info manifest generator
 * TODO: neoforge-like version creation
 */
abstract class InsaniamUtils(private val project: Project) {
    fun createChangelog(): String {
        // Pull the changelog settings from the insainam extension
        val data = project.extensions.getByType(InsaniamExtension::class.java).changelog

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
}
