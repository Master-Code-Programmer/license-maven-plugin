<?xml version="1.0" encoding="UTF-8"?>
<!-- Converts output to Markdown table. Convert via 'convert.sh' script. -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Output method set to text for markdown -->
    <xsl:output method="text" encoding="UTF-8"/>

    <xsl:template match="/dependencyInfos">
        <!-- Create header row -->
        <xsl:text>| Name | Group ID | Artifact ID | Version | License |&#xa;</xsl:text>
        <xsl:text>|------|----------|-------------|---------|--------|&#xa;</xsl:text>

        <!-- Iterate through dependencyInfos elements -->
        <xsl:apply-templates select="dependencyInfos"/>
    </xsl:template>

    <xsl:template match="dependencyInfos">
        <!-- Create a row for each dependencyInfos element -->
        <xsl:text>| </xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text> | </xsl:text>
        <xsl:value-of select="@groupId"/>
        <xsl:text> | </xsl:text>
        <xsl:value-of select="@artifactId"/>
        <xsl:text> | </xsl:text>
        <xsl:value-of select="@version"/>
        <xsl:text> | </xsl:text>
        <xsl:value-of select="@license"/>
        <xsl:text> |&#xa;</xsl:text>
    </xsl:template>

</xsl:stylesheet>
