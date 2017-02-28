package de.ude.is.dal;

import de.ude.is.entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

/**
 * Class {@code DBService} offers multiple database operations
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 12/23/2016.
 */
public class DBService {
    /**
     * Truncate tables
     */
    public static void truncateTables() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE wiktionary.word").executeUpdate();
        session.getTransaction().commit();
        if (session != null) {
            session.close();
        }
    }

    /**
     * Export collected data to files
     *
     * @param language Targeted language
     * @param pos      Targeted part of speech
     */
    public static void exportToFile(String language, String pos) {
        String filename = (pos + "s.txt").toLowerCase();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.doWork(new Work() {
            @Override
            public void execute(Connection con) throws SQLException {
                con.setAutoCommit(true);
                PreparedStatement pStmt = null;
                try {
                    String sqlQry = "TRUNCATE TABLE wiktionary.lemma;";
                    pStmt = con.prepareStatement(sqlQry);
                    pStmt.execute();
                    sqlQry = "SET group_concat_max_len=1000000;";
                    pStmt = con.prepareStatement(sqlQry);
                    pStmt.execute();
                    sqlQry = "INSERT INTO lemma (lemma, word_forms) SELECT DISTINCT word, GROUP_CONCAT(DISTINCT form ORDER BY form SEPARATOR ';') AS word_forms FROM word WHERE `language` = '" + language + "' and pos='" + pos + "' GROUP BY word ORDER BY word_forms;";
                    pStmt = con.prepareStatement(sqlQry);
                    pStmt.execute();
                    exportTemp(language, filename);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pStmt.close();
                }
            }
        });

        if (session != null) {
            session.close();
        }
    }

    /**
     * Export temp data to a file
     *
     * @param filename
     */
    private static void exportTemp(String language, String filename) throws FileNotFoundException {
        List<LemmaEntity> entities = null;
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM LemmaEntity ").list();
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
        File langDir = new File(language);
        if (!langDir.exists()) {
            langDir.mkdir();
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(language + "/" + filename));
        for (LemmaEntity entity : entities)
            pw.println(String.format("%s===%s", entity.getLemma(), entity.getWordForms()));
        pw.close();
    }

    /**
     * Saves a word
     *
     * @param title    page title or the word
     * @param language language of the word
     * @param pos      part of speech for the word
     * @param form     one form of that word
     */
    public static void saveWordsList(String title, String language, String pos, String form) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        WordEntity entity = new WordEntity();
        entity.setWord(title);
        entity.setLanguage(language);
        entity.setPos(pos);
        entity.setForm(form);
        session.save(entity);
        session.getTransaction().commit();
        if (session != null) {
            session.close();
        }
    }
}
