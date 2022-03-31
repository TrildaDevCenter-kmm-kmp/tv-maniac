import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    namespace = "com.thomaskioko.tvmaniac.shared.domain.lastairepisodes.api"
}

dependencies {
    commonMainApi(projects.shared.database)
    commonMainImplementation(projects.shared.core.util)
    commonMainImplementation(libs.kotlin.coroutines.core)
}
