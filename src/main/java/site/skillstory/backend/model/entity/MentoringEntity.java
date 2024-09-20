package site.skillstory.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "mentorings")
public class MentoringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.DATE)
    private LocalDate entryDate;

    @Temporal(TemporalType.DATE)
    private LocalDate modifyDate;

    @Column(nullable = false)
    private LocalDateTime mentoringTime;

    @Column(nullable = false)
    private Double mentoringFee;

    @Column(nullable = false)
    private Integer mentoringParticipants;

    // 유저 테이블과의 관계 설정 (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity mentor;  // 멘토(유저)

}