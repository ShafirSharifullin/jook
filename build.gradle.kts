plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("nu.studer.jooq") version "7.1.1"
}

val spring_boot_version: String by System.getProperties()
val db_url: String by System.getProperties()
val db_username: String by System.getProperties()
val db_password: String by System.getProperties()

group = "pro.siberian"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    jooqGenerator("org.jooq:jooq-meta-extensions:3.17.4")

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "15"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS) // the default (can be omitted)

    configurations {
        create("main") {  // name of the jOOQ configuration
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN

                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"

                        properties.add(org.jooq.meta.jaxb.Property().apply {
                            key = "scripts"
                            value = "src/main/resources/db/migration/*.sql"
                        })
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "pro.siberian.example"
                        directory = "build/generated-src/jooq/main"  // default (can be omitted)
                    }

                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}