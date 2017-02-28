package de.ude.is.entities;

import javax.persistence.*;

/**
 * Created by firas on 2/16/2017.
 */
@Entity
@Table(name = "lemma", schema = "wiktionary", catalog = "")
public class LemmaEntity {
    private int id;
    private String lemma;
    private String wordForms;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lemma", nullable = true, length = -1)
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @Basic
    @Column(name = "word_forms", nullable = true, length = -1)
    public String getWordForms() {
        return wordForms;
    }

    public void setWordForms(String wordForms) {
        this.wordForms = wordForms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LemmaEntity that = (LemmaEntity) o;

        if (id != that.id) return false;
        if (lemma != null ? !lemma.equals(that.lemma) : that.lemma != null) return false;
        if (wordForms != null ? !wordForms.equals(that.wordForms) : that.wordForms != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lemma != null ? lemma.hashCode() : 0);
        result = 31 * result + (wordForms != null ? wordForms.hashCode() : 0);
        return result;
    }
}
