package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        //웹서버가 실행될때 한 번 생성해서 전체 애플리케이션에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //요청사항을 수행할 때 마다 한 번 쓰고 버려야함. 쓰레드마다 공유 X
        //모든 데이터 변경은 트랜잭션 안에서 수행해야함.
        EntityManager em = emf.createEntityManager();//디비커넥션 하나 받았다고 생각하면됨

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            //연관관계의 주인에 값을 세팅해주는 부분 Member 와 Team 객체의 연관관계의 주인은 Member.team 이다
            //setTeam -> changeTeam 으로 바꿔주면서 team을 셋팅할때 team.getMembers().add(this) 를 해줌으로
            //양방향으로 셋팅을 하는 메서드로 만들어준다 (연관관계 편의 메서드라고 한다.)

            //연관관계의 주인은 아니지만 둘다 셋팅해주는게 좋다.
            //DB에서 읽지않고 영속성 컨텍스트의 순수 객체를 읽을때 못 읽어오는 경우가 있기 떄문
            //team.getMembers().add(member);


            //구현클래스마다 테이블 전략 -> 안쓰는게 좋음 이런식으로 부모클래스로 조회시 모든 테이블을 union 해서 성능저하
            //보통 조인 전략을 기본으로 사용한다.
            //정말 단순하고 데이터도 얼마 없을 시 단일 테이블 전략 사용

            //MappedSuperClass 는 엔티티가 아니기 때문에 조회가 안된다.

            Address address = new Address("city", "street", "zipcode");

            Member member = new Member();
            member.setHomeAddress(address);
            member.setUsername("member1");
            em.persist(member);

            Address newAddress = new Address("NewCity", address.getStreet(), address.getStreet());

            member.setHomeAddress(newAddress);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }


}
