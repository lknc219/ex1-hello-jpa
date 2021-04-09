package hellojpa;

import javax.persistence.*;


//@Table(name = "USER") //DB TEBLE 이름으로 바꾸고싶을때 기본은 클래스명과 같은 테이블을 찾는다.
//@Entity
/*@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)*/
public class Member3 {
    //@Id 내가 직접 Id 값을 셋팅할때 씀
    //@GeneratedValue 시퀀스처럼 값을 자동으로 할당해줌
    //@GeneratedValue의 속성으로는
    //IDENTITY - 기본키 생성을 데이터베이스에 위임
    //SEQUENCE - 시퀀스 오브젝트만들어서 이를 통해 값을 가져와 셋팅해준다. DB의 시퀀스 사용
    //@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name") //DB에 name 이고 객체는 username 일때
    private String username;

    public Member3() {
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
}
