
package de.ude.is.parsepagemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Parse {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pageid")
    @Expose
    private Integer pageid;
    @SerializedName("text")
    @Expose
    private Text text;

    /**
     * No args constructor for use in serialization
     */
    public Parse() {
    }

    /**
     * @param text
     * @param title
     * @param pageid
     */
    public Parse(String title, Integer pageid, Text text) {
        super();
        this.title = title;
        this.pageid = pageid;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Parse withTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public Parse withPageid(Integer pageid) {
        this.pageid = pageid;
        return this;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Parse withText(Text text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(pageid).append(text).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Parse) == false) {
            return false;
        }
        Parse rhs = ((Parse) other);
        return new EqualsBuilder().append(title, rhs.title).append(pageid, rhs.pageid).append(text, rhs.text).isEquals();
    }

}
