plugins {
  id("plugin.tvmaniac.multiplatform")
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.core.base)
        implementation(projects.core.networkUtil)
        implementation(projects.core.paging)
        implementation(projects.core.util)
        implementation(projects.data.popularshows.api)
        implementation(projects.data.requestManager.api)
        implementation(projects.database)
        implementation(projects.tmdbApi.api)

        api(libs.coroutines.core)

        implementation(libs.kotlinInject.runtime)
        implementation(libs.sqldelight.extensions)
        implementation(libs.sqldelight.paging)
        implementation(libs.kotlinx.atomicfu)
        implementation(libs.store5)
      }
    }
  }
}
