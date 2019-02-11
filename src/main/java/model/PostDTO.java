package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "id",
        "title",
        "body"
})
public class PostDTO implements DTO {

    @JsonProperty("userId")
    public Integer userId;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("id", id).append("title", title).append("body", body).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(body).append(title).append(userId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PostDTO)) {
            return false;
        }
        PostDTO otherPostDTO = (PostDTO) other;
        return new EqualsBuilder().append(id, otherPostDTO.id).append(body, otherPostDTO.body).append(title, otherPostDTO.title).append(userId, otherPostDTO.userId).isEquals();
    }

}