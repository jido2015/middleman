package com.project.middleman

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel
import com.project.middleman.designsystem.themes.MiddlemanTheme
import com.project.middleman.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
            setContent {
                val appStateViewModel: AppStateViewModel = hiltViewModel()
                val showNotificationBarSheet by appStateViewModel.showNotificationBarSheet.collectAsState()

                val statusBarColor =
                    if (showNotificationBarSheet) MaterialTheme.colorScheme.background else Color.White

                // Theme updates the status bar
                MiddlemanTheme(showNotificationBarSheet = showNotificationBarSheet) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(statusBarColor) // ensure background matches
                            .statusBarsPadding(),      // avoid content under status bar
                        color = statusBarColor
                    ) {
                        AppNavigation(appStateViewModel = appStateViewModel)
                    }
                }
            }
        }
    }
}
