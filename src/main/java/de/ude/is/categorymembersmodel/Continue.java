
package de.ude.is.categorymembersmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Continue {

    @SerializedName("cmcontinue")
    @Expose
    private String cmcontinue;
    @SerializedName("continue")
    @Expose
    private String _continue;

    /**
     * No args constructor for use in serialization
     */
    public Continue() {
    }

    /**
     * @param cmcontinue
     * @param _continue
     */
    public Continue(String cmcontinue, String _continue) {
        super();
        this.cmcontinue = cmcontinue;
        this._continue = _continue;
    }

    public String getCmcontinue() {
        return cmcontinue;
    }

    public void setCmcontinue(String cmcontinue) {
        this.cmcontinue = cmcontinue;
    }

    public Continue withCmcontinue(String cmcontinue) {
        this.cmcontinue = cmcontinue;
        return this;
    }

    public String getContinue() {
        return _continue;
    }

    public void setContinue(String _continue) {
        this._continue = _continue;
    }

    public Continue withContinue(String _continue) {
        this._continue = _continue;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cmcontinue).append(_continue).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Continue) == false) {
            return false;
        }
        Continue rhs = ((Continue) other);
        return new EqualsBuilder().append(cmcontinue, rhs.cmcontinue).append(_continue, rhs._continue).isEquals();
    }

}
