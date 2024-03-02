package com.mik2003.boukennihon.core


import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class ParseKML {
    companion object {
        fun parseKML(inputStream: InputStream): LocationsList {
            val locations = LocationsList()

            inputStream.use { stream ->
                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val parser = factory.newPullParser()
                parser.setInput(stream, null)

                var eventType = parser.eventType
                var name: String? = null
                var placemarkName: String? = null  // Variable to store the <Placemark> name
                var coordinates: Pair<Double, Double>? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            name = parser.name
                            if (name == "name") {
                                val text = parser.nextText()
                                // Trim the text to remove any leading or trailing whitespace
                                val trimmedText = text.trim()
                                if (trimmedText.isNotEmpty()) {
                                    // Assign the trimmed text as the name of the placemark
                                    placemarkName = trimmedText
                                }
                            } else if (name == "coordinates") {
                                val text = parser.nextText()
                                val parts = text.split(",")
                                if (parts.size >= 2) {
                                    coordinates = Pair(parts[1].toDouble(), parts[0].toDouble())
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (parser.name == "Placemark") {
                                if (coordinates != null) {
                                    locations.add(Location(placemarkName ?: "", Coordinate(coordinates.first, coordinates.second)))
                                }
                                // Reset variables
                                placemarkName = null
                                coordinates = null
                            }
                        }
                    }
                    eventType = parser.next()
                }
            }

            return locations
        }
    }
}
