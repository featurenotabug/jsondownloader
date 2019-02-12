package model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "id",
        "title",
        "body"
})
public class PostDTO implements JsonDTO {

    @JsonProperty("userId")
    public Integer userId;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;

    public PostDTO(){}

    public PostDTO(Integer userId, Integer id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

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
        if (other == this)
            return true;

        if (!(other instanceof PostDTO))
            return false;

        PostDTO otherPostDTO = (PostDTO) other;
        return new EqualsBuilder().append(id, otherPostDTO.id).append(body, otherPostDTO.body).append(title, otherPostDTO.title).append(userId, otherPostDTO.userId).isEquals();
    }

}