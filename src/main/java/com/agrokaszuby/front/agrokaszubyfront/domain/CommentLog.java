package com.agrokaszuby.front.agrokaszubyfront.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
public class CommentLog {
    private Long commentLogId;
    private String email;
    private String event;
    private Boolean successful;
    private LocalDateTime date;
}
