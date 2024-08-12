package pro.mikey.insaniam.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import pro.mikey.insaniam.types.ChangeLogData
import javax.inject.Inject

abstract class InsaniamExtension @Inject constructor(private val project: Project) {
    val changelog: ChangeLogData = project.objects.newInstance(ChangeLogData::class.java, project)

    fun changelog(action: Action<ChangeLogData>) {
        action.execute(changelog)
    }
}
