package org.codehaus.mojo.license.download;

import java.io.File;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingResult;

import static org.junit.Assert.assertNotNull;

class ProjectBuilderWrapper implements ProjectBuilder {
    private static final String PROJECT_BUILDER_MUST_NOT_BE_NULL = "projectBuilder must not be null";

    private ProjectBuilder projectBuilder;

    public void wrap(ProjectBuilder projectBuilder) {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        this.projectBuilder = projectBuilder;
    }

    @Override
    public ProjectBuildingResult build(File projectFile, ProjectBuildingRequest request)
            throws ProjectBuildingException {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        return projectBuilder.build(projectFile, request);
    }

    @Override
    public ProjectBuildingResult build(Artifact projectArtifact, ProjectBuildingRequest request)
            throws ProjectBuildingException {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        return projectBuilder.build(projectArtifact, request);
    }

    @Override
    public ProjectBuildingResult build(Artifact projectArtifact, boolean allowStubModel, ProjectBuildingRequest request)
            throws ProjectBuildingException {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        return projectBuilder.build(projectArtifact, allowStubModel, request);
    }

    @Override
    public ProjectBuildingResult build(ModelSource modelSource, ProjectBuildingRequest request)
            throws ProjectBuildingException {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        return projectBuilder.build(modelSource, request);
    }

    @Override
    public List<ProjectBuildingResult> build(List<File> pomFiles, boolean recursive, ProjectBuildingRequest request)
            throws ProjectBuildingException {
        assertNotNull(PROJECT_BUILDER_MUST_NOT_BE_NULL, projectBuilder);
        return projectBuilder.build(pomFiles, recursive, request);
    }

    // ------------------

    @Override
    public int hashCode() {
        return projectBuilder.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return projectBuilder.equals(obj);
    }

    @Override
    public String toString() {
        return projectBuilder == null ? super.toString() : projectBuilder.toString();
    }
}
