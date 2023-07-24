import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependency

/**
 * Хак для описание плагинов [.get().pluginId], которые были описаны в buildSrc.
 * @see <a href="https://github.com/gradle/gradle/issues/20084">gradle issue #20084</a>
 */
fun PluginDependenciesSpec.use(provider: Provider<PluginDependency>) = id(provider.get().pluginId)

/**
 * Хак для нейминга JS модулей
 */
fun org.gradle.api.Project.getJsModuleName(): String {
    val prefix = rootProject.name.toString()
    val module = project.path.toString().replace(":", "-")
    return "$prefix$module"
}

fun org.gradle.api.Project.getModuleNamespace(): String =
    "ru.kyamshanov.mission" + project.path.toString().replace(":", ".").replace("-", "_")