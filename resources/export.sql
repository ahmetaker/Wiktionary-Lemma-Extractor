TRUNCATE lemma;
SET group_concat_max_len = 20000;
INSERT INTO lemma (lemma, word_forms)
  SELECT DISTINCT
    word,
    GROUP_CONCAT(DISTINCT form ORDER BY form SEPARATOR ';') AS form
  FROM word
  WHERE `language` = 'Turkish' AND pos = "Verb"
  GROUP BY word
  ORDER BY form;

SELECT
  lemma,
  word_forms
FROM lemma
INTO OUTFILE 'ADVERB.txt'
FIELDS TERMINATED BY '==='
  ENCLOSED BY ''
LINES TERMINATED BY '\n';


SELECT *
FROM lemma
WHERE
  LENGTH(word_forms) - LENGTH(REPLACE(word_forms, ';', '')) = 0
  AND lemma = word_forms


SELECT *
FROM lemma
WHERE word_forms = "testet" AND language = "GERMAN";

INTO OUTFILE 'ADVERB.txt'
FIELDS TERMINATED BY '==='
ENCLOSED BY ''
LINES TERMINATED BY '\n';