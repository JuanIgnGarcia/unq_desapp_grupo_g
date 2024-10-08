// versions
val SWAGGER_VERSION = "3.0.0"
val SWAGGER_SPRINGDOC_OPENAPI = "2.3.0"

val SPRINGDOC_OPENAPI= "2.0.3"

val LOGGING_INTERCEPTOR= "4.9.2"
val OKHTTP= "4.9.3"
val GSON= "2.8.9"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	war
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.sonarqube") version "5.1.0.4882"
	id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

kotlin {
	jvmToolchain(17)
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$SPRINGDOC_OPENAPI")
	implementation("io.springfox:springfox-swagger2:$SWAGGER_VERSION")
	implementation("io.springfox:springfox-swagger-ui:$SWAGGER_VERSION")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$SWAGGER_SPRINGDOC_OPENAPI")
	implementation ("com.squareup.okhttp3:logging-interceptor:$LOGGING_INTERCEPTOR")
	implementation ("com.squareup.okhttp3:okhttp:$OKHTTP")
	implementation ("com.google.code.gson:gson:$GSON")




	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
	}
	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it).matching {
				exclude(
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/dto/**",
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/exceptions/**",
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/repository/**",
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/request/**",
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/service/**",
					"unq_desapp_grupo_g/src/main/kotlin/com/example/demo/webservice/**",
				)
			}
		})
	)
}


sonar {
	properties {
		property("sonar.host.url", "https://sonarcloud.io/")
		property("sonar.organization", "juanigngarcia")
		property("sonar.projectKey", "JuanIgnGarcia_unq_desapp_grupo_g")
	}
}
