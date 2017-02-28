
package de.ude.is.parsepagemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Text {

    @SerializedName("*")
    @Expose
    private String html;

    /**
     * No args constructor for use in serialization
     */
    public Text() {
    }

    /**
     * @param html
     */
    public Text(String html) {
        super();
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Text withHtml(String html) {
        this.html = html;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(html).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Text) == false) {
            return false;
        }
        Text rhs = ((Text) other);
        return new EqualsBuilder().append(html, rhs.html).isEquals();
    }

}
