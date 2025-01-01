package org.codehaus.mojo.license.download;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.RepositoryCache;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.ProjectDependencyGraph;
import org.apache.maven.monitor.event.EventDispatcher;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.eclipse.aether.RepositorySystemSession;

import static org.junit.Assert.assertNotNull;

class MavenSessionWrapper extends MavenSession {
    private static final String MAVEN_SESSION_MUST_NOT_BE_NULL = "mavenSession must not be null";

    private MavenSession mavenSession;

    MavenSessionWrapper() {
        super(null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public void setProjects(List<MavenProject> projects) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setProjects(projects);
    }

    @Override
    public ArtifactRepository getLocalRepository() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getLocalRepository();
    }

    @Override
    public List<String> getGoals() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getGoals();
    }

    @Override
    public Properties getUserProperties() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getUserProperties();
    }

    @Override
    public Properties getSystemProperties() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getSystemProperties();
    }

    @Override
    public Settings getSettings() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getSettings();
    }

    @Override
    public List<MavenProject> getProjects() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getProjects();
    }

    @Override
    public String getExecutionRootDirectory() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getExecutionRootDirectory();
    }

    @Override
    public MavenExecutionRequest getRequest() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getRequest();
    }

    @Override
    public void setCurrentProject(MavenProject currentProject) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setCurrentProject(currentProject);
    }

    @Override
    public MavenProject getCurrentProject() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getCurrentProject();
    }

    @Override
    public ProjectBuildingRequest getProjectBuildingRequest() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getProjectBuildingRequest();
    }

    @Override
    public List<String> getPluginGroups() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getPluginGroups();
    }

    @Override
    public boolean isOffline() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.isOffline();
    }

    @Override
    public MavenProject getTopLevelProject() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getTopLevelProject();
    }

    @Override
    public MavenExecutionResult getResult() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getResult();
    }

    @Override
    public Map<String, Object> getPluginContext(PluginDescriptor plugin, MavenProject project) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getPluginContext(plugin, project);
    }

    @Override
    public ProjectDependencyGraph getProjectDependencyGraph() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getProjectDependencyGraph();
    }

    @Override
    public void setProjectDependencyGraph(ProjectDependencyGraph projectDependencyGraph) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setProjectDependencyGraph(projectDependencyGraph);
    }

    @Override
    public String getReactorFailureBehavior() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getReactorFailureBehavior();
    }

    @Override
    public MavenSession clone() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.clone();
    }

    @Override
    public Date getStartTime() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getStartTime();
    }

    @Override
    public boolean isParallel() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.isParallel();
    }

    @Override
    public void setParallel(boolean parallel) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setParallel(parallel);
    }

    @Override
    public RepositorySystemSession getRepositorySession() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getRepositorySession();
    }

    @Override
    public void setProjectMap(Map<String, MavenProject> projectMap) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setProjectMap(projectMap);
    }

    @Override
    public List<MavenProject> getAllProjects() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getAllProjects();
    }

    @Override
    public void setAllProjects(List<MavenProject> allProjects) {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        mavenSession.setAllProjects(allProjects);
    }

    @Override
    public Map<String, MavenProject> getProjectMap() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getProjectMap();
    }

    @Override
    public List<MavenProject> getSortedProjects() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getSortedProjects();
    }

    @Override
    public RepositoryCache getRepositoryCache() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getRepositoryCache();
    }

    @Override
    public EventDispatcher getEventDispatcher() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getEventDispatcher();
    }

    @Override
    public boolean isUsingPOMsFromFilesystem() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.isUsingPOMsFromFilesystem();
    }

    @Override
    public Properties getExecutionProperties() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getExecutionProperties();
    }

    @Override
    public PlexusContainer getContainer() {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.getContainer();
    }

    @Override
    public Object lookup(String role) throws ComponentLookupException {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.lookup(role);
    }

    @Override
    public Object lookup(String role, String roleHint) throws ComponentLookupException {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.lookup(role, roleHint);
    }

    @Override
    public List<Object> lookupList(String role) throws ComponentLookupException {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.lookupList(role);
    }

    @Override
    public Map<String, Object> lookupMap(String role) throws ComponentLookupException {
        assertNotNull(MAVEN_SESSION_MUST_NOT_BE_NULL, mavenSession);
        return mavenSession.lookupMap(role);
    }

    public void wrap(MavenSession session) {
        this.mavenSession = session;
    }
}
