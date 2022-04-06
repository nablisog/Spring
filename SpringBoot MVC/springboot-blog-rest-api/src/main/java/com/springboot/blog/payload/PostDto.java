package com.springboot.blog.payload;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post Model Information")
@Data
public class PostDto {
    @ApiModelProperty(value = "Blog post id")
    private Long id;

    @ApiModelProperty(value = "Blog post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 Charcaters")
    private String title;

    @ApiModelProperty(value = "Blog post description")
    @NotEmpty
    @Size(min = 10, message = "Post title should have at least 10 Charcaters")
    private String description;

    @ApiModelProperty(value = "Blog post content")
    @NotEmpty
    private String content;

    @ApiModelProperty(value = "Blog post comments")
    private Set<CommentDto> comments;

}
