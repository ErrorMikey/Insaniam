package pro.mikey.insaniam.extensions

import groovy.lang.Closure
import groovy.lang.DelegatesTo
import org.gradle.api.Project
import pro.mikey.insaniam.types.ChangeLogData

abstract class InsaniamUtils(private val project: Project) {
    fun createChangelog(
        @DelegatesTo(ChangeLogData::class) closure: Closure<*>
    ): String {
        val data = project.objects.newInstance(ChangeLogData::class.java)
        closure.delegate = data
        closure.call()

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
            ?: data.fallbackValue.getOrElse("No changelog data found")
    }
}
