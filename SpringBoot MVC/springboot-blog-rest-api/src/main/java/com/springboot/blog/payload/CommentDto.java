package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Comment Model Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @ApiModelProperty(value = "Blog comment id")
    private long id;

    @ApiModelProperty(value = "Blog comment name")
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @ApiModelProperty(value = "Blog comment email")
    @Email
    @NotEmpty(message = "email should not be null or empty")
    private String email;

    @ApiModelProperty(value = "Blog comment body")
    @NotEmpty
    @Size(message = "must be at least 10 Character")
    private String body;
}
