package site.skillstory.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String career;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'ROLE_USER'")
    private String role;  // 역할 (일반회원, 강사, 멘토, 관리자 등)

}
