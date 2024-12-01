package org.codehaus.mojo.license;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

import org.apache.maven.DefaultMaven;
import org.apache.maven.Maven;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.settings.MavenSettingsBuilder;
import org.apache.maven.settings.Settings;
import org.eclipse.aether.DefaultRepositorySystemSession;


public class DownloadLicensesIT extends AbstractMojoTestCase {

    public static final String LICENSE_AGGREGATE_DOWNLOAD_LICENSES = "license:" + AggregateDownloadLicensesMojo.GOAL;
    //    public static final String ARTIFACT_ID =
    // "org.codehaus.mojo.license:AbstractDownloadLicensesMojoIT:1.0-SNAPSHOT";
    public static final String ARTIFACT_ID = "license-maven-plugin";

    public void testDownloadLicenses() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/unit/AbstractDownloadLicensesMojoIT/pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());

        // The officially recommended way.
        // Doesn't work at all.
        //        AggregateDownloadLicensesMojo mojo = (AggregateDownloadLicensesMojo) lookupMojo( "compile", pom );
        //        mojo.execute();

        // Can be created, but MavenProject isn't executable at all.
        //        MavenProject project = readMavenProject(pom);
        //        MavenPlugin mavenPlugin = (MavenPlugin) lookupConfiguredMojo(project, "compile"); //"parameters");
        //        mavenPlugin.execute();

        //        MojoExecution mojoExecution = newMojoExecution("compile");
        //        mojoExecution.

        //        AggregateDownloadLicensesMojo downloadLicensesMojo = new AggregateDownloadLicensesMojo();

        // Doesn't find it.
        // lookupMojo(LICENSE_AGGREGATE_DOWNLOAD_LICENSES, pom);

        // Doesn't configure the default parameters.
        //        downloadLicensesMojo = (AggregateDownloadLicensesMojo) configureMojo(downloadLicensesMojo,
        //            extractPluginConfiguration(ARTIFACT_ID, pom));

        /* ================= */
        //        MavenExecutionRequest executionRequest = new DefaultMavenExecutionRequest();
        //        ProjectBuildingRequest buildingRequest = executionRequest.getProjectBuildingRequest();

        MavenSettingsBuilder mavenSettingsBuilder =
            (MavenSettingsBuilder) getContainer().lookup(MavenSettingsBuilder.ROLE);
        Settings settings = mavenSettingsBuilder.buildSettings();

        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setPom(pom);
        request.setLocalRepositoryPath(settings.getLocalRepository());
        request.setGoals(Collections.singletonList("compile"));

        MavenExecutionRequestPopulator populator = getContainer().lookup(MavenExecutionRequestPopulator.class);
        populator.populateDefaults(request);

        Properties userProperties = new Properties();
        userProperties.setProperty("java.version", "11.0");
        request.setUserProperties(userProperties);

        DefaultMaven maven = (DefaultMaven) getContainer().lookup(Maven.class);
        //        DefaultRepositorySystemSession repoSession =
        //            (DefaultRepositorySystemSession)
        //                maven.newRepositorySession(request);
        //        buildingRequest.setRepositorySession(repoSession);

        //        ProjectBuilder projectBuilder = this.lookup(ProjectBuilder.class);
        //        MavenProject project = projectBuilder.build(pom, buildingRequest).getProject();

        //        project = (MavenProject) lookupConfiguredMojo(project, "compile");
        //        project.execute();

        MavenExecutionResult executed = maven.execute(request);
        if (executed.getExceptions() != null) {
            for (Throwable exception : executed.getExceptions()) {
                exception.printStackTrace();
            }
            assertTrue(executed.getExceptions().isEmpty());
        }

        /* =================== */

        //        MavenExecutionRequest executionRequest = new DefaultMavenExecutionRequest();
        //        executionRequest.setPom(pom);
        //        //request.setLocalRepositoryPath(settings.getLocalRepository());
        //
        //        MavenExecutionRequestPopulator populator =
        //            getContainer().lookup(MavenExecutionRequestPopulator.class);
        //        populator.populateDefaults(executionRequest);

        // Working, but without the default parameters.
        //        assertNotNull(downloadLicensesMojo);
        //        assertNotNull("Parameters must be initialized with defaults.",
        // downloadLicensesMojo.remoteRepositories);
        //        downloadLicensesMojo.execute();
    }

    protected MavenProject readMavenProject(File pom) throws ProjectBuildingException, Exception {
        //        File pom = new File( basedir, "pom.xml" );
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory(pom.getParentFile());
        ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        configuration.setRepositorySession(new DefaultRepositorySystemSession());
        ProjectBuilder projectBuilder = lookup(ProjectBuilder.class);
        MavenProject project = projectBuilder
            // If this fails, make sure the parent minimal Maven version isn't later than this Maven version.
            .build(pom, configuration)
            .getProject();
        assertNotNull(project);
        return project;
    }
}
