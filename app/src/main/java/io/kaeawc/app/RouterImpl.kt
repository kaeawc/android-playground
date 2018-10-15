package io.kaeawc.app

import io.kaeawc.domain.Router
import io.kaeawc.launch.LaunchActivity

class RouterImpl : Router() {

    override fun launch() = LaunchActivity::class
    override fun viewRepository() = TODO("not implemented")
}
