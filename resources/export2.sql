SELECT
  lemma,
  word_forms
FROM lemma
WHERE language = "GERMAN" AND pos = "NOUN"
INTO OUTFILE 'NOUN_one_to_one.txt'
FIELDS TERMINATED BY '==='
  ENCLOSED BY ''
LINES TERMINATED BY '\n';

SELECT
  lemma,
  word_forms
FROM lemma
WHERE language = "GERMAN" AND pos = "VERB"
INTO OUTFILE 'VERB_one_to_one.txt'
FIELDS TERMINATED BY '==='
  ENCLOSED BY ''
LINES TERMINATED BY '\n';

SELECT
  lemma,
  word_forms
FROM lemma
WHERE language = "GERMAN" AND pos = "ADVERB"
INTO OUTFILE 'ADVERB_one_to_one.txt'
FIELDS TERMINATED BY '==='
  ENCLOSED BY ''
LINES TERMINATED BY '\n';

SELECT
  lemma,
  word_forms
FROM lemma
WHERE language = "GERMAN" AND pos = "ADJECTIVE"
INTO OUTFILE 'ADJECTIVE_one_to_one.txt'
FIELDS TERMINATED BY '==='
  ENCLOSED BY ''
LINES TERMINATED BY '\n';