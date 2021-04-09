package hellojpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


//@Table(name = "USER") //DB TEBLE 이름으로 바꾸고싶을때 기본은 클래스명과 같은 테이블을 찾는다.
//@Entity
public class Member2 {
    @Id
    private Long id;

    @Column(name = "name",nullable = false, columnDefinition = "varchar(100) default 'EMPTY'") //DB에 name 이고 객체는 username 일때
    private String username;

    @Column(precision = 20,scale = 2)
    private BigDecimal age; //Integer 타입 가능
    //enum 사용 가능 ORDINAL은 숫자를 저장하기 때문에 enum클래스에서 순서가 바뀌면 디비에 저장될때
    // 치명적인 오류를 발생할 수 있으므로 STRING으로 사용한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    //과거버전에서는 애너테이션을 명시해줘야함
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //하이버네이트에서는 데이터 타입을 보고 알아서 타입을 지정해서 create 해준다. (최신버전)
    private LocalDate testLocalDate; //년월
    private LocalDateTime testLocalDateTime; //년월일


    @Lob //varchar를 넘어서는 큰 값을 넣을 때 String 타입이면 db에서 clob타입으로 생성 나머지는 blob
    private String description;

    // @Transient
    // 필드와 매핑하지 않으며, 데이터베이스 저장,조회 불가능하다.
    // 메모리상에서 임시로 값을 보관하고 싶을 때 사용한다.

    public Member2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
