
package de.ude.is.categorymembersmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Categorymember {

    @SerializedName("pageid")
    @Expose
    private int pageid;
    @SerializedName("ns")
    @Expose
    private int ns;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     */
    public Categorymember() {
    }

    /**
     * @param title
     * @param ns
     * @param pageid
     */
    public Categorymember(int pageid, int ns, String title) {
        super();
        this.pageid = pageid;
        this.ns = ns;
        this.title = title;
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public Categorymember withPageid(int pageid) {
        this.pageid = pageid;
        return this;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public Categorymember withNs(int ns) {
        this.ns = ns;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Categorymember withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pageid).append(ns).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Categorymember) == false) {
            return false;
        }
        Categorymember rhs = ((Categorymember) other);
        return new EqualsBuilder().append(pageid, rhs.pageid).append(ns, rhs.ns).append(title, rhs.title).isEquals();
    }

}
