package dev.nanite.insaniam.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import dev.nanite.insaniam.types.ChangeLogData
import javax.inject.Inject

abstract class InsaniamExtension @Inject constructor(private val project: Project) {
    val changelog: ChangeLogData = project.objects.newInstance(ChangeLogData::class.java, project)

    @get:Input
    val minecraftVersion: Property<String> = project.objects.property(String::class.java).apply {
        convention(project.properties["minecraft_version"] as String? ?: "")
    }

    fun changelog(action: Action<ChangeLogData>) {
        action.execute(changelog)
    }

    // Static method to create the extension
    companion object {
        fun changelog(project: Project): ChangeLogData {
            return project.extensions.getByType(InsaniamExtension::class.java).changelog
        }

        fun extension(project: Project): InsaniamExtension {
            return project.extensions.getByType(InsaniamExtension::class.java)
        }
    }
}
