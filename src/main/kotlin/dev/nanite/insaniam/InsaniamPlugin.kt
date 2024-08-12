package dev.nanite.insaniam

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reflect.TypeOf
import dev.nanite.insaniam.extensions.InsaniamExtension
import dev.nanite.insaniam.extensions.InsaniamUtils

@Suppress("unused")
class InsaniamPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val commonExtension = project.extensions.create(TypeOf.typeOf(InsaniamExtension::class.java), "insaniam", InsaniamExtension::class.java, project)

        val utilsExtension = project.extensions.create(TypeOf.typeOf(InsaniamUtils::class.java), "insaniamUtils", InsaniamUtils::class.java, project)
    }
}

