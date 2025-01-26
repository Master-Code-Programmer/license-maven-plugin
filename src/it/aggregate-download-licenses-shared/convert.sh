#!/bin/bash
# Converts output XML file into a markdown table,
# to easily check if the template to check against, is itself correct.
# Then starts a webserver to view the HTML view of the XML files.

function convertToMarkdown() {
    xsltproc --output "../${1}/${2}.html" "sortedByHtml.xslt" "../${1}/${2}.xml"
    echo "HTML link (XSLT conversion): http://0.0.0.0:8000/${1}/${2}.xml"
}

convertToMarkdown "aggregate-download-licenses-sort-by-dependencyName" sortedByDependencyName
convertToMarkdown "aggregate-download-licenses-sort-by-licenseMatch" sortedByLicenseMatch
convertToMarkdown "aggregate-download-licenses-sort-by-licenseName" sortedByLicenseName
convertToMarkdown "aggregate-download-licenses-sort-by-pluginId" sortedByDependencyPluginId

pushd ..
echo "Stop the server with Ctrl+C"
python3 -m http.server
popd || exit