
package de.ude.is.categorymembersmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoryMembersAPI {

    @SerializedName("batchcomplete")
    @Expose
    private String batchcomplete;
    @SerializedName("continue")
    @Expose
    private Continue _continue;
    @SerializedName("query")
    @Expose
    private Query query;

    /**
     * No args constructor for use in serialization
     */
    public CategoryMembersAPI() {
    }

    /**
     * @param query
     * @param batchcomplete
     * @param _continue
     */
    public CategoryMembersAPI(String batchcomplete, Continue _continue, Query query) {
        super();
        this.batchcomplete = batchcomplete;
        this._continue = _continue;
        this.query = query;
    }

    public String getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public CategoryMembersAPI withBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
        return this;
    }

    public Continue getContinue() {
        return _continue;
    }

    public void setContinue(Continue _continue) {
        this._continue = _continue;
    }

    public CategoryMembersAPI withContinue(Continue _continue) {
        this._continue = _continue;
        return this;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public CategoryMembersAPI withQuery(Query query) {
        this.query = query;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(batchcomplete).append(_continue).append(query).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CategoryMembersAPI) == false) {
            return false;
        }
        CategoryMembersAPI rhs = ((CategoryMembersAPI) other);
        return new EqualsBuilder().append(batchcomplete, rhs.batchcomplete).append(_continue, rhs._continue).append(query, rhs.query).isEquals();
    }

}
