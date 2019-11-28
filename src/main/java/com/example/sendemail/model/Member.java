package com.example.sendemail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = -1414864271894265765L;

    @Id @GeneratedValue
    @JsonProperty("memberSeq")
    private Long memberSeq;

    @JsonProperty("memberId")
    private String memberId;

    @JsonProperty("memberName")
    private String memberName;

    @JsonProperty("memberBirth")
    private String memberBirth;

    @JsonProperty("modDate")
    @UpdateTimestamp
    private LocalDateTime modDate;

    @JsonProperty("regDate")
    @CreationTimestamp
    private LocalDateTime regDate;

}
