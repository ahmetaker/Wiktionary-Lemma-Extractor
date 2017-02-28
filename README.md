
## Getting Started


### Prerequisites

You will need a mysql database. your database "wiktionary" should has two tables "word" and "lemma".

```
CREATE DATABASE wiktionary CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE wiktionary.`word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` text COLLATE utf8mb4_unicode_ci,
  `form` text COLLATE utf8mb4_unicode_ci,
  `language` text COLLATE utf8mb4_unicode_ci,
  `pos` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `word_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE wiktionary.`lemma` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lemma` text COLLATE utf8mb4_unicode_ci,
  `word_forms` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lemma2_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### Usage & Examples

To run the jar file:
```
java wiktionary-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar LANGUAGE
```
where LANGUAGE is the name of the language such as German, English, Turkish, etc.

More examples on the code level:

To fetch the data of a single Wiktionary page, use the page parser. This is an example of how to extract the English part of speech for page "test" 
https://en.wiktionary.org/wiki/test

```
PageParser parser = new PageParser("test");
List<Section> sections = parser.parsePage();
```
each section represents a part of speech for the given languages in that page.
it contains the section language, section title(part of speech), lemma indicator and list of word forms.

In case you need to run the code on a language you need to check if this language is supported in Wiktionary or not.
To search for German language use this example.

```
LangInfo language = languages.search("german");
```
Variable language will be null if the given language is not defined

To collect all words of a language use this example

```
LanguageWordsCollector collector = new LanguageWordsCollector(language);
```

collector is an iterable object. to access the data inside it, use this example

```
Iterator<String> iterator = collector.iterator();
while (iterator.hasNext()) {
    PageParser parser = new PageParser(pageTitle, pageIndex);
    List<Section> sections = parser.parsePage();
}
```

You need to use a thread pool to speed up the parsing process.

```
ExecutorService pool = Executors.newFixedThreadPool(100);
LanguageWordsCollector collector = new LanguageWordsCollector(language);
Iterator<String> iterator = collector.iterator();
while (iterator.hasNext()) {
    PageParser parser = new PageParser(pageTitle, pageIndex);
    pool.submit(parser);
}
```


To export the results to files you may use 

```
DBService.exportToFile("German", "Noun");
```
This will generate a file of all German Wiktionary nouns in the following format

```
Abschiedsbrief===Abschiedsbrief;Abschiedsbriefe;Abschiedsbriefen;Abschiedsbriefes;Abschiedsbriefs
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

See license file.
