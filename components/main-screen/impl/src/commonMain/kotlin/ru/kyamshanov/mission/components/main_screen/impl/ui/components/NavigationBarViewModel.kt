package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.LoggedSession

internal interface NavigationBarViewModel {

    val hasManagerFunctionsState: Value<Boolean>

    fun openProfile()

    fun openCreatingProjectScreen()
}

internal class NavigationBarComponentContext(
    private val context: ComponentContext
) : NavigationBarViewModel, ComponentContext by context {

    private val navigator = requireNotNull(di<NavigationComponent>()).navigator
    private val sessionInfo = requireNotNull(di<SessionFrontComponent>()).sessionInfo

    override val hasManagerFunctionsState: Value<Boolean> =
        MutableValue(
            (sessionInfo.session as? LoggedSession)
                ?.userInfo?.roles?.contains(UserRole.MANAGER) ?: false
        )

    override fun openProfile() {
        //TODO("Not yet implemented")
    }

    override fun openCreatingProjectScreen() {
        //TODO("Not yet implemented")
    }


}