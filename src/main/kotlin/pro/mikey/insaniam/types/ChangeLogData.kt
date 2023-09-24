package pro.mikey.insaniam.types

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import java.util.regex.Pattern

interface ChangeLogData {
    @get:InputFile
    val file: RegularFileProperty

    @get:Input
    val version: Property<String>

    @get:Input
    val fallbackValue: Property<String>

    @get:Input
    val versionPattern: Property<Pattern>
}
