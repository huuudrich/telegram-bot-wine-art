plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.wine.art"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

	// Telegram
	implementation("org.telegram:telegrambots-spring-boot-starter:6.8.0")
	implementation("org.telegram:telegrambotsextensions:6.8.0")
	// Redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.data:spring-data-redis")
	implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")
	//PDF
	implementation("org.apache.pdfbox:pdfbox:2.0.24")
	//QR
	implementation("com.google.zxing:core:3.4.1")
	implementation("com.google.zxing:javase:3.4.1")
}

tasks {
	jar {
		manifest {
			attributes(
				"Main-Class" to "org.wine.art.AppKt"
					  )
		}
	}

	withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
		archiveClassifier.set("")
		mergeServiceFiles()
	}

	build {
		dependsOn(shadowJar)
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

