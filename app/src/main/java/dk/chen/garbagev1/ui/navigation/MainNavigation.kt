package dk.chen.garbagev1.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import dk.chen.garbagev1.ui.features.AddWhat
import dk.chen.garbagev1.ui.features.AddWhatScreen
import dk.chen.garbagev1.ui.features.AddWhatViewModel
import dk.chen.garbagev1.ui.features.AddWhere
import dk.chen.garbagev1.ui.features.AddWhereScreen
import dk.chen.garbagev1.ui.features.AddWhereViewModel
import dk.chen.garbagev1.ui.features.Details
import dk.chen.garbagev1.ui.features.DetailsScreen
import dk.chen.garbagev1.ui.features.DetailsViewModel
import dk.chen.garbagev1.ui.features.GarbageGraph
import dk.chen.garbagev1.ui.features.GarbageList
import dk.chen.garbagev1.ui.features.GarbageListScreen
import dk.chen.garbagev1.ui.features.GarbageListViewModel
import dk.chen.garbagev1.ui.features.GarbageSearch
import dk.chen.garbagev1.ui.features.GarbageSortingScreen
import dk.chen.garbagev1.ui.features.GarbageSortingViewModel
import dk.chen.garbagev1.ui.features.ListGroup

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val singleTopNavOptions: NavOptions = navOptions {
        launchSingleTop = true
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GarbageGraph
    ) {
        navigation<GarbageGraph>(startDestination = GarbageSearch) {
            composable<GarbageSearch> {
                GarbageSortingScreen(
                    onNavigate = { event ->
                        when (event) {
                            is GarbageSortingViewModel.NavigationEvent.NavigateToList ->
                                navController.navigate(GarbageList, singleTopNavOptions)
                        }
                    }
                )
            }

            navigation<ListGroup>(startDestination = GarbageList) {
                composable<GarbageList> {
                    GarbageListScreen(
                        onNavigate = { event ->
                            when (event) {
                                is GarbageListViewModel.NavigationEvent.NavigateToAddWhat -> navController.navigate(
                                    route = AddWhat,
                                    navOptions = singleTopNavOptions
                                )

                                is GarbageListViewModel.NavigationEvent.NavigateToDetails -> {
                                    println("DEBUG_ID: The item ID is ${event.itemId}")

                                    navController.navigate (
                                        // TODO add navigation to DetailsScreen logic
                                        route = Details(itemId = event.itemId),
                                        navOptions = singleTopNavOptions
                                    )
                                }

                                is GarbageListViewModel.NavigationEvent.NavigateUp -> navController.navigate (
                                    route = GarbageSearch,
                                    navOptions = singleTopNavOptions
                                )
                            }
                        }
                    )
                }

                composable<Details>(
                    deepLinks = listOf(
                        // TODO add deep linking matching the following URI pattern: "shopping://items/{itemId}" - check the intent-filter in the AndroidManifest.xml file
                        navDeepLink {
                            uriPattern = "garbage://items/{itemId}"
                        }
                    ),
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
                    DetailsScreen(
                        onNavigate = { event: DetailsViewModel.NavigationEvent ->
                            when (event) {
                                is DetailsViewModel.NavigationEvent.NavigateUp -> {
                                    // TODO add back button logic
                                    navController.navigateUp()
                                }
                            }
                        }
                    )
                }
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
                        is AddWhatViewModel.NavigationEvent.NavigateUp -> {
                            // TODO add back button logic
                            navController.navigateUp()
                        }

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
                onNavigate = { event: AddWhereViewModel.NavigationEvent ->
                    when (event) {
                        is AddWhereViewModel.NavigationEvent.CloseDialog -> navController.popBackStack()
                        is AddWhereViewModel.NavigationEvent.NavigateToGarbageList -> navController.popBackStack(
                            route = GarbageSearch,
                            inclusive = false
                        )
                    }
                }
            )
        }
    }
}