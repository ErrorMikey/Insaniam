package pro.mikey.insaniam.test.integration.utils

import pro.mikey.insaniam.test.integration.IntegrationTest
import kotlin.test.Test
import kotlin.test.assertTrue

class UtilsTest : IntegrationTest {
    @Test
    fun `test correct version string creation`() {
        val runner = gradleTest()
            .buildScript(
                """
                    version = "4"
                    
                    insaniam {
                        minecraftVersion.set("1.21.1")
                    }
                    
                    println("MOD_VERSION=" + insaniamUtils.createModVersion())
                """.trimIndent()
            ).run("emptyTask")

        val resultText = runner.output

        assertTrue(resultText.contains("MOD_VERSION=21.1.4"))
    }

    @Test
    fun `test correct version from project versions`() {
        val runner = gradleTest()
            .buildScript(
                """
                    version = "4"
                    
                    insaniam {}
                    
                    println("MOD_VERSION=" + insaniamUtils.createModVersion())
                """.trimIndent()
            )
            .addProperty("minecraft_version", "1.21.1")
            .run("emptyTask")

        val resultText = runner.output

        assertTrue(resultText.contains("MOD_VERSION=21.1.4"))
    }

    @Test
    fun `test correct version with non-standard semver minecraft version`() {
        val runner = gradleTest()
            .buildScript(
                """
                    version = "5"
                    
                    insaniam {
                        minecraftVersion.set("1.21")
                    }
                    
                    println("MOD_VERSION=" + insaniamUtils.createModVersion())
                """.trimIndent()
            )
            .run("emptyTask")

        val resultText = runner.output

        assertTrue(resultText.contains("MOD_VERSION=21.0.5"))
    }
}
