package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Member {

    private Long memberId;
    private String name;
    private String email;
    private String phone;
    private String department;
}
