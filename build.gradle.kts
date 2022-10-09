import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.liquibase.gradle") version "2.1.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}


group = "ru.pvndpl"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-mail")
/*    implementation("org.springframework.boot:spring-boot-starter-security")*/
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //liquibase deps
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("org.postgresql:postgresql:42.5.0")
    liquibaseRuntime("info.picocli:picocli:4.6.1")
    //liquibaseRuntime("org.liquibase:liquibase-kotlin-dsl")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
/*    testImplementation("org.springframework.security:spring-security-test")*/
}

val databaseUsername: String = "postgres"
val databasePassword: String = "postgres"
val databaseUrl: String = "jdbc:postgresql://localhost:5432/pvndpl"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "driver" to "org.postgresql.Driver",
            "changeLogFile" to "src/main/resources/liquibase/changelog.yml",
            "url" to databaseUrl,
            "username" to databaseUsername,
            "password" to databasePassword
        )
    }
    runList = "main"
}

/*
tasks.register("main") {
    // depend on the liquibase status task
    dependsOn("update")
}*/
