package com.example.jsonphapitomaszkaczor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.jsonphapitomaszkaczor.navigation.NavGraph
import com.example.jsonphapitomaszkaczor.ui.theme.JSONPHAPITomaszKaczorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()

        setContent {
            val navController = rememberNavController()
            JSONPHAPITomaszKaczorTheme {
                NavGraph(navController = navController)
            }
        }
    }

    private fun requestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val granted = ContextCompat.checkSelfPermission(this, permission)

        if (granted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 100)
        }
    }
}
