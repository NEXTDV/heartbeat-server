package com.nextdv.harness;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "com.nextdv")
class AgentHarnessArchitectureTest {

  @ArchTest
  static final ArchRule layerDependencies =
      Architectures.layeredArchitecture()
          .consideringOnlyDependenciesInLayers()
          .withOptionalLayers(true)
          .layer("Api").definedBy("com.nextdv.api..")
          .layer("Domain").definedBy("com.nextdv.domain..")
          .layer("Infrastructure").definedBy("com.nextdv.infrastructure..")
          .whereLayer("Domain").mayOnlyBeAccessedByLayers("Api", "Infrastructure")
          .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Api");
}
