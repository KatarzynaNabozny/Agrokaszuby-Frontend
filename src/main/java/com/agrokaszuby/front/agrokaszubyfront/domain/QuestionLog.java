package com.agrokaszuby.front.agrokaszubyfront.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
public class QuestionLog {
    private Long questionLogId;
    private String email;
    private String event;
    private Boolean successful;
    private LocalDateTime date;
}
