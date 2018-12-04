# Serverside Test

## About

This project will scrape product data from the [Sainsbury's Test Site](https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html).

The result from the scrape is then transformed to Json data and output in the console in the following format:

```json
{
  "results" : [ {
    "title" : "Sainsbury's Strawberries 400g",
    "kcalPer100g" : 33,
    "unitPrice" : 1.75,
    "description" : "by Sainsbury's strawberries"
  }, {
    "title" : "Sainsbury's Blueberries 200g",
    "kcalPer100g" : 45,
    "unitPrice" : 1.75,
    "description" : "by Sainsbury's blueberries"
  }],
 "total" : {
   "gross" : 3.50,
   "vat" : 0.58
 }
}
```

## Build Status

[![Build Status](https://travis-ci.org/davetrencher/sainsburys.svg?branch=master)](https://travis-ci.org/davetrencher/sainsburys)
 

## Design Considerations


* The initial URL of the target site may change and so this has been held in configuration.
* The project currently outputs the response as Json.  Other formats may be requested in the future 
so in order to facilitate this, I have created a factory pattern using the Formatters enum  which will
return the specified subclass of the Formatter interface.
* Parsing HTML is subject to the changes of the site that it is parsing.  Changes to the target site can 
cause the css selectors being used to extract the data to become invalid and so it is useful that these can
be easily configured.
* According to the brief all items are changed at a standard 20% VAT rate.  In the real world different items
are subject to different rates of VAT.  The program calculates the VAT rate on each item and then calculates 
the total VAT amount rather than calculating the VAT amount from the total gross.   This would make it easier 
in future to set different VAT rates per item either through scraping or obtaining from a separate source.

## Future Enhancments

There are several future enhancements that would make this program easier to run and maintain.

* Fully externalising the config file so that it can be picked up from anywhere on the host machine rather than 
just the class path.
* Being able to scrape the VAT rate for individual line items as different items may be subject to different VAT rates.
* Adding additional output formats e.g. html or CSV.

## Tools, Libraries and Frameworks

* Build: Gradle 
* Java: Java 8
* Test: WireMock, Hamcrest, 
* HTML scraping: Jsoup
* Json Parsing: Jackson
 
## Setup

Build the project

```
./gradlew clean build fatJar

```

## Execution

Run the project

```
 java -jar build/libs/sainsburys-all.jar
```

## Configuration

Configuration is held in: ``src/main/resources/config/config.properties``

This can be overridden with a different config file that is on the classpath using a relative
or root relative path e.g. 

```json
 java -jar build/libs/sainsburys-all.jar /config/config2.properties
```

Using ```/config/config2.properties``` will set the standard VAT rate to 10% rather than 20% for 
demonstration purposes.