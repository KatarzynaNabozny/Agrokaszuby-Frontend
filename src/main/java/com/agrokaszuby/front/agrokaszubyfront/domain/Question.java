package com.agrokaszuby.front.agrokaszubyfront.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Question {
    private Long questionId;
    private String fromName;
    private String email;
    private String content;
    private String subject;
    private LocalDateTime date;
}
