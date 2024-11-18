package com.example.demo

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test

class ArchitectureRulesTest {

    private val importedClasses: JavaClasses = ClassFileImporter().importPackages("com.example.demo..")

    @Test
    fun `los controladores no deben depender directamente de repositorios`() {
        val rule = ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..webservice..")
            .should().dependOnClassesThat().resideInAPackage("..repository..")
            .allowEmptyShould(true)

        rule.check(importedClasses)
    }

    @Test
    fun `las clases en el paquete model no deben depender de clases en el paquete service`() {
        val rule = ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..model..")
            .should().dependOnClassesThat().resideInAPackage("..service..")

        rule.check(importedClasses)
    }







}
