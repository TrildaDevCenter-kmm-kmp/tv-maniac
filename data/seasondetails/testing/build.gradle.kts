plugins { alias(libs.plugins.tvmaniac.multiplatform) }

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.data.seasondetails.api)
        implementation(projects.database)

        implementation(libs.coroutines.core)
      }
    }
  }
}
