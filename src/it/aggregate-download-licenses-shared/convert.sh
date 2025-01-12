#!/bin/bash
# Converts output XML file into a markdown table,
# to easily check if the template to check against, is itself correct.

function convertToMarkdown() {
    xsltproc --output "../${1}/${2}.md" "sortedByDepdendencyMd.xslt" "../${1}/${2}.xml"
}

convertToMarkdown "aggregate-download-licenses-sort-by-name" sortedByDependencyName
convertToMarkdown "aggregate-download-licenses-sort-by-pluginId" sortedByDependencyPluginId