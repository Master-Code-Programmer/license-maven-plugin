package org.codehaus.mojo.license.download;

import org.apache.maven.DefaultMaven;
import org.apache.maven.Maven;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.settings.MavenSettingsBuilder;
import org.apache.maven.settings.Settings;
import org.codehaus.mojo.license.AbstractDownloadLicensesMojo;
import org.codehaus.mojo.license.AggregateDownloadLicensesMojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class DownloadLicensesIT extends AbstractMojoTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadLicensesIT.class);

    public static final String LICENSE_AGGREGATE_DOWNLOAD_LICENSES = "license:" + AggregateDownloadLicensesMojo.GOAL;
    private final Parameter parameter;

    private static class DependencyInfo {
        final String name;
        final String groupId;
        final String artifactId;
        final String version;
        final String license;

        public DependencyInfo(String name, String groupId, String artifactId, String version, String license) {
            this.name = name;
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
            this.license = license;
        }

        @Override
        public String toString() {
            return "DependencyInfo{" +
                "name='" + name + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", license='" + license + '\'' +
                '}';
        }
    }

    public static class Parameter {
        final AbstractDownloadLicensesMojo.DataFormatting dataFormatting;
        final String pom;

        public Parameter(AbstractDownloadLicensesMojo.DataFormatting dataFormatting, String pom) {
            this.dataFormatting = dataFormatting;
            this.pom = pom;
        }

        @Override
        public String toString() {
            return "Order: " + dataFormatting.orderBy.toString();
        }
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Parameter> data() {
        AbstractDownloadLicensesMojo.DataFormatting dataFormatting1 = new AbstractDownloadLicensesMojo.DataFormatting();
        dataFormatting1.orderBy = AbstractDownloadLicensesMojo.DataFormatting.OrderBy.dependencyName;

        AbstractDownloadLicensesMojo.DataFormatting dataFormatting2 = new AbstractDownloadLicensesMojo.DataFormatting();
        dataFormatting2.orderBy = AbstractDownloadLicensesMojo.DataFormatting.OrderBy.dependencyPluginId;

        AbstractDownloadLicensesMojo.DataFormatting dataFormatting3 = new AbstractDownloadLicensesMojo.DataFormatting();
        dataFormatting3.orderBy = AbstractDownloadLicensesMojo.DataFormatting.OrderBy.licenseName;

        return Arrays.asList(
            new Parameter(dataFormatting1, "pom - orderBy.dependencyName.xml"),
            new Parameter(dataFormatting2, "pom - orderBy.dependencyPluginId.xml"),
            new Parameter(dataFormatting3, "pom - orderBy.licenseName.xml")
        );
    }

    public DownloadLicensesIT(Parameter parameter) {
        super();
        this.parameter = parameter;
    }

    /**
     * Tests if all licenses are correctly formatted.
     *
     * @throws Exception Exception happened, e.g., at setup.
     */
    @Test
    public void testDataFormatting() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/unit/AbstractDownloadLicensesMojoIT/" + parameter.pom);
        assertNotNull(pom);
        assertTrue(pom.exists());

        // "MavenSettingsBuilder" may be deprecated, but there is no alternative interface?!
        MavenSettingsBuilder mavenSettingsBuilder =
            (MavenSettingsBuilder) getContainer().lookup(MavenSettingsBuilder.ROLE);
        Settings settings = mavenSettingsBuilder.buildSettings();

        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setPom(pom);
        request.setLocalRepositoryPath(settings.getLocalRepository());
        request.setGoals(Collections.singletonList(LICENSE_AGGREGATE_DOWNLOAD_LICENSES));

        MavenExecutionRequestPopulator populator = getContainer().lookup(MavenExecutionRequestPopulator.class);
        populator.populateDefaults(request);

        Properties userProperties = new Properties();
        userProperties.setProperty("java.version", "11.0");
        request.setUserProperties(userProperties);

        DefaultMaven maven = (DefaultMaven) getContainer().lookup(Maven.class);

        MavenExecutionResult executed = maven.execute(request);
        if (executed.getExceptions() != null) {
            for (Throwable exception : executed.getExceptions()) {
                System.out.println("Error in executing \"testDownloadLicenses\"\n" + exception + "\n"
                    + Arrays.toString(exception.getStackTrace()));
            }
            assertTrue("Exceptions during Maven execution", executed.getExceptions().isEmpty());
        }

        checkResultingLicensesXml();
    }

    private void checkResultingLicensesXml() throws ParserConfigurationException, SAXException, IOException {
        Path resourcesPath = Paths.get(getBasedir(),
            "src/test/resources/unit/AbstractDownloadLicensesMojoIT/target/generated-resources");
        Path licensesPath = Paths.get(resourcesPath.toString(), "licenses.xml");

        File licensesFile = licensesPath.toFile();
        if (!licensesFile.exists()) {
            throw new FileNotFoundException("Licenses file not found: " + licensesFile.getAbsolutePath());
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(licensesFile);

        NodeList dependenciesRoot = document.getElementsByTagName("dependencies");
        assertEquals(1, dependenciesRoot.getLength());
        NodeList dependencies = dependenciesRoot.item(0).getChildNodes();
        List<DependencyInfo> dependencyInfos = new ArrayList<>();
        for (int i = 0; i < dependencies.getLength(); i++) {
            if (dependencies.item(i).getNodeName().equals("dependency")) {
                NodeList dependency = dependencies.item(i).getChildNodes();
                String name = null;
                String groupId = null;
                String artifactId = null;
                String version = null;
                String license = null;
                for (int j = 0; j < dependency.getLength(); j++) {
                    if (dependency.item(j).getNodeName().equals("name")) {
                        name = dependency.item(j).getTextContent();
                    } else if (dependency.item(j).getNodeName().equals("groupId")) {
                        groupId = dependency.item(j).getTextContent();
                    } else if (dependency.item(j).getNodeName().equals("artifactId")) {
                        artifactId = dependency.item(j).getTextContent();
                    } else if (dependency.item(j).getNodeName().equals("version")) {
                        version = dependency.item(j).getTextContent();
                    } else if (dependency.item(j).getNodeName().equals("license")) {
                        license = dependency.item(j).getTextContent();
                    }
                }
                assertNotNull(groupId);
                assertNotNull(artifactId);
                assertNotNull(version);
                dependencyInfos.add(new DependencyInfo(name, groupId, artifactId, version, license));
                if (name == null) {
                    System.out.println("Dependency without name: " + groupId + ":" + artifactId + ":" + version);
                }
            }
        }
        List<DependencyInfo> sorted;
        switch (parameter.dataFormatting.orderBy) {
            case dependencyName:
                sorted = dependencyInfos.stream().sorted(Comparator.comparing((DependencyInfo o) ->
                            o.name == null ? "" : o.name)
                        .thenComparing((DependencyInfo o) -> o.groupId)
                        .thenComparing(o -> o.artifactId)
                        .thenComparing(o -> o.version))
                    .collect(Collectors.toList());
                break;
            case dependencyPluginId:
                sorted = dependencyInfos.stream().sorted(Comparator.comparing((DependencyInfo o) -> o.groupId)
                        .thenComparing(o -> o.artifactId)
                        .thenComparing(o -> o.version))
                    .collect(Collectors.toList());
                break;
            case licenseName:
                sorted = dependencyInfos.stream().sorted(Comparator.comparing((DependencyInfo o) ->
                            o.license == null ? "" : o.license)
                        .thenComparing((DependencyInfo o) -> o.name == null ? "" : o.name)
                        .thenComparing((DependencyInfo o) -> o.groupId)
                        .thenComparing(o -> o.artifactId)
                        .thenComparing(o -> o.version))
                    .collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parameter.dataFormatting.orderBy);
        }
        for (int i = 0; i < dependencyInfos.size(); i++) {
            DependencyInfo dependencyInfo = dependencyInfos.get(i);
            DependencyInfo sortedInfo = sorted.get(i);
            // These must be pointer identical!
            assertSame("XML: " + dependencyInfo.name + ", Sorted: " + sortedInfo.name,
                dependencyInfo, sortedInfo);
        }
    }
}
