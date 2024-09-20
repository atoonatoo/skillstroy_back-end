package site.skillstory.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "lectures")
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;  // 강의 제목

    @Column(nullable = false)
    private String instructor;  // 강사 이름

    @Column(nullable = false)
    private Double price;  // 수강료

    @Column(nullable = false)
    private Duration duration;  // 강의 시간 (총 강의 시간)

    @Column(nullable = false)
    private String difficultyLevel;  // 난이도 (입문, 초급, 중급, 고급)

    @Column(nullable = false)
    private String category;  // 강의 카테고리 (프론트엔드, 백엔드, 인공지능 등)

    @Column(nullable = false)
    private LocalDate startDate;  // 강의 시작일

    @Column(nullable = false)
    private LocalDate endDate;  // 강의 종료일

    @Column(nullable = false)
    private Integer studentCount;  // 수강생 수

    @Column(nullable = true)
    @ColumnDefault("0")
    private Double rating;  // 평점

}