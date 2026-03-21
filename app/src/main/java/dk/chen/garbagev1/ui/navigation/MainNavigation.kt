package dk.chen.garbagev1.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import dk.chen.garbagev1.ui.features.garbage.AddWhat
import dk.chen.garbagev1.ui.features.garbage.AddWhatScreen
import dk.chen.garbagev1.ui.features.garbage.AddWhatViewModel
import dk.chen.garbagev1.ui.features.garbage.AddWhere
import dk.chen.garbagev1.ui.features.garbage.AddWhereScreen
import dk.chen.garbagev1.ui.features.garbage.AddWhereViewModel
import dk.chen.garbagev1.ui.features.garbage.GarbageGraph
import dk.chen.garbagev1.ui.features.garbage.GarbageListScreen
import dk.chen.garbagev1.ui.features.garbage.GarbageListViewModel
import dk.chen.garbagev1.ui.features.garbage.GarbageSortingScreen
import dk.chen.garbagev1.ui.features.garbage.GarbageSortingViewModel
import dk.chen.garbagev1.ui.features.garbage.SortingList
import dk.chen.garbagev1.ui.features.garbage.SortingSearch
import dk.chen.garbagev1.ui.features.recycling.Bins
import dk.chen.garbagev1.ui.features.recycling.RecyclingScreen
import dk.chen.garbagev1.ui.features.settings.Settings
import dk.chen.garbagev1.ui.features.settings.SettingsScreen
import dk.chen.garbagev1.ui.features.garbage.AffaldKbh
import dk.chen.garbagev1.ui.features.garbage.AffaldKbhScreen

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val destinations = listOf(
        BottomNavigation.GarbageTab,
        BottomNavigation.BinsTab,
        BottomNavigation.SettingsTab
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                destinations.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.title)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavigation.GarbageTab.route,
            Modifier.padding(paddingValues = innerPadding)
        ) {
            garbageNavGraph(navController)
            composable<Bins> {
                RecyclingScreen()
            }
            composable<Settings> {
                SettingsScreen()
            }
        }
    }
}


fun NavGraphBuilder.garbageNavGraph(navController: NavHostController) {
    val singleTopNavOptions: NavOptions = navOptions {
        launchSingleTop = true
    }

    navigation<GarbageGraph>(startDestination = SortingSearch) {
        composable<SortingSearch> {
            GarbageSortingScreen(
                onNavigate = { event ->
                    when (event) {
                        is GarbageSortingViewModel.NavigationEvent.NavigateToList -> {
                            navController.navigate(SortingList())
                        }

                        is GarbageSortingViewModel.NavigationEvent.NavigateToAdd -> {
                            navController.navigate(AddWhat)
                        }

                        is GarbageSortingViewModel.NavigationEvent.NavigateToAffaldKbh -> {
                            navController.navigate(AffaldKbh)
                        }
                    }
                }
            )
        }

        composable<AffaldKbh> {
            AffaldKbhScreen()
        }

        composable<SortingList>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "garbage://items/{itemId}" }
            )
        ) {
            GarbageListScreen(
                onNavigate = { event ->
                    when (event) {
                        is GarbageListViewModel.NavigationEvent.NavigateToAddWhat -> navController.navigate(
                            route = AddWhat,
                            navOptions = singleTopNavOptions
                        )

                        is GarbageListViewModel.NavigationEvent.NavigateToDetails -> {}

                        is GarbageListViewModel.NavigationEvent.NavigateUp ->
                            navController.navigateUp()
                    }
                }
            )
        }
    }

    composable<AddWhat>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 500)
            )
        }
    ) {
        AddWhatScreen(
            onNavigate = { event ->
                when (event) {
                    is AddWhatViewModel.NavigationEvent.NavigateUp -> navController.popBackStack()
                    is AddWhatViewModel.NavigationEvent.NavigateToAddWhere -> navController.navigate(
                        route = AddWhere(event.what),
                        navOptions = singleTopNavOptions
                    )
                }
            }
        )
    }
    dialog<AddWhere> {
        AddWhereScreen(
            onNavigate = { event ->
                when (event) {
                    is AddWhereViewModel.NavigationEvent.CloseDialog -> navController.popBackStack()
                    is AddWhereViewModel.NavigationEvent.NavigateToGarbageList -> navController.navigate(
                        route = SortingList(),
                        navOptions = navOptions {
                            popUpTo(route = GarbageGraph)
                        }
                    )
                }
            }
        )
    }
}