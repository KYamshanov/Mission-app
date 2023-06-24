import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependency

/**
 * Хак для описание плагинов [.get().pluginId], которые были описаны в buildSrc.
 * @see <a href="https://github.com/gradle/gradle/issues/20084">gradle issue #20084</a>
 */
fun PluginDependenciesSpec.use(provider: Provider<PluginDependency>) = id(provider.get().pluginId)