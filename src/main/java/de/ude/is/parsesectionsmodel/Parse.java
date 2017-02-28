
package de.ude.is.parsesectionsmodel;

import java.util.List;

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
    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;

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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Parse withSections(List<Section> sections) {
        this.sections = sections;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(pageid).append(sections).toHashCode();
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
        return new EqualsBuilder().append(title, rhs.title).append(pageid, rhs.pageid).append(sections, rhs.sections).isEquals();
    }

}
