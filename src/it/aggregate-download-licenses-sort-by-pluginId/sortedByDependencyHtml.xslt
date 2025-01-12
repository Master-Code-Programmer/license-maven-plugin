<?xml version="1.0" encoding="UTF-8"?>
<!-- Converts output to HTML table. -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Output method set to html for HTML table -->
    <xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
                doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>

    <xsl:template match="/*//*[1]">
        <link rel="stylesheet" type="text/css" href="sortBy.css"/>
    </xsl:template>

    <xsl:template match="/dependencyInfos">
        <!-- Create HTML table with CSS styling -->
        <table border="1" cellpadding="5" cellspacing="0" style="width:100%; border-collapse:collapse;">
            <thead>
                <tr style="background-color:#f0f0f0;">
                    <!-- Create header row -->
                    <th>Name</th>
                    <th>Group ID</th>
                    <th>Artifact ID</th>
                    <th>Version</th>
                    <th>License</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iterate through dependencyInfos elements -->
                <xsl:apply-templates select="dependencyInfos"/>
            </tbody>
        </table>
        <script src="sortBy.js"/>
    </xsl:template>

    <xsl:template match="dependencyInfos">
        <!-- Create a row for each dependencyInfos element -->
        <tr>
            <td><xsl:value-of select="@name"/></td>
            <td><xsl:value-of select="@groupId"/></td>
            <td><xsl:value-of select="@artifactId"/></td>
            <td><xsl:value-of select="@version"/></td>
            <td><xsl:value-of select="@license"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
