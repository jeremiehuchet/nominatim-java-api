Nominatim Java API
==================

This library provides a client for [Nominatim](https://wiki.openstreetmap.org/wiki/Nominatim). This API provides search and reverse geocoding operations.

⚠️ This is an old library you should not use anymore in modern applications.  
💡 Prefer generating a client using an OpenAPI contract like the one proposed in [osm-search/Nominatim #1697](https://github.com/osm-search/Nominatim/issues/1697).

Quickstart
----------

Maven users can add the following dependency to their pom.xml:

    <dependency>
      <groupId>fr.dudie</groupId>
      <artifactId>nominatim-api</artifactId>
      <version>3.4</version>
    </dependency>

*Note that it is fair and reasonable to specify an email address when using the Nominatim API*, see the [Notminatim Usage Policy](https://operations.osmfoundation.org/policies/nominatim/) for more informations.

> If you are sending in large numbers of requests it is particularly important that we have some way of contacting you - otherwise, in the event of problems, the only alternative is to ban your IP and wait for you to contact us.

How to use
----------

See the API [javadoc](https://jeremiehuchet.github.io/nominatim-java-api/latest) and [JUnit tests](https://github.com/jeremiehuchet/nominatim-java-api/blob/master/src/test/java/fr/dudie/nominatim/client/JsonNominatimClientTest.java). 

Java & Android
--------------

I don't use it anymore in android app but it should still be compatible with Android.

Features
--------

See the [releases](https://github.com/jeremiehuchet/nominatim-java-api/releases) page.

Please ask for enhancements and report bugs on [Github tracker](https://github.com/jeremiehuchet/nominatim-java-api/issues/new) or [contact us](http://www.google.com/recaptcha/mailhide/d?k=01Th60_7w3rxWuSJumsnqxfg==&c=REgbsYXndhO58POROxZGybu0F_Xu3JmR-wBRNbh8knE).
