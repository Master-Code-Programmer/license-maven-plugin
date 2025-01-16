<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Dependency Infos</h2>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Group ID</th>
                        <th>Artifact ID</th>
                        <th>Version</th>
                        <th>Licenses</th>
                    </tr>
                    <xsl:apply-templates select="/dependencyInfos/dependencyInfos"/>
                </table>
                <script src="sortBy.js"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="dependencyInfos">
        <tr>
            <xsl:call-template name="create-cell">
                <xsl:with-param name="value" select="@name"/>
            </xsl:call-template>

            <xsl:call-template name="create-cell">
                <xsl:with-param name="value" select="@groupId"/>
            </xsl:call-template>

            <xsl:call-template name="create-cell">
                <xsl:with-param name="value" select="@artifactId"/>
            </xsl:call-template>

            <xsl:call-template name="create-cell">
                <xsl:with-param name="value" select="@version"/>
            </xsl:call-template>

            <td>
                <table border="1">
                    <tr><th>Licenses</th></tr>
                    <xsl:for-each select="licenses">
                        <tr><td><xsl:value-of select="."/></td></tr>
                    </xsl:for-each>
                </table>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="create-cell">
        <xsl:param name="value"/>
        <td>
            <xsl:choose>
                <xsl:when test="$value != ''">
                    <xsl:value-of select="$value"/>
                </xsl:when>
                <xsl:otherwise>
                    N/A
                </xsl:otherwise>
            </xsl:choose>
        </td>
    </xsl:template>

</xsl:stylesheet>
