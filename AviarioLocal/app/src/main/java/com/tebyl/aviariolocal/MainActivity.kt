package com.tebyl.aviariolocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tebyl.aviariolocal.data.AppDatabase
import com.tebyl.aviariolocal.data.BirdRepository
import com.tebyl.aviariolocal.navigation.AviarioNav
import com.tebyl.aviariolocal.ui.theme.AviarioTheme
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db   = AppDatabase.get(this)
        val repo = BirdRepository(db.birdDao())
        setContent {
            AviarioTheme {
                val vm: BirdViewModel = viewModel(factory = BirdViewModel.factory(repo))
                AviarioNav(vm = vm)
            }
        }
    }
}
