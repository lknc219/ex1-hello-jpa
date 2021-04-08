package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//@Table(name = "USER") //DB TEBLE 이름으로 바꾸고싶을때 기본은 클래스명과 같은 테이블을 찾는다.
@Entity
public class Member {


    @Id
    private long id;
    //@Column(name = "username")//컬럼명을 username 을 찾도록 입력
    private String name;

    //JPA는 기본생성자 필수

    public Member(){

    }
    public Member(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
