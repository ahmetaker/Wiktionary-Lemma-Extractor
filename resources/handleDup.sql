DROP PROCEDURE IF EXISTS wiktionary.remove_dup;
CREATE PROCEDURE remove_dup()

  BEGIN

    SET @n := (SELECT count(*)
               INTO n
               FROM lemma
               WHERE
                 LENGTH(word_forms) - LENGTH(REPLACE(word_forms, ';', '')) = 0
                 AND lemma = lemma.word_forms);
    DECLARE i INT DEFAULT 0;

    SET i = 0;
    WHILE i < n DO
      SET @config := (SELECT lemma
                      FROM lemma
                      WHERE LENGTH(word_forms) - LENGTH(REPLACE(word_forms, ';', '')) = 0
                            AND lemma = lemma.word_forms
                      LIMIT i, 1);
      SET @lemma_count := (SELECT count(*)
                           FROM lemma
                           WHERE lemma.lemma = @config AND lemma.language = "GERMAN" AND lemma.pos = "VERB");
      IF @lemma_count > 1
      THEN
        DELETE FROM lemma
        WHERE lemma.lemma = @config;
      END IF;
      SET i = i + 1;
    END WHILE;

    /* SELECT lemma, word_forms FROM lemma2 where
         LENGTH(word_forms) - LENGTH(REPLACE(word_forms, ';', '')) = 0
         and lemma = lemma2.word_forms

     SET @config := (select years from table1 where route_id = 1);


     declare v_max int unsigned default 1000;
     declare v_counter int unsigned default 0;

     PREPARE stmt FROM @sql;
     EXECUTE stmt;

     set @platform_id := (select id from platform where name = 'Nintendo DS');

     -- Only insert rows if the platform was found
     if @platform_id is not null then

         insert into game(name, platform_id) values('New Super Mario Bros', @platform_id);
         insert into game(name, platform_id) values('Mario Kart DS', @platform_id);

     end if;
 */
  END;