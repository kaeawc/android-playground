package io.kaeawc.app

import io.kaeawc.domain.Router
import io.kaeawc.launch.LaunchActivity

class AppRouter : Router() {

    override fun launch() = LaunchActivity::class
}
