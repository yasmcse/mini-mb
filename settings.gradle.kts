pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "mini-mb"
include (":app")
include(":core")
include("domain")
include("feature")
include(":core:data")
include(":domain:usecase")
include(":feature:login")
include(":feature:home")
include(":core:designsystem")
include(":core:common")
include(":domain:repositorycontract")
