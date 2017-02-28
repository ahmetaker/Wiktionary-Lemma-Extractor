
package de.ude.is.parsesectionsmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Section {

    @SerializedName("toclevel")
    @Expose
    private Integer toclevel;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("line")
    @Expose
    private String line;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("fromtitle")
    @Expose
    private String fromtitle;
    @SerializedName("byteoffset")
    @Expose
    private Integer byteoffset;
    @SerializedName("anchor")
    @Expose
    private String anchor;

    public Integer getToclevel() {
        return toclevel;
    }

    public void setToclevel(Integer toclevel) {
        this.toclevel = toclevel;
    }

    public Section withToclevel(Integer toclevel) {
        this.toclevel = toclevel;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Section withLevel(String level) {
        this.level = level;
        return this;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Section withLine(String line) {
        this.line = line;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Section withNumber(String number) {
        this.number = number;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Section withIndex(String index) {
        this.index = index;
        return this;
    }

    public String getFromtitle() {
        return fromtitle;
    }

    public void setFromtitle(String fromtitle) {
        this.fromtitle = fromtitle;
    }

    public Section withFromtitle(String fromtitle) {
        this.fromtitle = fromtitle;
        return this;
    }

    public Integer getByteoffset() {
        return byteoffset;
    }

    public void setByteoffset(Integer byteoffset) {
        this.byteoffset = byteoffset;
    }

    public Section withByteoffset(Integer byteoffset) {
        this.byteoffset = byteoffset;
        return this;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public Section withAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(toclevel).append(level).append(line).append(number).append(index).append(fromtitle).append(byteoffset).append(anchor).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Section) == false) {
            return false;
        }
        Section rhs = ((Section) other);
        return new EqualsBuilder().append(toclevel, rhs.toclevel).append(level, rhs.level).append(line, rhs.line).append(number, rhs.number).append(index, rhs.index).append(fromtitle, rhs.fromtitle).append(byteoffset, rhs.byteoffset).append(anchor, rhs.anchor).isEquals();
    }

}
