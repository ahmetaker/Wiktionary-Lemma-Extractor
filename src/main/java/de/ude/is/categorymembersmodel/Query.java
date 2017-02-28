
package de.ude.is.categorymembersmodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Query {

    @SerializedName("categorymembers")
    @Expose
    private List<Categorymember> categorymembers = null;

    /**
     * No args constructor for use in serialization
     */
    public Query() {
    }

    /**
     * @param categorymembers
     */
    public Query(List<Categorymember> categorymembers) {
        super();
        this.categorymembers = categorymembers;
    }

    public List<Categorymember> getCategorymembers() {
        return categorymembers;
    }

    public void setCategorymembers(List<Categorymember> categorymembers) {
        this.categorymembers = categorymembers;
    }

    public Query withCategorymembers(List<Categorymember> categorymembers) {
        this.categorymembers = categorymembers;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorymembers).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Query) == false) {
            return false;
        }
        Query rhs = ((Query) other);
        return new EqualsBuilder().append(categorymembers, rhs.categorymembers).isEquals();
    }

}
