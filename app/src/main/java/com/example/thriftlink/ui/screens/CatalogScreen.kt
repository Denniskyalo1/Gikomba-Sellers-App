package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.ui.components.SwipeRefreshBox
import com.example.thriftlink.ui.components.ProductItem
import com.example.thriftlink.ui.components.SearchBar
import com.example.thriftlink.ui.components.CategoryTabs
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: ProductViewModel,
    onOpenDetail: (Int) -> Unit,
    onOpenOrders: () -> Unit,
    onLogout: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    // -----------------------------
    // OBSERVE STATEFLOW PRODUCTS
    // -----------------------------
    val products by viewModel.productList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var refreshing by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet(
                modifier = Modifier.width(250.dp) // Set the width of the drawer
            ) {
                // Header/Title
                Text("ThriftLink Menu", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                Divider()

                // "Your Orders" Navigation Item
                NavigationDrawerItem(
                    label = { Text("Your Orders") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onOpenOrders()
                    }
                )
//  Theme Toggle Item
                NavigationDrawerItem(
                    label = { Text("Dark Mode") }, // Simple text label
                    selected = false,
                    icon = { Icon(Icons.Filled.Brightness4, contentDescription = "Toggle Theme") },
                    // Use the badge slot for the trailing component (Switch)
                    badge = {
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = {
                                // Don't call onThemeToggle here, let the row click handle it
                            }
                        )
                    },
                    // The onClick for the whole row handles the actual theme toggle
                    onClick = { onThemeToggle() }
                )
                // "Logout" Button/Item
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogout()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                Column(Modifier.fillMaxWidth()) {

                    // 1. STANDARD TopAppBar with Hamburger Menu Icon
                    TopAppBar(
                        title = { Text("EXPLORE") },
                        navigationIcon = {
                            // Hamburger Menu Icon to open the drawer
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Open Menu")
                            }
                        },
                        // Use surface color to match the rest of the screen
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        // You can also add actions here if needed, like a Cart/Orders icon:
                        actions = {
                            IconButton(onClick = onOpenOrders) {
                                Icon(Icons.Filled.ShoppingCart, contentDescription = "Your Orders")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. SEARCH BAR — kept directly below the TopAppBar
                    SearchBar(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        // FIX 2: This modifier usage is correct, provided SearchBar accepts it.
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        )
        { paddingValues ->

        SwipeRefreshBox(
            isRefreshing = refreshing,
            onRefresh = {
                refreshing = true
                viewModel.refreshProducts { refreshing = false }
            },
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                // -----------------------------
                // TITLE
                // -----------------------------
                Text(
                    text = "EXPLORE",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // -----------------------------
                // CATEGORY FILTER TABS
                // -----------------------------
                CategoryTabs(
                    selected = viewModel.selectedFilter,
                    onSelect = { viewModel.selectFilter(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // -----------------------------
                // PRODUCT GRID
                // -----------------------------
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onClick = { onOpenDetail(product.id) }
                        )
                    }
                }
            }
        }
    }
}}
