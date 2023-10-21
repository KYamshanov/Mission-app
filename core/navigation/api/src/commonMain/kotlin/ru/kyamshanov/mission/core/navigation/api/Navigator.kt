package ru.kyamshanov.mission.core.navigation.api

interface Navigator {

    fun navigateTo(screen: Screen)

    fun replaceTo(screen: Screen)

    fun newRootScreen(screen: Screen)

    fun exit()

    fun alert(screen: Screen)

    fun dismissAlert()

    /**
     * @see [ru.kyamshanov.mission.navigation_core.common.SerializableNavigationBoundaryData]
     */
    fun <ReturnDataType : NavigationBoundaryData?> backWithResult(key: String, data: ReturnDataType)
}