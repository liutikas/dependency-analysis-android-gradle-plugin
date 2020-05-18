package com.autonomousapps.jvm.projects

import com.autonomousapps.advice.Advice
import com.autonomousapps.kit.*

import static com.autonomousapps.kit.Dependency.conscryptUber
import static com.autonomousapps.kit.Dependency.okHttp

class SecurityProviderProject {

  final GradleProject gradleProject

  SecurityProviderProject() {
    this.gradleProject = build()
  }

  private GradleProject build() {
    def builder = new GradleProject.Builder()
    builder.withSubproject('proj') { s ->
      s.sources = sources
      s.withBuildScript { bs ->
        bs.plugins = plugins
        bs.dependencies = dependencies
      }
    }

    def project = builder.build()
    project.writer().write()
    return project
  }

  private List<Plugin> plugins = [Plugin.javaLibraryPlugin]

  private List<Dependency> dependencies = [
    conscryptUber("implementation"),
    okHttp("api")
  ]

  private List<Source> sources = [
    new Source(
      SourceType.JAVA, "Main", "com/example",
      """\
        package com.example;
        
        import okhttp3.OkHttpClient;

        public class Main {
          public OkHttpClient ok() {
            return new OkHttpClient.Builder().build();
          }
        }
      """.stripIndent()
    )
  ]

  final List<Advice> expectedAdvice = []
}
