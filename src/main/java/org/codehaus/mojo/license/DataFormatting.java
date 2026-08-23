package org.codehaus.mojo.license;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/**
 * Information about how to format the data written into the <i>MS Office Excel</i> or <i>LibreOffice Calc</i>
 * file.<br>
 * Except for <code>orderBy</code>: That affects also the order of the data written into the summary XML.
 *
 * @since 2.5.0
 */
public class DataFormatting {
    /**
     * By what column / data are the lines ordered?
     */
    public enum OrderBy {
        /**
         * No sorting, list it in the order it appears in memory.
         */
        none,
        /**
         * Sort by the dependency's name 1st, then by plugin group id, artifact id, etc..<br>
         * Only working if <code>&lt;extendedInfo&gt;</code> is <code>true</code>, as the dependency's name is part
         * of the extended info.
         */
        dependencyName,
        /**
         * Sort only by the dependency's plugin id with the group id, artifact id, etc., ignoring the name.
         */
        dependencyPluginId,
        /**
         * Sort by the dependency's license name.
         * <ul>
         * <li>If there are multiple licenses per project, it takes the first license according to alphabetical
         * order.</li>
         * <li>If there are no licenses in the pom.xml, and there were licenses found via extendedInfo,
         * it will try to sort them by the licenses found there.
         * But the pom.xml's licenses take precedence.</li>
         * </ul>
         */
        licenseName,
        /**
         * Sort by the dependency's license match in the lists in the following order:
         * <ol>
         * <li>{@link #excludedLicenses}</li>
         * <li>{@link #problematicLicenses}</li>
         * <li>{@link #okLicenses}</li>
         * <li>Licenses without a match in the previous lists.</li>
         * </ol>
         */
        licenseMatch
    }

    /**
     * By what column / data are the lines ordered?
     */
    @Parameter(property = "formatting.orderBy", defaultValue = "NONE")
    protected OrderBy orderBy;

    /**
     * If all unknown licenses should be highlighted.
     * Unknown means: There is no entry for a license in {@link #excludedLicenses},
     * {@link #problematicLicenses} or {@link #okLicenses}.
     */
    @Parameter(property = "formatting.unknownLicenses")
    protected boolean highlightUnknownLicenses;

    /**
     * List of licenses which should be highlighted as problematic.
     */
    @Parameter(property = "formatting.problematicLicenses")
    protected List<String> problematicLicenses;

    /**
     * List of licenses which should be highlighted as OK.
     */
    @Parameter(property = "formatting.okLicenses")
    protected List<String> okLicenses;

    /**
     * If licenses found in {@link #excludedLicenses}, {@link #problematicLicenses}, {@link #okLicenses}
     * or where {@link #highlightUnknownLicenses} is used,
     * have a visible border.
     * <p>
     * The border makes license types better visible at a glance, but when quickly scrolling through a long list,
     * it makes licenses look as if they were the only ones belonging to a dependency if it has multiple licenses.
     */
    @Parameter(property = "formatting.matchedLicensesHaveBorder")
    protected boolean matchedLicensesHaveBorder;

    /**
     * Skip the developer info.
     * The report may become much clearer,
     * because often then each dependency has only a single line of information.
     */
    @Parameter(property = "formatting.skipDevelopers")
    protected boolean skipDevelopers;

    public boolean getHighlightUnknownLicenses() {
        return highlightUnknownLicenses;
    }

    public boolean getMatchedLicensesHaveBorder() {
        return matchedLicensesHaveBorder;
    }

    public List<String> getOkLicenses() {
        return okLicenses;
    }

    public void setOkLicenses(List<String> okLicenses) {
        this.okLicenses = okLicenses;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public void setProblematicLicenses(List<String> problematicLicenses) {
        this.problematicLicenses = problematicLicenses;
    }

    public List<String> getProblematicLicenses() {
        return problematicLicenses;
    }

    public boolean getSkipDevelopers() {
        return skipDevelopers;
    }

    @Override
    public String toString() {
        return "DataFormatting{" + "orderBy="
            + orderBy + ", highlightUnknownLicenses="
            + highlightUnknownLicenses + ", problematicLicenses="
            + problematicLicenses + ", okLicenses="
            + okLicenses + ", matchedLicensesHaveBorder="
            + matchedLicensesHaveBorder + ", skipDevelopers="
            + skipDevelopers + '}';
    }
}
