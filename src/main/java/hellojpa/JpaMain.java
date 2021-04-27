package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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

            /*Address address = new Address("city", "street", "zipcode");

            Member member = new Member();
            member.setHomeAddress(address);
            member.setUsername("member1");
            em.persist(member);

            Address newAddress = new Address("NewCity", address.getStreet(), address.getStreet());

            member.setHomeAddress(newAddress);*/

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homecity1", "street", "zipcode"));

            member.getFavoritFoods().add("치킨");
            member.getFavoritFoods().add("족발");
            member.getFavoritFoods().add("피자");

            // member.getAddressHistory().add(new Address("old1", "street", "zipcode"));
            // member.getAddressHistory().add(new Address("old2", "street", "zipcode"));

            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("===============START=============");
            Member findMember = em.find(Member.class, member.getId());


            //homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity");
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity",a.getStreet(),a.getZipcode()));

            //치킨 -> 한식
            findMember.getFavoritFoods().remove("치킨");
            findMember.getFavoritFoods().add("한식");

            //
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "zipcode"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "zipcode"));

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
