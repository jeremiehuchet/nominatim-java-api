Nominatim Java API
==================

[![Build Status](https://travis-ci.org/kops/nominatim-java-api.png?branch=master)](https://travis-ci.org/kops/nominatim-java-api)

This library provides a client for [Nominatim](http://wiki.openstreetmap.org/wiki/Nominatim). This API provides search and reverse geocoding operations. 

*Note that it is fair and reasonable to specify an email address when using the Nominatim API*, see the [Notminatim Usage Policy](http://wiki.openstreetmap.org/wiki/Nominatim_usage_policy) for more informations.

> If you are sending in large numbers of requests it is particularly important that we have some way of contacting you - otherwise, in the event of problems, the only alternative is to ban your IP and wait for you to contact us.

Java & Android
--------------

This library is fully compatible with Android. The `pom.xml` file has two profiles: 
* **java** is the default and use common java dependencies (`org.json:json-20070829` and `org.apache.httpcomponents:httpclient-4.0.2`)
* **android** can be activated using command `mvn -Pandroid clean package`. Then the dependencies will be switched to android dependency (`com.google.android:android-2.1.2`)

Please ask for enhancements and report bugs on [Github tracker](https://github.com/kops/nominatim-java-api/issues/new) or [contact us](http://www.google.com/recaptcha/mailhide/d?k=01Th60_7w3rxWuSJumsnqxfg==&c=REgbsYXndhO58POROxZGybu0F_Xu3JmR-wBRNbh8knE).

Features
--------

next-release
* Issue #11 wrong documentation

2.0.2
* Issue #7 Remove `org.json:json` dependency

2.0.1
* Bug [#6](https://github.com/kops/nominatim-java-api/issues/6) The bounding box values are incorrect
* Enhancement [#1](https://github.com/kops/nominatim-java-api/issues/1) Make the API URL customizable
* Pull-request [#2](https://github.com/kops/nominatim-java-api/pull/2) Addition of place_id field to Address (from tonytw1)
* Pull-request [#5](https://github.com/kops/nominatim-java-api/pull/5) Additional reverse look up methods (from tonytw1)

2.0
* Use gson to deserialize Nominatim responses

1.0.1
* Expose the OpenStreetMap place_id field on the Address class. [Tony McCrae]

1.0
* string searches
* reverse geocoding

