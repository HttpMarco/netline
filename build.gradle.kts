plugins {
    id("java")
    alias(libs.plugins.nexus.publish)

}

group = "dev.httpmarco"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(rootProject.libs.junitBom))
    testImplementation(rootProject.libs.bundles.testing)

    testAnnotationProcessor(rootProject.libs.lombok)

    implementation(rootProject.libs.netty5)
    implementation(rootProject.libs.bundles.utils)

    annotationProcessor(rootProject.libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    apply(plugin = "maven-publish")
}

extensions.configure<PublishingExtension> {
    publications {
        create("library", MavenPublication::class.java) {
            from(project.components.getByName("java"))

            pom {
                name.set(project.name)
                url.set("https://github.com/httpmarco/netline")
                description.set("Simple network library for Java, based on Netty 5")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        name.set("Mirco Lindenau")
                        email.set("mirco.lindenau@gmx.de")
                    }
                }
                scm {
                    url.set("https://github.com/httpmarco/netline")
                    connection.set("https://github.com/httpmarco/netline.git")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(System.getenv("ossrhUsername")?.toString() ?: "")
            password.set(System.getenv("ossrhPassword")?.toString() ?: "")
        }
    }
    useStaging.set(!project.rootProject.version.toString().endsWith("-SNAPSHOT"))
}